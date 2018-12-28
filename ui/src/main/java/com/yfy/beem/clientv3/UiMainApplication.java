package com.yfy.beem.clientv3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main UI application
 * */
//@SpringBootApplication
public class UiMainApplication extends Application {
    private static final Logger log = LoggerFactory.getLogger(UiMainApplication.class);

    private ConfigurableApplicationContext ctx;
    private Parent root;

    @Override
    public void init() throws Exception {
        super.init();
        ctx = SpringApplication.run(UiMainApplication.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        // set the loader's controller factory to the context
//        loader.setControllerFactory(ctx::getBean); not necessary with spring boot? idk
        root = loader.load();
        log.info("loaded root, {}", root);
        log.info("controller = {}", (Object) loader.getController());
    }

    @Override
    public void stop() throws Exception {
        ctx.stop();
        log.info("ctx {} stopped", ctx);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Beem Chat");
        Scene scene = new Scene(root, 800,800);
        primaryStage.setScene(scene);
        log.info("showing scene {}", scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        /*
        * DO NOT RUN THIS MAIN METHOD DIRECTLY, AS SPRING WILL THROW A SQLEXCEPTION FOR SOME REASON!
        * RUN THIS CLASS' MAIN METHOD FROM A PROXY CLASS
        * */
        launch(args);
    }
}
