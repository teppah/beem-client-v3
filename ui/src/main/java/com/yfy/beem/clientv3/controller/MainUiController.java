package com.yfy.beem.clientv3.controller;

import com.yfy.beem.clientv3.apiaccess.UserApiAccessor;
import com.yfy.beem.clientv3.crypto.CryptoUtils;
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
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
        apiIdColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(
                        param.getValue().getId().toString()
                );
            }
        });
        apiNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(
                        param.getValue().getName()
                );
            }
        });
        apiIpColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(
                        param.getValue().getIpAddress().getHostAddress()
                );
            }
        });
        apiPKeyColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(
                        CryptoUtils.keyToString(
                                param.getValue().getPublicKey()
                        )
                );
            }
        });
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

    public void handlePostToApi() {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Choose your name");
        inputDialog.setHeaderText("Please enter a name for this session");
        inputDialog.setContentText("Name:");
        Optional<String> result = inputDialog.showAndWait();
        result.ifPresent(name -> log.info("name entered: {}", name));
    }

}
