package lk.ijse.stockmanage102.dto;

public class Supplier {
    private String supId;
    private String supName;
    private String shop;
    private String tel;

    public Supplier() {
    }

    public Supplier(String supId, String supName, String shop, String tel) {
        this.supId = supId;
        this.supName = supName;
        this.shop = shop;
        this.tel = tel;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supId='" + supId + '\'' +
                ", supName='" + supName + '\'' +
                ", shop='" + shop + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
