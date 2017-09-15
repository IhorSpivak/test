package llc.net.mydutyfree.fragments;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.utils.HorScrollView;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 11/27/15.
 */
public class FragmentScrollTest extends Fragment {

    int screenHeightInPixels;
    int screenWidthInPixels;
    ImageView imgBigImageOne;
    ImageView imgBigImageTwo;
    ImageView imgBigImageThree;
    View rootView;

    public FragmentScrollTest() {
    }

    @Override
    public void onResume() {
        super.onResume();

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        int heightInPixels = displaymetrics.heightPixels;
        int widthInPixels = displaymetrics.widthPixels;
        screenHeightInPixels = heightInPixels;
        screenWidthInPixels = widthInPixels;

        final HorScrollView bottom = (HorScrollView)rootView.findViewById(R.id.horizontalScrollViewBottom);
        final HorScrollView top = (HorScrollView)rootView.findViewById(R.id.horizontalScrollViewTop);

        top.removeAllViews();
        top.setHorizontalScrollBarEnabled(false);
        top.setVerticalScrollBarEnabled(false);
        bottom.removeAllViews();
        bottom.setHorizontalScrollBarEnabled(false);
        bottom.setVerticalScrollBarEnabled(false);
        bottom.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams layoutParamsForLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayoutParentBottom = new LinearLayout(getContext());
        linearLayoutParentBottom.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutParentBottom.setLayoutParams(layoutParamsForLinearLayout);

        LinearLayout linearLayoutParentTop = new LinearLayout(getContext());
        linearLayoutParentTop.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutParentTop.setLayoutParams(layoutParamsForLinearLayout);

        RelativeLayout.LayoutParams layoutParamsForRelativeLayoutBottom = new RelativeLayout.LayoutParams(widthInPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParamsForRelativeLayoutTop = new RelativeLayout.LayoutParams((int) (widthInPixels / 0.7f), ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout relativeLayoutOneBig = new RelativeLayout(getContext());
        relativeLayoutOneBig.setPadding(0,0,0,0);
        relativeLayoutOneBig.setLayoutParams(layoutParamsForRelativeLayoutBottom);
        RelativeLayout relativeLayoutTwoBig = new RelativeLayout(getContext());
        relativeLayoutTwoBig.setPadding(0,0,0,0);
        relativeLayoutTwoBig.setLayoutParams(layoutParamsForRelativeLayoutBottom);
        RelativeLayout relativeLayoutThreeBig = new RelativeLayout(getContext());
        relativeLayoutThreeBig.setPadding(0,0,0,0);
        relativeLayoutThreeBig.setLayoutParams(layoutParamsForRelativeLayoutBottom);

        RelativeLayout relativeLayoutOneSmall = new RelativeLayout(getContext());
        relativeLayoutOneSmall.setLayoutParams(layoutParamsForRelativeLayoutTop);
        RelativeLayout relativeLayoutTwoSmall = new RelativeLayout(getContext());
        relativeLayoutTwoSmall.setLayoutParams(layoutParamsForRelativeLayoutTop);
        RelativeLayout relativeLayoutThreeSmall = new RelativeLayout(getContext());
        relativeLayoutThreeSmall.setLayoutParams(layoutParamsForRelativeLayoutTop);

        RelativeLayout.LayoutParams layoutParamsImageBig = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsImageBig.setMargins(0, 0, 0, 0);

        RelativeLayout.LayoutParams layoutParamsImageSmall = new RelativeLayout.LayoutParams((int) Utils.dpToPx(200),(int) Utils.dpToPx(75));
        layoutParamsImageSmall.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParamsImageSmall.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParamsImageSmall.setMargins(0, 0, (int) Utils.dpToPx(200), 0);
//        widthInPixels = widthInPixels + 0.5f;

        imgBigImageOne = new ImageView(getContext());
//        imgBigImageOne.setMaxWidth(widthInPixels);
//        imgBigImageOne.setMinimumWidth(widthInPixels);
//        imgBigImageOne.setPadding(0, 0, 0, 0);
        imgBigImageOne.setLayoutParams(layoutParamsImageBig);
        imgBigImageOne.setImageResource(R.drawable.slide_1_big);
        imgBigImageOne.setAdjustViewBounds(true);
        imgBigImageOne.setScaleType(ImageView.ScaleType.FIT_START);
//        imgBigImageOne.setCropToPadding(false);
//        imgBigImageOne.setPaddingRelative(0, 0, 0, 0);
//        imgBigImageOne.offsetLeftAndRight(10);

        imgBigImageTwo = new ImageView(getContext());
//        imgBigImageTwo.setMaxWidth(widthInPixels);
//        imgBigImageTwo.setMinimumWidth(widthInPixels);
//        imgBigImageTwo.setPadding(0, 0, 0, 0);
        imgBigImageTwo.setLayoutParams(layoutParamsImageBig);
        imgBigImageTwo.setAdjustViewBounds(true);
        imgBigImageTwo.setScaleType(ImageView.ScaleType.FIT_START);
        imgBigImageTwo.setImageResource(R.drawable.slide_2_big);
//        imgBigImageTwo.setCropToPadding(false);
//        imgBigImageTwo.setPaddingRelative(0, 0, 0, 0);
//        imgBigImageTwo.offsetLeftAndRight(0);

        imgBigImageThree = new ImageView(getContext());
//        imgBigImageThree.setMaxWidth(widthInPixels);
//        imgBigImageThree.setMinimumWidth(widthInPixels);
//        imgBigImageThree.setPadding(0, 0, 0, 0);
        imgBigImageThree.setLayoutParams(layoutParamsImageBig);
        imgBigImageThree.setAdjustViewBounds(true);
        imgBigImageThree.setScaleType(ImageView.ScaleType.FIT_START);
        imgBigImageThree.setImageResource(R.drawable.slide_3_big);
//        imgBigImageThree.setCropToPadding(false);
//        imgBigImageThree.setPaddingRelative(0, 0, 0, 0);
//        imgBigImageThree.offsetLeftAndRight(0);

        relativeLayoutOneBig.addView(imgBigImageOne, layoutParamsImageBig);
        linearLayoutParentBottom.addView(relativeLayoutOneBig);
        relativeLayoutTwoBig.addView(imgBigImageTwo, layoutParamsImageBig);
        linearLayoutParentBottom.addView(relativeLayoutTwoBig);
        relativeLayoutThreeBig.addView(imgBigImageThree, layoutParamsImageBig);
        linearLayoutParentBottom.addView(relativeLayoutThreeBig);
//        linearLayoutParentBottom.addView(imgBigImageOne);
//        linearLayoutParentBottom.addView(imgBigImageTwo);
//        linearLayoutParentBottom.addView(imgBigImageThree);
        bottom.addView(linearLayoutParentBottom);

//        ((RelativeLayout)top.getParent()).removeView(top);

        ImageView imgSmallImageOne = new ImageView(getContext());
        imgSmallImageOne.setLayoutParams(layoutParamsImageSmall);
        imgSmallImageOne.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgSmallImageOne.setImageResource(R.drawable.slide_1_small);

        ImageView imgSmallImageTwo = new ImageView(getContext());
        imgSmallImageTwo.setLayoutParams(layoutParamsImageSmall);
        imgSmallImageTwo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgSmallImageTwo.setImageResource(R.drawable.slide_2_small);

        ImageView imgSmallImageThree = new ImageView(getContext());
        imgSmallImageThree.setLayoutParams(layoutParamsImageSmall);
        imgSmallImageThree.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgSmallImageThree.setImageResource(R.drawable.slide_3_small);

//        relativeLayoutOneBig.getChildAt(0).setAlpha(0);
//        relativeLayoutOneBig.setBackgroundColor(Color.WHITE);
//        relativeLayoutTwoBig.getChildAt(0).setAlpha(0);
//        relativeLayoutTwoBig.setBackgroundColor(Color.GREEN);
//        relativeLayoutThreeBig.getChildAt(0).setAlpha(0);
//        relativeLayoutThreeBig.setBackgroundColor(Color.BLACK);



        relativeLayoutOneSmall.addView(imgSmallImageOne, layoutParamsImageSmall);
        linearLayoutParentTop.addView(relativeLayoutOneSmall);
        relativeLayoutTwoSmall.addView(imgSmallImageTwo, layoutParamsImageSmall);
        linearLayoutParentTop.addView(relativeLayoutTwoSmall);
        relativeLayoutThreeSmall.addView(imgSmallImageThree, layoutParamsImageSmall);
        linearLayoutParentTop.addView(relativeLayoutThreeSmall);
        top.addView(linearLayoutParentTop);


        RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) Utils.dpToPx(80));
        newParams.setMargins(0, (int) Utils.dpToPx(-50), 0, 0);
        newParams.addRule(RelativeLayout.BELOW, bottom.getId());
        top.setLayoutParams(newParams);
        top.invalidate();


        top.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = top.getScrollX(); //for horizontalScrollView
                int scrollY = top.getScrollY(); //for verticalScrollView
                //DO SOMETHING WITH THE SCROLL COORDINATES
                bottom.scrollTo((int) (scrollX * 0.7), scrollY);

                float currentPositionBottom = bottom.getScrollX();
                float pagesCountBottom = 3;
                float pageLengthInPxBottom = screenWidthInPixels;
                float currentPageBottom = currentPositionBottom / pageLengthInPxBottom;
                float wholeScrollHeight = bottom.getChildAt(0).getHeight();
                float wholeScrollWidth = bottom.getChildAt(0).getWidth();


                float currentPosition = top.getScrollX();
                float pagesCount = 3;
                float pageLengthInPx = screenWidthInPixels / 0.7f;
                float currentPage = currentPosition / pageLengthInPx;

                int page = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
//                Log.e("dddd","dedddd");
            }
        });

        top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
