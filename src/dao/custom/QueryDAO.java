package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.CustomEntity;
import entity.Customer;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> mostMovableItem() throws SQLException, ClassNotFoundException;
    ArrayList<OrderDetails> searchOrderDetail(String enteredText) throws SQLException, ClassNotFoundException;
    ArrayList<CustomEntity> income()throws SQLException, ClassNotFoundException;
}
