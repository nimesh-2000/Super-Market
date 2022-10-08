package view.tdm;

public class SearchOrderTM {

    private String customerId;
    private String orderId;

    public SearchOrderTM() {
    }

    public SearchOrderTM(String customerId, String orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "SearchOrderTM{" +
                "customerId='" + customerId + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
