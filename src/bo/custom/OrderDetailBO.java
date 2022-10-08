package bo.custom;

import bo.SuperBO;
import dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDetailsDTO> searchOrderDetails(String enteredText) throws SQLException, ClassNotFoundException;
}