//                    float currentPosition = top.getScrollX();
//                    float pagesCount = 3;//_horizontalBar.getChildCount();
//                    float pageLengthInPx = screenWidthInPixels / 0.7f;
//                    float currentPage = currentPosition/pageLengthInPx;
//
//                    Boolean isBehindHalfScreen =  currentPage - (int)currentPage > 0.5;
//
//                    float edgePosition = 0;
//                    if(isBehindHalfScreen)
//                    {
//                        edgePosition = (int)(currentPage+1)*pageLengthInPx;
//                    }
//                    else
//                    {
//                        edgePosition = (int)currentPage*pageLengthInPx;
//                    }
//
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(top, "scrollX", top.getScrollX(), (int)edgePosition).setDuration(300);
//                    objectAnimator.start();
////                    top.scrollTo((int)edgePosition, 0);
                    float currentPosition = top.getScrollX();
                    float pagesCount = 3;
                    float pageLengthInPx = screenWidthInPixels / 0.7f;
                    float currentPage = currentPosition / pageLengthInPx;

                    int page = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
//                    top.scrollTo((int) (page * pageLengthInPx), 0);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(top, "scrollX", top.getScrollX(), (int) (page * pageLengthInPx)).setDuration(300);
                    objectAnimator.start();

                }
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.screen_scroll_test, container, false);


        return rootView;
    }
}
