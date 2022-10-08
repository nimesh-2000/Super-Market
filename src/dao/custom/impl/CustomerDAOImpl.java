package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> allCustomers = new ArrayList<>();
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7)));
        }
        return allCustomers;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Customer (customerId,customerTitle,CustomerName,CustomerAddress,city,province,postalCode) VALUES (?,?,?,?,?,?,?)", entity.getCustomerId(),entity.getCustomerTitle(), entity.getCustomerName(), entity.getCustomerAddress(),
                entity.getCity(),entity.getProvince(),entity.getPostalCode());
    }


    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Customer SET customerTitle=?,customerName=?,customerAddress=?,city=?,province=?,postalCode=? WHERE customerId=?",entity.getCustomerTitle(), entity.getCustomerName(), entity.getCustomerAddress(),entity.getCity(),
                entity.getProvince(),entity.getPostalCode(),entity.getCustomerId());
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer WHERE customerId=?", id);
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT customerId FROM Customer WHERE customerId=?", id).next();
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Customer WHERE customerId=?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT customerId FROM Customer ORDER BY customerId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("customerId");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public ArrayList<Customer> searchCustomers(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT * FROM Customer where CustomerId LIKE ? OR CustomerTitle LIKE ? OR CustomerName LIKE ? OR CustomerAddress LIKE ? OR City LIKE ? OR Province LIKE ? OR PostalCode LIKE ? ", enteredText, enteredText, enteredText, enteredText, enteredText, enteredText, enteredText);
        ArrayList<Customer> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Customer(result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7)

            ));
        }
        return list;
    }
}
