package llc.net.mydutyfree.utils;

/**
 * Created by gorf on 2/18/16.
 */
public class Const {

    public static String MIXPANEL_PROJECT_TOKEN = "a6cb98711da5ae33156972967f476754";


    public static class URLS {
        public static String BASE_URL = "http://api2.mydutyfree.net/front";

        public static String URL = "http://app.mydutyfree.net/front?v=1";
    }
    public static String API_KEY = "jYy7UGuiIUjttYUjjBvsLkMJh";

    public static class Actions {

        public static class WishList {
            public static String GET_ALL = "wishlist/get-all";
            public static String ADD_PRODUCT = "wishlist/add-product";
            public static String REMOVE_PRODUCT = "wishlist/remove-product";
            public static String CLEAR = "wishlist/clear";
        }

        public static class Orders {
            public static String GET_ALL = "orders/get-all";
            public static String GET_ONE = "orders/get-one";
            public static String CANCEL = "orders/cancel";
            public static String CHANGE_FLIGHT_DATA = "orders/change-flight-data";
            public static String CHANGE_PRODUCTS_LIST = "orders/change-products-list";
            public static String NEED_SHOW_PARTNER_FIELD = "orders/need-show-partner-field";
        }

        public static class Status {
            public static String SUCCESS = "success";
            public static String FAILURE = "fail";
        }

        public static class Profile {
            public static String GET_ATTRIBUTES = "profile/get-attributes";
            public static String SET_ATTRIBUTES = "profile/change-attributes";
        }

        public static class Products {
            public static String GET_FILTERED = "products/get-filtered";
            public static String GET_ONE = "products/get-one";
            public static String GET_ALL = "products/get-all";
            public static String SEARCH = "products/search";
        }

        public static class Account {
            public static String SIGN_IN = "account/sign-in";
            public static String SIGN_IN_SOCIAL = "account/sign-in-social";
            public static String SIGN_UP = "account/sign-up";
        }

        public static class Categories {
            public static String GET_ALL = "categories/get-all";
        }

        public static class Content {
            public static String GET_PAGES = "content/get-pages";
            public static String GET_BANNERS = "content/get-banners";
            public static String GET_BANNERS_ALL = "content/get-banners-all";
            public static String GET_STORE_DISCOUNT = "content/get-store-discount";
            public static String GET_FLIGHTS = "content/get-flights";
            public static String GET_BRANDS = "content/get-brands";
            public static String GET_CURRENCY_LIST = "content/get-currency-list";
            public static String GET_LANGUAGES = "content/get-languages";
            public static String GET_STORES = "content/get-stores";
        }

        public static class Brands {
            public static String GET_FOR_CATEGORY = "content/get-brands";
        }

        public static class Users {
            public static String SAVE_DEVICE_TOKEN = "users/save-device-token";
        }

        public static class Order {
            public static String CHECK_DISCOUNT_CODE = "orders/check-discount-code";
            public static String CHECKOUT = "orders/checkout";
            public static String CANCEL_REQUEST = "orders/cancel-request";
        }
    }
}
