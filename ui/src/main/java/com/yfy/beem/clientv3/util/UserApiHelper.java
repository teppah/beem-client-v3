package com.yfy.beem.clientv3.util;

import com.yfy.beem.clientv3.apiaccess.UserApiAccessor;
import com.yfy.beem.clientv3.datamodel.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Wrapper object that helps interfacing between api-access and the ui module. Contains
 * methods that wrap lists inside Observable stuff.
 */
@Component
public class UserApiHelper {
    private static final Logger log = LoggerFactory.getLogger(UserApiHelper.class);

    private UserApiAccessor userApiAccessor;

    private ObservableList<User> currentUsers;

    // the user info for the current session
    private User self;

    public UserApiHelper(UserApiAccessor userApiAccessor) {
        this.userApiAccessor = userApiAccessor;
        currentUsers = FXCollections.observableArrayList(
                userApiAccessor.getAllUsers()
        );
    }

    public ObservableList<User> getObservableCurrentUsers() {
        update();
        return currentUsers;
    }

    public void update() {
        currentUsers.clear();
        currentUsers.addAll(userApiAccessor.getAllUsers());
    }

    public boolean setCurrentUser(User user) {
        if (this.self == null) {
            this.self = user;
            log.info("set current user to {}", user);
            return true;
        } else {
            return false;
        }
    }

    public User getCurrentUser() {
        log.debug("returning current user {}", self);
        return self;
    }

}
