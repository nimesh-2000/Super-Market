package dao.custom;

import dao.CrudDAO;
import entity.Customer;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Orders,String> {
    public ArrayList<Orders> searchOrder(String enteredText) throws SQLException, ClassNotFoundException;
}
