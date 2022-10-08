package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierLoginFormController {
    public JFXTextField txtCashierName;
    public JFXPasswordField pwdCashierPassword;
    public AnchorPane cashierLoginContext;

    public void cashierLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtCashierName.getText().equals("Denuwan") && pwdCashierPassword.getText().equals("1234")) {
            new Alert(Alert.AlertType.CONFIRMATION, "Your Logging is Successful!..").show();
            setUi("CashierDashboardForm");
        }else {
            new Alert(Alert.AlertType.WARNING, "Your Logging is Fail!..").show();
        }
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) cashierLoginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void cashierLoginOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("AdminAndUserLoginForm");
    }
}
