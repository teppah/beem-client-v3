package com.yfy.beem.clientv3.util;

import com.yfy.beem.clientv3.apiaccess.UserApiAccessor;
import com.yfy.beem.clientv3.datamodel.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Wrapper object that helps interfacing between api-access and the ui module. Contains
 * methods that wrap lists inside Observable stuff.
 * */
@Component
public class UserHelper {
    private static final Logger log = LoggerFactory.getLogger(UserHelper.class);

    private UserApiAccessor userApiAccessor;

    private ObservableList<User> currentUsers;

    public UserHelper(UserApiAccessor userApiAccessor) {
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
        userApiAccessor.getAllUsers().forEach(user -> {
            if (!currentUsers.contains(user)) {
                currentUsers.add(user);
            }
        });
    }

}
