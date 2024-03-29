package com.koo.generator.utils;

import com.koo.generator.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author gu.wq
 * @version 1.0
 * @type StageUtils
 * @desc
 * @date 2024/3/22 16:26
 */
public class StageUtils {

    public static Stage stage;

    public static void switchScene(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
    }
}
