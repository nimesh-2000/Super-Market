package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.OrderDAO;
import entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT orderId,customerId FROM Orders");
        ArrayList<Orders> searchOrders = new ArrayList();
        while (rst.next()) {
            searchOrders.add(new Orders(rst.getString(1), rst.getString(2)));
        }
        return searchOrders;
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO `Orders` (orderId, orderDate, customerId) VALUES (?,?,?)", entity.getOrderId(), entity.getOrderDate(), entity.getCustomerId());
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT orderId FROM `Orders` WHERE orderId=?", oid).next();
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT orderId FROM `Orders` ORDER BY orderId DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("orderId").replace("OID-", "")) + 1)) : "OID-001";
    }

    @Override
    public ArrayList<Orders> searchOrder(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT orderId,customerId from Orders where orderId LIKE ? OR customerId LIKE ? ORDER BY orderId DESC", enteredText, enteredText);
        ArrayList<Orders> orList = new ArrayList<>();


        while (result.next()) {
            orList.add(new Orders(
                    result.getString(1),
                    result.getString(2)

            ));
        }
        return orList;
    }
}
