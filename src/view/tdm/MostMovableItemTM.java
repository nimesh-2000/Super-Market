package view.tdm;

import java.math.BigDecimal;

public class MostMovableItemTM {
    private String itemCode;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
    private int orderQty;

    public MostMovableItemTM() {
    }

    public MostMovableItemTM(String itemCode, String description, BigDecimal unitPrice, int qtyOnHand, int orderQty) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.orderQty = orderQty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public String toString() {
        return "MostMovableItemTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", orderQty=" + orderQty +
                '}';
    }
}
