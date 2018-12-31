package com.yfy.beem.clientv3.apiaccess.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yfy.beem.clientv3.apiaccess.gson.UserTypeAdapter;
import com.yfy.beem.clientv3.apiaccess.util.ApiConstants;
import com.yfy.beem.clientv3.apiaccess.service.ApiService;
import com.yfy.beem.clientv3.datamodel.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Configuration class for the various dependencies of {@link com.yfy.beem.clientv3.apiaccess.UserApiAccessorImpl}
 * */
@Configuration
public class ApiAccessorConfiguration {
    @Bean
    public ApiService retrofitApiService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService service = retrofit.create(ApiService.class);
        return service;

    }

}
