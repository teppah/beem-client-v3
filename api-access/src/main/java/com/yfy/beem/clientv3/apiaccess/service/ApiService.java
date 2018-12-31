package com.yfy.beem.clientv3.apiaccess.service;

import com.yfy.beem.clientv3.apiaccess.util.ApiConstants;
import com.yfy.beem.clientv3.datamodel.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Interface for retrofit to access the user api
 * */
public interface ApiService {
    /**
     * Get all registered users.
     * @param queryParams can contain id or name
     * */
    @GET(ApiConstants.GET_USERS)
    Call<List<User>> getRegisteredUsers(@QueryMap Map<String, String> queryParams);

    /**
     * Register an user on the service. Only use to register self. Does not
     * care about the IP address, as it is the server that will resolve that
     * */
    @POST(ApiConstants.PUT_USER)
    Call<Void> registerSelf(@Query(value = "id") Long id,
                            @Query(value = "name") String name,
                            @Query(value = "publicKey") String publicKey);
}
