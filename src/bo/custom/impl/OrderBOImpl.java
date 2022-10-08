package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dto.OrderDTO;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);


    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> all = orderDAO.getAll();
        ArrayList<OrderDTO> allOrder = new ArrayList<>();

        for (Orders order : all) {
            allOrder.add(new OrderDTO(order.getOrderId(), order.getCustomerId()));
        }
        return allOrder;
    }

    @Override
    public boolean deleteOrders(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public ArrayList<OrderDTO> getAllSearchOrder(String enteredText) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> order = orderDAO.searchOrder(enteredText);

        ArrayList<OrderDTO> orDto = new ArrayList<>();

        for (Orders orderList : order) {

            orDto.add(new OrderDTO(
                    orderList.getOrderId(),
                    orderList.getCustomerId()
            ));
        }
        return orDto;
    }
}
