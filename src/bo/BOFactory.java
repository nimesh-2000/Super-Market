package bo;

import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl(); // SuperBO bo =new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl(); // SuperBO bo = new ItemBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl(); //SuperBO bo = new PurchaseOrderBOImpl();
            case MostMovableItem:
                return new MostMovableItemBOImpl();
            case Order:
                return new OrderBOImpl();
            case OrderDetail:
                return new OrderDetailBOImpl();
            case Income:
                return new IncomeBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, PURCHASE_ORDER,MostMovableItem,Order,OrderDetail,Income
    }
}
