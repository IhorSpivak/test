package llc.net.mydutyfree.interfaces;

import com.google.gson.JsonObject;

import llc.net.mydutyfree.response.AccountSignIn;
import llc.net.mydutyfree.response.AccountSignUp;
import llc.net.mydutyfree.response.CheckDiscountResponse;
import llc.net.mydutyfree.response.GetBannersResponse;
import llc.net.mydutyfree.response.GetBrandsResponse;
import llc.net.mydutyfree.response.GetCategoriesResponse;
import llc.net.mydutyfree.response.GetCurrencyResponse;
import llc.net.mydutyfree.response.GetFlightsResponse;
import llc.net.mydutyfree.response.GetLanguagesResponse;
import llc.net.mydutyfree.response.GetOneOrderResponse;
import llc.net.mydutyfree.response.GetOneProductResponse;
import llc.net.mydutyfree.response.GetOrdersResponse;
import llc.net.mydutyfree.response.GetProductsResponse;
import llc.net.mydutyfree.response.GetStoreDiscountResponse;
import llc.net.mydutyfree.response.GetStores;
import llc.net.mydutyfree.response.OrderCheckoutResponse;
import llc.net.mydutyfree.response.ProfileGetAttributes;
import llc.net.mydutyfree.response.ProfileSetAttributes;
import llc.net.mydutyfree.response.SaveDeviceToken;
import llc.net.mydutyfree.response.ShowPartnerField;
import llc.net.mydutyfree.response.WishListAddOrRemoveProductResponse;
import llc.net.mydutyfree.response.WishListClearResponse;
import llc.net.mydutyfree.response.WishListGetAllResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gorf on 7/15/16.
 */
public interface PostInterfaceApi {

    @POST("front?v=1")
    Call<GetBannersResponse> getBanners(@Body JsonObject requestJson);

    @POST("front?v=1")
    Call<GetBrandsResponse> getBrands(@Body JsonObject requestJson);

    @POST("front?v=1")
    Call<GetCategoriesResponse> getCategories(@Body JsonObject requestJson);

    @POST("front?v=1")
    Call<GetFlightsResponse> getFlights(@Body JsonObject requestJson);

    @POST("front?v=1")
    Call<CheckDiscountResponse> checkDiscount(@Body JsonObject requestJson);

    @POST("front?v=1")
    Call<GetStoreDiscountResponse> getStoreDiscount(@Body JsonObject requestJson);

    @POST("front?v=2")
    Call<GetBannersResponse> getAllBanners(@Body JsonObject requestJson);

    @POST("front?v=1")
    Call<GetLanguagesResponse> getLanguages(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<GetCurrencyResponse> getCurrency(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<GetStores> getStores(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<SaveDeviceToken> saveDeviceToken(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<GetProductsResponse> getProducts(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<GetOneProductResponse> getOneProduct(@Body com.google.gson.JsonObject requestJson);

    @FormUrlEncoded
    @POST("front?v=1")
    Call<GetLanguagesResponse> getLanguages(@Field("body")com.google.gson.JsonObject body,
                                            @Field("auth_key")String key,
                                            @Field("action")String action,
                                            @Field("store_id")String storeId,
                                            @Field("lang_code")String langCode);

    @POST("front?v=1")
    Call<AccountSignIn> accountSignIn(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<AccountSignUp> accountSignUp(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<ProfileGetAttributes> profileGetAttributes(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<ProfileSetAttributes> profileSetAttributes(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<WishListGetAllResponse> wishListGetAll(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<WishListClearResponse> wishListClear(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<WishListAddOrRemoveProductResponse> wishListAddProduct(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<WishListAddOrRemoveProductResponse> wishListRemoveProduct(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<GetOrdersResponse> ordersGetAll(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<GetOneOrderResponse> ordersGetOne(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<ShowPartnerField> ordersShowPartnerField(@Body com.google.gson.JsonObject requestJson);

    @POST("front?v=1")
    Call<OrderCheckoutResponse> orderCheckout(@Body com.google.gson.JsonObject requestJson);

    // Another endpoints...

//    @POST("front?v=1")
//    Call<ResponseBody> getStoresAsString(@Body com.google.gson.JsonObject requestJson);
}
