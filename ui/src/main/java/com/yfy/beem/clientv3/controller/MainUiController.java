package com.yfy.beem.clientv3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * Main UI controller
 * */
@Component
public class MainUiController {
    private static final Logger log = LoggerFactory.getLogger(MainUiController.class);
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem testItem;
    @FXML
    private TableView msgTableView;

    public void initialize() {
        log.info("main controller initialized, {}", this);
    }

    public void testClick() {
        log.info("clicked, something happened");
    }


}
