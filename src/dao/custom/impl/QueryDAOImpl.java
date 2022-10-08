package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.QueryDAO;
import entity.CustomEntity;
import entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> mostMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT Item.itemCode,description,unitPrice,qtyOnHand,SUM(orderQty) from Item inner join OrderDetail on Item.itemCode = OrderDetail.itemCode GROUP BY itemCode ORDER BY SUM(orderQTY) DESC");
        ArrayList<CustomEntity> mostMovable = new ArrayList();
        while (rst.next()) {
            mostMovable.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getBigDecimal(3), rst.getInt(4), rst.getInt(5)));
        }
        return mostMovable;
    }

    @Override
    public ArrayList<OrderDetails> searchOrderDetail(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT OrderDetail.orderId,OrderDetail.itemCode,OrderDetail.orderQty,Item.unitPrice,OrderDetail.discount from OrderDetail INNER JOIN  Item on OrderDetail.itemCode = Item.itemCode where OrderDetail.orderId LIKE ? OR OrderDetail.itemCode LIKE ? OR OrderDetail.orderQty LIKE ? OR OrderDetail.discount LIKE ?;", enteredText, enteredText, enteredText, enteredText);
        ArrayList<OrderDetails> orList = new ArrayList<>();


        while (result.next()) {
            double total = (result.getInt(3) * result.getDouble(4) - result.getDouble(5));
            orList.add(new OrderDetails(result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getBigDecimal(4),
                    result.getBigDecimal(5),
                    total

            ));
        }
        return orList;
    }

    @Override
    public ArrayList<CustomEntity> income() throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT orderDate,SUM(orderQTY*unitPrice-o.discount)FROM Orders INNER JOIN orderDetail o on Orders.orderId = o.OrderID INNER JOIN Item i on o.ItemCode = i.ItemCode GROUP BY OrderDate ORDER BY OrderDate;");
        ArrayList<CustomEntity> orList = new ArrayList<>();


        while (result.next()) {
            // double total=(result.getInt(2)*result.getDouble(1)-result.getDouble(3));
            orList.add(new CustomEntity(result.getDate(1).toLocalDate(),
                    result.getBigDecimal(2))

            );
        }

        return orList;
    }


}
