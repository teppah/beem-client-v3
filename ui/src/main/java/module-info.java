open module ui {
    // javafx
    requires javafx.fxml;
    requires javafx.controls;

    // spring
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;

    // lombok
    // requires static lombok;
    // lombok does not work with Jigsaw

    exports com.yfy.beem.clientv3;

    requires slf4j.api;

}