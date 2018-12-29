open module ui {
    // javafx
    requires javafx.fxml;
    requires javafx.controls;

    // spring
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;

    requires slf4j.api;

    requires apiaccess;
    requires java.sql;


    // lombok
    // requires static lombok;
    // lombok does not work with Jigsaw

    exports com.yfy.beem.clientv3;



}