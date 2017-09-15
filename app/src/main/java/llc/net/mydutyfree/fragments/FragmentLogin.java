package llc.net.mydutyfree.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.AccountSignIn;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.JsonCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 12/22/16.
 */

public class FragmentLogin extends Fragment {

//    private ListView mDrawerList;
    private EditText edtEmail, edtPassword;
    private TextInputLayout tilEmail, tilPassword;
    private Button btnLogIn, btnChangeToRegister;
    private CheckBox checkBoxAgree;
    private Boolean isStateLogIn = true;
    private ImageView imgMain;
    private Tracker mTracker;
//    private String[] mArray;

    public  FragmentLogin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.screen_login, container, false);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Login");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        edtEmail = (EditText) rootView.findViewById(R.id.edtEmailScreenLogIn);
        edtPassword = (EditText) rootView.findViewById(R.id.edtPasswordScreenLogIn);
        tilEmail = (TextInputLayout) rootView.findViewById(R.id.tilEmailScreenLogIn);
        tilPassword = (TextInputLayout) rootView.findViewById(R.id.tilPasswordScreenLogIn);
        btnLogIn = (Button) rootView.findViewById(R.id.btnLogInScreenLogIn);
        btnChangeToRegister = (Button) rootView.findViewById(R.id.btnChangeToRegisterScreenLogIn);
        checkBoxAgree = (CheckBox) rootView.findViewById(R.id.chkbxAgreeNewsScreenLogIn);
        imgMain = (ImageView) rootView.findViewById(R.id.imgMainImageScreenLogIn);

        btnChangeToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStateLogIn = !isStateLogIn;
                updateScreenWithStateISLOGIN(isStateLogIn);
            }
        });

        edtEmail.addTextChangedListener(new MyTextWatcher(edtEmail));
        edtPassword.addTextChangedListener(new MyTextWatcher(edtPassword));

        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((!hasFocus) && (v == edtEmail)) {
                    if (edtEmail.getText().length() < 1) {
                        tilEmail.setError("");
                        tilEmail.setErrorEnabled(false);
                    }
                }
            }
        });

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((!hasFocus) && (v == edtPassword)) {
                    if (edtPassword.getText().length() < 1) {
                        tilPassword.setError("");
                        tilPassword.setErrorEnabled(false);
                    }
                }
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()
                submitForm();
            }
        });

//        mDrawerList = (ListView) rootView.findViewById(R.id.listList);
//
//        mArray = getResources().getStringArray(R.array.screen_array);
//
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this.getContext(),
//                R.layout.cart_cell, R.id.txtProductCartCell, mArray));
//        View footerView =  ((LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.cart_footer, null, false);
//        mDrawerList.addFooterView(footerView);

        return rootView;
    }

    private void submitForm() {
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), Const.MIXPANEL_PROJECT_TOKEN);
        try {
            JSONObject properties = new JSONObject();
            properties.put("email", edtEmail.getText());
            mixpanel.track("Login Click", properties);
        } catch (JSONException e) {}

//        if (!validateName()) {
//            return;
//        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

