package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);


    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustomerId(),customer.getCustomerTitle(),customer.getCustomerName(),customer.getCustomerAddress(),
                    customer.getCity(),customer.getProvince(),customer.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCustomerId(),dto.getCustomerTitle(),dto.getCustomerName(),dto.getCustomerAddress(),
                dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustomerId(),dto.getCustomerTitle(),dto.getCustomerName(),dto.getCustomerAddress(),
                dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public boolean customerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> searchCustomers(String enteredText) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.searchCustomers(enteredText);
        ArrayList<CustomerDTO> customerDto=new ArrayList<>();

        for (Customer customer : customers) {
            customerDto.add(new CustomerDTO(customer.getCustomerId(),
                    customer.getCustomerTitle(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            ));
        }
        return customerDto;
    }
}
