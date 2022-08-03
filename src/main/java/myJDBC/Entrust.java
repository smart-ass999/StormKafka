package myJDBC;
//定义原始数据对象
public class Entrust {

    public String getEntrust_date() {
        return entrust_date;
    }

    public void setEntrust_date(String entrust_date) {
        this.entrust_date = entrust_date;
    }

    public String getMarket_sector() {
        return market_sector;
    }

    public void setMarket_sector(String market_sector) {
        this.market_sector = market_sector;
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public void setStock_symbol(String stock_symbol) {
        this.stock_symbol = stock_symbol;
    }

    public String getEntrust_behavior() {
        return entrust_behavior;
    }

    public void setEntrust_behavior(String entrust_behavior) {
        this.entrust_behavior = entrust_behavior;
    }

    public String getEntrust_time() {
        return entrust_time;
    }

    public void setEntrust_time(String entrust_time) {
        this.entrust_time = entrust_time;
    }

    public String getEntrust_state() {
        return entrust_state;
    }

    public void setEntrust_state(String entrust_state) {
        this.entrust_state = entrust_state;
    }

    public String getEntrust_account() {
        return entrust_account;
    }

    public void setEntrust_account(String entrust_account) {
        this.entrust_account = entrust_account;
    }

    public String getEntrust_id() {
        return entrust_id;
    }

    public void setEntrust_id(String entrust_id) {
        this.entrust_id = entrust_id;
    }

    public String getEntrust_count() {
        return entrust_count;
    }

    public void setEntrust_count(String entrust_count) {
        this.entrust_count = entrust_count;
    }

    public String getEntrust_prise() {
        return entrust_prise;
    }

    public void setEntrust_prise(String entrust_prise) {
        this.entrust_prise = entrust_prise;
    }

    public String getEntrust_amount() {
        return entrust_amount;
    }

    public void setEntrust_amount(String entrust_amount) {
        this.entrust_amount = entrust_amount;
    }

    @Override
    public String toString() {
        return "{" +
                "entrust_date='" + entrust_date + '\'' +
                ", entrust_time='" + entrust_time + '\'' +
                ", market_sector='" + market_sector + '\'' +
                ", entrust_account='" + entrust_account + '\'' +
                ", stock_symbol='" + stock_symbol + '\'' +
                ", entrust_id='" + entrust_id + '\'' +
                ", entrust_behavior='" + entrust_behavior + '\'' +
                ", entrust_state='" + entrust_state + '\'' +
                ", entrust_count='" + entrust_count + '\'' +
                ", entrust_prise='" + entrust_prise + '\'' +
                ", entrust_amount='" + entrust_amount + '\'' +
                '}';
    }

    private String entrust_date = "未知";
    private String entrust_time = "未知";
    private String market_sector = "未知";
    private String entrust_account = "未知";
    private String stock_symbol = "未知";
    private String entrust_id = "未知";
    private String entrust_behavior = "未知";
    private String entrust_state = "未知";
    private String entrust_count = "未知";
    private String entrust_prise = "未知";
    private String entrust_amount = "未知";
}
