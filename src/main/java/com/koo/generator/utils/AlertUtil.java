package com.koo.generator.utils;

import javafx.scene.control.Alert;

/**
 * @author gu.wq
 * @version 1.0
 * @type AlertUtil
 * @desc
 * @date 2024/3/27 9:44
 */
public class AlertUtil {

   private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
   private static Alert warningAlert = new Alert(Alert.AlertType.WARNING);
   private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
   public static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
       Alert alert = infoAlert;
       switch (alertType) {
           case INFORMATION:
               alert = infoAlert;
               break;
           case WARNING:
               alert = warningAlert;
               break;
           case ERROR:
               alert = errorAlert;
               break;
       }
       alert.setTitle(title);
       alert.setHeaderText(header);
       alert.setContentText(content);
       alert.showAndWait();
   }
}
