package com.magdy.abo100.helpers;

import com.magdy.abo100.models.area_models.AreaResponse;
import com.magdy.abo100.models.cart.AddCartResponse;
import com.magdy.abo100.models.cart.CartResponse;
import com.magdy.abo100.models.cart.delete_cart_models.DeleteCartResponse;
import com.magdy.abo100.models.categories.CategoriesResponse;
import com.magdy.abo100.models.gifts_models.GiftsResponse;
import com.magdy.abo100.models.login_models.EditNameResponse;
import com.magdy.abo100.models.login_models.LogInSendModel;
import com.magdy.abo100.models.login_models.LoginResponse;
import com.magdy.abo100.models.message_models.MessageResponse;
import com.magdy.abo100.models.offers_models.OffersResponse;
import com.magdy.abo100.models.order_models.StoreOrderResponse;
import com.magdy.abo100.models.registration_models.RegistrationResponse;
import com.magdy.abo100.models.registration_models.RegistrationSendModel;
import com.magdy.abo100.models.search_products.SearchProductResponse;
import com.magdy.abo100.models.slider_models.SliderResponse;
import com.magdy.abo100.models.wishlist_models.WishlistResponse;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @POST(StaticMembers.login)
    Call<LoginResponse> login(@Body LogInSendModel logInSendModel);

    @POST(StaticMembers.register)
    Call<RegistrationResponse> register(@Body RegistrationSendModel registrationSendModel);

    @POST(StaticMembers.WISHLIST_ACTION)
    Call<WishlistResponse> toggleWishlist(@Path(StaticMembers.ID) long id);

    @GET(StaticMembers.PRODUCT)
    Call<SearchProductResponse> getProducts(@QueryMap HashMap<String, String> params);

    @GET(StaticMembers.GIFT_GET)
    Call<GiftsResponse> getGifts();

    @GET(StaticMembers.OFFERS)
    Call<OffersResponse> getOffers();

    @GET(StaticMembers.WISHLIST)
    Call<SearchProductResponse> getWishList(@QueryMap HashMap<String, String> params);

    @GET(StaticMembers.CATEGORY)
    Call<CategoriesResponse> getCategories();

    @GET(StaticMembers.CART)
    Call<CartResponse> getCart();

    @GET(StaticMembers.SLIDER)
    Call<SliderResponse> getSlider();

    @FormUrlEncoded
    @POST(StaticMembers.EDIT_CART)
    Call<AddCartResponse> addOrEditCart(@Field(StaticMembers.PRODUCT_ID) String id,
                                        @Field(StaticMembers.QUANTITY) int q,
                                        @Field(StaticMembers.UNIT_SIZE) int unitSize,
                                        @Field(StaticMembers.COLOR) int color
    );

    @FormUrlEncoded
    @POST(StaticMembers.EDIT_CART)
    Call<AddCartResponse> addOrEditCart(@Field(StaticMembers.PRODUCT_ID) String id,
                                        @Field(StaticMembers.QUANTITY) int q,
                                        @Field(StaticMembers.COLOR) int color
    );


    @GET(StaticMembers.AREA)
    Call<AreaResponse> getAreas();

    @POST(StaticMembers.DELETE_CART)
    Call<DeleteCartResponse> deleteCartItem(@Path(StaticMembers.ID) String id);

    @FormUrlEncoded
    @POST(StaticMembers.EDIT_NAME)
    Call<EditNameResponse> editField(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST(StaticMembers.CONTACT)
    Call<MessageResponse> sendMessage(@Field(StaticMembers.MESSAGE) String message);

    @FormUrlEncoded
    @POST(StaticMembers.GIFT)
    Call<MessageResponse> addPromo(@Field(StaticMembers.CODE) String code);

    @POST(StaticMembers.ORDER_STORE)
    Call<StoreOrderResponse> storeOrder(@Body RequestBody file);

   /*
    @POST(StaticMembers.login)
    Call<LoginResponse> login(@Body LogInSendModel logInSendModel);

    @FormUrlEncoded
    @POST(StaticMembers.getPinCode)
    Call<PinCodeResponse> getPinCode(@Field(StaticMembers.EMAIL) String email);

    @FormUrlEncoded
    @POST(StaticMembers.verifyPinCode)
    Call<TestCodeResponse> verifyPinCode(@Field(StaticMembers.EMAIL) String email, @Field(StaticMembers.PIN_CODE) String pincode, @Field(StaticMembers.PIN_CODE_TOKEN) String pincodetoken);

    @POST(StaticMembers.reset)
    Call<ResetPasswordResponse> reset(@Body SignUpSendModel sendModel);*/
}
