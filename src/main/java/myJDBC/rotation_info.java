package myJDBC;

/**
 * 用于日内回转计算累计和的类
 */
public class rotation_info {
    private double quantity_in=0;
    private double quantity_out=0;
    private String time="";
    private double price_in=0;
    private double price_out=0;
    private double amount_in=0;
    private double amount_out=0;

    public double getQuantity_in() {
        return quantity_in;
    }

    public void setQuantity_in(double quantity_in) {
        this.quantity_in = quantity_in;
    }
    public double getAmount_in() {
        return amount_in;
    }

    public void setAmount_in(double amount_in) {
        this.amount_in = amount_in;
    }
    public double getAmount_out() {
        return amount_out;
    }

    public double getQuantity_out() {
        return quantity_out;
    }

    public void setQuantity_out(double quantity_out) {
        this.quantity_out = quantity_out;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice_in() {
        return price_in;
    }

    public void setPrice_in(double price_in) {
        this.price_in = price_in;
    }

    public double getPrice_out() {
        return price_out;
    }

    public void setPrice_out(double price_out) {
        this.price_out = price_out;
    }

    @Override
    public String toString() {
        return "rotation_info{" +
                "quantity_in=" + quantity_in +
                ", quantity_out=" + quantity_out +
                ", time='" + time + '\'' +
                ", amount_in=" + price_in +
                ", amount_out=" + price_out +
                '}';
    }
}
