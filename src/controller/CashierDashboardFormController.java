package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class CashierDashboardFormController {
    public AnchorPane cashierDashboardContext;
    public AnchorPane mainCContext;
    public Label lblDate;
    public Label lblTime;


    public void initialize() throws IOException {
        mainCContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
        mainCContext.getChildren().add(parent);
        loadDateAndTime();
    }
    private void loadDateAndTime() {
        Calendar clndr = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("aa");



        Timeline clock = new Timeline(new KeyFrame(Duration.INDEFINITE.ZERO, e -> {
            LocalTime currenttime = LocalTime.now();
            LocalDate currentdate = LocalDate.now();
            lblTime.setText(currenttime.getHour() + ":" + currenttime.getMinute() + ":" + currenttime.getSecond()+"  "+format1.format(clndr.getTime()));
            lblDate.setText(currentdate.getDayOfWeek()+","+ currentdate.getMonth() +" " + currentdate.getDayOfMonth() + "," + currentdate.getYear());

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void manageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        mainCContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
        mainCContext.getChildren().add(parent);
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        mainCContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"));
        mainCContext.getChildren().add(parent);
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) cashierDashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierLoginForm");
    }

    public void searchOrderOnAction(ActionEvent actionEvent) throws IOException {
        mainCContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ManageCustomerOrdersForm.fxml"));
        mainCContext.getChildren().add(parent);
    }
}