//        Toast.makeText(getActivity(), "Thank You!", Toast.LENGTH_SHORT).show();

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        final String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        final Fragment thisFragment = this;

        if (isStateLogIn) {
            Call<AccountSignIn> call = service.accountSignIn(JsonCreator.AccountSignIn(email, password));
            ((MainActivity)getActivity()).showProgress();
            call.enqueue(new Callback<AccountSignIn>() {
                @Override
                public void onResponse(Call<AccountSignIn> call, Response<AccountSignIn> response) {
                    ((MainActivity)getActivity()).hideProgress();
                    if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
//                        Toast.makeText(getActivity(), "UserAuth: " + response.body().getData(), Toast.LENGTH_LONG).show();
                        MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), Const.MIXPANEL_PROJECT_TOKEN);
                        mixpanel.identify(edtEmail.getText().toString());
                        try {
                            JSONObject properties = new JSONObject();
                            properties.put("type", "email");
                            mixpanel.track("Login Success", properties);
                        } catch (JSONException e) {}

                        Fragment fragment = new FragmentProfile();
                        ((MDFApplication)(getActivity().getApplication())).setUserAuthKey(response.body().getData());
                        ((MDFApplication)(getActivity().getApplication())).setEmail(email);
//                        ((FragmentItem) fragment).setProductID(productID);

                        if (fragment != null) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                            fragmentManager.popBackStack();
                            transaction.remove(thisFragment);
                            fragmentManager.popBackStackImmediate();
                            transaction.replace(R.id.content_frame, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }
                    }
                    else {
                        MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), Const.MIXPANEL_PROJECT_TOKEN);
                        try {
                            JSONObject properties = new JSONObject();
                            properties.put("type", "email");
                            mixpanel.track("Login Fail", properties);
                        } catch (JSONException e) {}
                        if (!TextUtils.isEmpty(response.body().getMessage())) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle(getResources().getString(R.string.an_error_has_occurred))
                                    .setMessage(response.body().getMessage())
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<AccountSignIn> call, Throwable t) {
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), Const.MIXPANEL_PROJECT_TOKEN);
                    try {
                        JSONObject properties = new JSONObject();
                        properties.put("type", "email");
                        mixpanel.track("Login Fail", properties);
                    } catch (JSONException e) {}
                    ((MainActivity)getActivity()).hideProgress();
                }
            });
        }
        else {
            Call<AccountSignIn> call = service.accountSignIn(JsonCreator.AccountSignUp(email, password, checkBoxAgree.isChecked()));
            ((MainActivity)getActivity()).showProgress();
            call.enqueue(new Callback<AccountSignIn>() {
                @Override
                public void onResponse(Call<AccountSignIn> call, Response<AccountSignIn> response) {
                    ((MainActivity)getActivity()).hideProgress();
                    if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
//                        Toast.makeText(getActivity(), "UserAuth: " + response.body().getData(), Toast.LENGTH_LONG).show();
                        new AlertDialog.Builder(getContext())
                                .setTitle(getResources().getString(R.string.register_ok_title))
                                .setMessage(getResources().getString(R.string.register_ok_message))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        ((MainActivity)getActivity()).onBackPressed();
                                    }
                                })
                                .setCancelable(false)
                                .show();
//                        Fragment fragment = new FragmentProfile();
//                        ((MDFApplication)(getActivity().getApplication())).setUserAuthKey(response.body().getData());
////                        ((FragmentItem) fragment).setProductID(productID);
//
//                        if (fragment != null) {
//                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                            FragmentTransaction transaction = fragmentManager.beginTransaction();
//                            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                            transaction.replace(R.id.content_frame, fragment);
//                            transaction.addToBackStack(null);
//                            transaction.commit();
//                        }
                    }
                    else {
//                        Toast.makeText(getActivity(), "Error: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("!!!", "!!!");
                        if (!TextUtils.isEmpty(response.body().getMessage())) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle(getResources().getString(R.string.an_error_has_occurred))
                                    .setMessage(response.body().getMessage())
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<AccountSignIn> call, Throwable t) {
                    Log.e("!!!", "error!!!");
                    ((MainActivity)getActivity()).hideProgress();
                }
            });
        }
//        Call<AccountSignIn> call = service.accountSignIn(JsonCreator.AccountSignIn("wardroft@gmail.com", "123123123"));

//        Call<AccountSignIn> call = service.accountSignIn(JsonCreator.AccountSignIn(email, password));
//        ((MainActivity)getActivity()).showProgress();
//        call.enqueue(new Callback<AccountSignIn>() {
//            @Override
//            public void onResponse(Call<AccountSignIn> call, Response<AccountSignIn> response) {
//                Log.e("!!!", "!!!");
//                ((MainActivity)getActivity()).hideProgress();
//                if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
//                    Log.e("!!!", "!!!");
////                    Toast.makeText(getActivity(), "UserAuth: " + response.body().getData(), Toast.LENGTH_LONG).show();
//
//                }
//                else {
////                    Toast.makeText(getActivity(), "Error: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("!!!", "!!!");
//                }
//            }
//            @Override
//            public void onFailure(Call<AccountSignIn> call, Throwable t) {
//                Log.e("!!!", "error!!!");
//                ((MainActivity)getActivity()).hideProgress();
//            }
//        });
    }

    private void updateScreenWithStateISLOGIN(Boolean state) {
        edtEmail.setText("");
        edtPassword.setText("");
        tilEmail.setError("");
        tilEmail.setErrorEnabled(false);
        tilPassword.setError("");
        tilPassword.setErrorEnabled(false);
        if (state) {
            btnLogIn.setText(getResources().getString(R.string.log_in));
            checkBoxAgree.setVisibility(View.GONE);
            btnChangeToRegister.setText(getResources().getString(R.string.account_dont_have));
        }
        else {
            btnLogIn.setText(getResources().getString(R.string.register));
            checkBoxAgree.setVisibility(View.VISIBLE);
            btnChangeToRegister.setText(getResources().getString(R.string.account_already_have));
        }
        imgMain.requestFocus();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                edtEmail.setText("");
//                edtPassword.setText("");
//                validateEmail();
//                validatePassword();
//                imgMain.requestFocus();
//
//            }
//        });
//        thread.start();
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getResources().getString(R.string.password_length_alert));
//            requestFocus(edtEmail);
            return false;
        } else {
            tilEmail.setError("");
            tilEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (edtPassword.getText().toString().trim().isEmpty()) {
            tilPassword.setErrorEnabled(true);
            tilPassword.setError(getResources().getString(R.string.enter_password));
            return false;
        } else {
            if (edtPassword.getText().toString().trim().length() < 4) {
                tilPassword.setErrorEnabled(true);
                tilPassword.setError(getResources().getString(R.string.password_length_alert));
                return false;
            }
            else {
                tilPassword.setError("");
                tilPassword.setErrorEnabled(false);
            }
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edtEmailScreenLogIn:
                    validateEmail();
                    break;
                case R.id.edtPasswordScreenLogIn:
                    validatePassword();
                    break;
            }
        }
    }
}
