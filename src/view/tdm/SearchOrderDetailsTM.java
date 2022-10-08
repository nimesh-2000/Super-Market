package view.tdm;

import java.math.BigDecimal;

public class SearchOrderDetailsTM {

    private String orderId;
    private String itemCode;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal total;

    public SearchOrderDetailsTM() {
    }

    public SearchOrderDetailsTM(String orderId, String itemCode, int qty, BigDecimal unitPrice, BigDecimal discount, BigDecimal total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SearchOrderDetailsTM{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
