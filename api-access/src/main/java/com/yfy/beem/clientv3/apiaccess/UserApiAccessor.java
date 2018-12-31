package com.yfy.beem.clientv3.apiaccess;


import com.yfy.beem.clientv3.datamodel.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface, acting as another abstraction layer than {@link com.yfy.beem.clientv3.apiaccess.service.ApiService}, that specifies an implementation that accesses the API.
 */
public interface UserApiAccessor {
    /**
     * Get all users registered on the REST API server.
     *
     * @return an immutable {@link List} of all {@link User}s.
     */
    List<User> getAllUsers();
    /**
     * Get the {@link User} matching id wrapped in an {@link Optional}, if it exists.
     *
     * @param id the id of {@link User}
     * @return an {@link Optional} containing either the matching {@link User} or {@code null}
     * */
    Optional<User> getUserMatchingId(Long id);
    /**
     * Get all {@link User}s matching <b>identically</b></b> the name specified, <b>case insensitive</b>.
     *
     * @param name the name of the {@link User}
     * @return an Immutable {@link List} of all {@link User}s matching name.
     * */
    List<User> getUsersMatchingName(String name);

    /**
     * Register the current user to the service. Use sparingly.
     * @param user the local {@link User} matching
     * */
    void registerSelf(User user);

}
