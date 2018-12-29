package com.yfy.beem.clientv3.apiaccess.mappings;

/**
 * Class that holds the mapping constants for the API.
 * */
public class ApiConstants {
    // do not let anyone instantiate
    private ApiConstants() {
        throw new UnsupportedOperationException("nah dude");
    }

    public static final String BASE_URL = "http://ec2-35-183-203-48.ca-central-1.compute.amazonaws.com:8080/";
    public static final String API_PREFIX = "api/";

    public static final String GET_USERS = API_PREFIX + "users";
    public static final String PUT_USER = API_PREFIX + "users";
}
