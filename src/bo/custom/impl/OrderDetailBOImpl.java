package bo.custom.impl;

import bo.custom.OrderDetailBO;
import dao.custom.QueryDAO;
import dao.custom.impl.QueryDAOImpl;
import dto.OrderDetailsDTO;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    QueryDAO queryDAO = new QueryDAOImpl();

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetailsDTO> searchOrderDetails(String enteredText) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> orderDetails = queryDAO.searchOrderDetail(enteredText);


        ArrayList<OrderDetailsDTO> orDetailDto=new ArrayList<>();

        for (OrderDetails ordersList : orderDetails) {

            orDetailDto.add(new OrderDetailsDTO(ordersList.getOrderId(),
                    ordersList.getItemCode(),
                    ordersList.getOrderQty(),
                    ordersList.getUnitPrice(),
                    ordersList.getDiscount(),
                    ordersList.getTotal()
            ));
        }
        return orDetailDto;
    }
}
