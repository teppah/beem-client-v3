package com.yfy.beem.clientv3.controller;

import com.yfy.beem.clientv3.apiaccess.UserApiAccessor;
import com.yfy.beem.clientv3.crypto.CryptoUtils;
import com.yfy.beem.clientv3.datamodel.User;
import com.yfy.beem.clientv3.util.UserApiHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    private MenuItem connectMenuItem;
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

    @Autowired
    public MainUiController(ConfigurableApplicationContext ctx, UserApiAccessor userApiAccessor, Environment env, UserApiHelper fxHelper) {
        this.ctx = ctx;
        this.userApiAccessor = userApiAccessor;
        this.env = env;
        this.fxHelper = fxHelper;
    }

    public void initialize() throws UnknownHostException {
        userApiAccessor.registerSelf(User
                .builder()
                .id(new Random().nextLong())
                .name("Testing User 2")
                .publicKey(CryptoUtils.generateKeyPair().getPublic())
                .ipAddress(InetAddress.getByName("45.42.79.206"))
                .build());
        // disable message selection
        chatHistoryTableView.setSelectionModel(null);

        //set text to display in msgTableView
        msgTableView.setItems(fxHelper.getObservableCurrentUsers());


        log.info("main controller initialized, {}", this);
    }

    public void showConnectDialog() {
        log.info("showing connection dialog");

        Dialog<User> dialog = new Dialog<>();

        TableView<User> activeUsersTable = new TableView<>();
        activeUsersTable.setItems(fxHelper.getObservableCurrentUsers());

        TableColumn<User, String> userId = new TableColumn<>();
        userId.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId().toString()));

        TableColumn<User, String> userName = new TableColumn<>();
        userName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(param.getValue().getName());
            }
        });
        TableColumn<User, String> ipAddr = new TableColumn<>();
        ipAddr.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(param.getValue().getIpAddress().toString());
            }
        });


        activeUsersTable.getColumns().addAll(userId, userName, ipAddr);

        dialog.show();



    }


}
