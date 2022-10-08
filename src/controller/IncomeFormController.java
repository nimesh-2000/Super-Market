package controller;

import bo.BOFactory;
import bo.custom.IncomeBO;
import bo.custom.ItemBO;
import dto.CustomDTO;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tdm.CustomTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class IncomeFormController {
    public AnchorPane incomeContext;
    public TableView<CustomTM> tblIncome;
    private IncomeBO incomeBO = (IncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Income);

    public void initialize() throws SQLException, ClassNotFoundException {
        tblIncome.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblIncome.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("income"));

        loadIncome();
    }

    private void loadIncome() {
        tblIncome.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<CustomDTO> dailyIncome = incomeBO.income();
            for (CustomDTO dto : dailyIncome) {
                tblIncome.getItems().add(new CustomTM(dto.getOrderDate(), dto.getUnitPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
