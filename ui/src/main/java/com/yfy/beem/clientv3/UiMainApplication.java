package com.yfy.beem.clientv3;

import com.yfy.beem.clientv3.controller.MainUiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * Main UI application, run from here
 * */
@SpringBootApplication
public class UiMainApplication extends Application {
    private static final Logger log = LoggerFactory.getLogger(UiMainApplication.class);

    private ConfigurableApplicationContext ctx;
    private Parent root;

    @Override
    public void init() throws Exception {
        super.init();
//        new SpringApplicationBuilder().main(UiMainApplication.class).build().run()
        ctx = SpringApplication.run(UiMainApplication.class);
        log.info("finished initializing ctx {}", ctx);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
//        System.out.println(ctx.getBean(MainUiController.class));
        // set the loader's controller factory to the context
        loader.setControllerFactory(ctx::getBean);
        root = loader.load();
        log.debug("loaded root, {}", root);
        log.debug("controller = {}", (Object) loader.getController());
    }

    @Override
    public void stop() throws Exception {
        ctx.stop();

        log.info("ctx {} stopped", ctx);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Beem Chat");
        Scene scene = new Scene(root, 1440,900);
        primaryStage.setScene(scene);
        log.info("showing scene {}", scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
