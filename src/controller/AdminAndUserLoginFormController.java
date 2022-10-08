package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAndUserLoginFormController {


    public AnchorPane adminCashierContext;

    public void adminLoginOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminLoginForm");
    }

    public void cashierLoginOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierLoginForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) adminCashierContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
 