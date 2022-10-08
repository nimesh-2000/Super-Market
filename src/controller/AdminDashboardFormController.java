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

public class AdminDashboardFormController {
    public AnchorPane AdminDashboardContext;
    public AnchorPane mainContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize() throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IncomeForm.fxml"));
        mainContext.getChildren().add(parent);
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

    public void manageItemOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void mostSellingItemOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MostMovableItemForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void incomeOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IncomeForm.fxml"));
        mainContext.getChildren().add(parent);
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) AdminDashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminLoginForm");
    }
}
