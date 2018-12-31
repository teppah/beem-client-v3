package com.yfy.beem.clientv3.controller;

import com.yfy.beem.clientv3.apiaccess.UserApiAccessor;
import com.yfy.beem.clientv3.apiaccess.UserApiAccessorImpl;
import com.yfy.beem.clientv3.datamodel.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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
    // table for chat history
    @FXML
    private TableView<String> chatHistoryTableView;

    @Autowired
    public MainUiController(ConfigurableApplicationContext ctx,UserApiAccessor userApiAccessor, Environment env) {
        this.ctx = ctx;
        this.userApiAccessor = userApiAccessor;
        this.env = env;
    }

    public void initialize() {
        chatHistoryTableView.setSelectionModel(null);
        log.info("main controller initialized, {}", this);
    }

    public void showConnectDialog() {
        log.info("showing connection dialog");



        Alert alert = new Alert(Alert.AlertType.INFORMATION);


    }


}
