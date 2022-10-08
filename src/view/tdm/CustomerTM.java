package view.tdm;

public class CustomerTM implements Comparable<CustomerTM>{
    private String customerId;
    private String customerTitle;
    private String customerName;
    private String customerAddress;
    private String city;
    private String province;
    private String postalCode;

    public CustomerTM() {
    }

    public CustomerTM(String customerId, String customerTitle, String customerName, String customerAddress, String city, String province, String postalCode) {
        this.customerId = customerId;
        this.customerTitle = customerTitle;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "customerId='" + customerId + '\'' +
                ", customerTitle='" + customerTitle + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    @Override
    public int compareTo(CustomerTM o) {
        return customerId.compareTo(o.getCustomerId());
    }
}
