package com.yfy.beem.clientv3.apiaccess;

import com.google.common.collect.ImmutableList;
import com.yfy.beem.clientv3.apiaccess.service.ApiService;
import com.yfy.beem.clientv3.apiaccess.util.PropertyName;
import com.yfy.beem.clientv3.crypto.CryptoUtils;
import com.yfy.beem.clientv3.datamodel.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class used to access the REST api endpoints.
 */
@Service
@SuppressWarnings("null")
public class UserApiAccessorImpl implements UserApiAccessor {
    private static final Logger log = LoggerFactory.getLogger(UserApiAccessorImpl.class);

    private ApiService service;

    @Autowired
    public UserApiAccessorImpl(ApiService service) {
        this.service = service;
        log.debug("finished initializing UserApiAccessorImpl, {}", this);
    }

    @Override
    public List<User> getAllUsers() {
        try {
            List<User> body = executeGetUsers(Map.of());
            log.debug("body = {}", body);
            return ImmutableList.copyOf(body);
        } catch (IOException e) {
            log.error("error in getAllUsers(): {}, please check configuration", e);
            return null;
        }
    }

    @Override
    public Optional<User> getUserMatchingId(Long id) {
        try {
            List<User> shouldContainOne = executeGetUsers(Map.of(PropertyName.ID, id.toString()));
            if (shouldContainOne != null) {
                if (shouldContainOne.size() == 1) {
                    return Optional.of(shouldContainOne.get(0));
                } else {
                    return Optional.empty();
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("error in getUsersMatchingId(): {}, please check configuration", e);
            return null;
        }
    }

    @Override
    public List<User> getUsersMatchingName(String name) {
        try {
            return executeGetUsers(Map.of(PropertyName.NAME, name));
        } catch (IOException e) {
            log.error("error in getUsersMatchingName(): {}, please check configuration", e);
            return null;
        }
    }

    @Override
    public void registerSelf(User user) {
        Long id = user.getId();
        String name = user.getName();
        String publicKey = CryptoUtils.keyToString(user.getPublicKey());
        log.info("saving user, id = {}, name = {}, publicKey = {}", id, name, publicKey);
        Call<Void> call = service.registerSelf(id, name, publicKey);
        try {
            Response<Void> response = call.execute();
            log.info("response, success = {}, code = {}", response.isSuccessful(), response.code());
        } catch (IOException e) {
            log.error("error in registerSelf(): {}, please check configuration", e);
        }

    }

    private List<User> executeGetUsers(Map<String, String> queryParams) throws IOException {
        log.info("executing getRegisteredUsers with queryParams = {}", queryParams);
        Call<List<User>> call = service.getRegisteredUsers(Map.of());
        Response<List<User>> response = call.execute();
        log.info("called executeGetUsers(), success = {}, code = {}", response.isSuccessful(), response.code());
        return response.body();
    }
}
