package com.yfy.beem.clientv3.controller;

import com.yfy.beem.clientv3.apiaccess.UserApiAccessor;
import com.yfy.beem.clientv3.crypto.CryptoUtils;
import com.yfy.beem.clientv3.datamodel.SelfUser;
import com.yfy.beem.clientv3.datamodel.User;
import com.yfy.beem.clientv3.util.UserApiHelper;
import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Main UI controller
 */
@Component
public class MainUiController {
    private static final Logger log = LoggerFactory.getLogger(MainUiController.class);


    // ==== spring autowired components ====
    private ConfigurableApplicationContext ctx;
    private Environment env;
    private UserApiAccessor userApiAccessor;
    private UserApiHelper fxHelper;

    // ==== javafx nodes ====
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu connectionMenu;
    @FXML
    private MenuItem closeButton;
    // main table for contacts
    @FXML
    private TableView<User> msgTableView;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> latestChatColumn;
    // table for chat history
    @FXML
    private TableView<String> chatHistoryTableView;

    // table for apiUsers
    @FXML
    private TableView<User> apiUsersTable;
    @FXML
    private TableColumn<User, String> apiIdColumn;
    @FXML
    private TableColumn<User, String> apiNameColumn;
    @FXML
    private TableColumn<User, String> apiIpColumn;
    @FXML
    private TableColumn<User, String> apiPKeyColumn;

    @Autowired
    public MainUiController(ConfigurableApplicationContext ctx, UserApiAccessor userApiAccessor, Environment env, UserApiHelper fxHelper) {
        this.ctx = ctx;
        this.userApiAccessor = userApiAccessor;
        this.env = env;
        this.fxHelper = fxHelper;
    }

    public void initialize() throws UnknownHostException {
//        userApiAccessor.registerSelf(User
//                .builder()
//                .id(new Random().nextLong())
//                .name("Testing User 2")
//                .publicKey(CryptoUtils.generateKeyPair().getPublic())
//                .ipAddress(InetAddress.getByName("45.42.79.206"))
//                .build());
        // disable message selection
        chatHistoryTableView.setSelectionModel(null);

        //set text to display in msgTableView
        msgTableView.setItems(fxHelper.getObservableCurrentUsers());

        // setting the contents of apiUsersTable
        apiIdColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getId().toString()
        ));
        apiNameColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getName()
        ));
        apiIpColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getIpAddress().getHostAddress()
        ));
        apiPKeyColumn.setCellValueFactory(param -> new SimpleStringProperty(
                CryptoUtils.keyToString(
                        param.getValue().getPublicKey()
                )
        ));
        // set items
        apiUsersTable.setItems(fxHelper.getObservableCurrentUsers());
        // set multiselection model
        apiUsersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        log.info("main controller initialized, {}", this);
    }

    public void shutdownApplication() {
        Platform.exit();
    }

    public void refreshRegisteredUsers() {
        fxHelper.update();

    }

    public void handleDeleteUser() {
        log.info("deleteUser button clicked");

        ObservableList<User> selectedUsers = apiUsersTable.getSelectionModel().getSelectedItems();
        if (selectedUsers != null && !selectedUsers.isEmpty()) {
            selectedUsers.forEach(user -> {
                boolean success = userApiAccessor.deleteUser(user);
                log.info("deleting user = {}, success = {}", user, success);
            });
            refreshRegisteredUsers();
        } else {
            log.info("nothing selected");
        }

    }

    public void handlePostSelfToApi() {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Choose your name");
        inputDialog.setHeaderText("Please enter a name for this session");
        inputDialog.setContentText("Name:");
        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent()) {
            String name = result.get();
            log.info("name entered: {}", name);
            // if there was something entered in the field
            if (!name.isBlank()) {
                try {
                    KeyPair keyPair = CryptoUtils.generateKeyPair();
                    SelfUser self = SelfUser.selfBuilder()
                            .name(name)
                            .id(new Random().nextLong())
                            .ipAddress(InetAddress.getLocalHost())
                            .publicKey(keyPair.getPublic())
                            .privateKey(keyPair.getPrivate())
                            .build();
                    log.info("built user {}", self);
                    // if this doesn't throw an exception, it means that
                    // in this session, no previous users have been registered yet.
                    // if that is the case, continue the flow
                    boolean success = fxHelper.setCurrentUser(self);

                    if (success) {
                        userApiAccessor.registerSelf(self);
                        log.info("posted user");
                        refreshRegisteredUsers();

                        // registering this user as a bean to the ApplicationContext
                        // get the bean factory of ctx
                        ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();
                        // register the bean
                        beanFactory.registerSingleton(self.getClass().getCanonicalName(), self);
                    } else {
                        log.info("user has already been set");
                        Alert alert = new Alert(
                                Alert.AlertType.INFORMATION,
                                "User has already been set",
                                ButtonType.CLOSE);
                        alert.show();
                    }
                } catch (IOException e) {
                    log.error("ERROR, {}", e);
                }
            } else {
                // nothing was entered
                log.info("name is empty");
            }
        }


    }

}
