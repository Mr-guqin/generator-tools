package com.koo.generator;

import com.koo.generator.utils.StageUtils;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class GeneratorMainController {

    @FXML
    protected void loadButton() {
        StageUtils.switchScene("generator-view.fxml");
    }

    @FXML
    protected void exitButton() {
        Stage stage = StageUtils.stage;
        stage.close();
    }
}