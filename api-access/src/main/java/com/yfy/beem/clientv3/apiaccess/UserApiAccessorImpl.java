package com.yfy.beem.clientv3.apiaccess;

import com.yfy.beem.clientv3.apiaccess.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

/**
 * Class used to access the REST api endpoints.
 */
@Service
public class UserApiAccessor {
    private ApiService service;

    @Autowired
    public UserApiAccessor(ApiService service) {
        this.service = service;
    }
}
