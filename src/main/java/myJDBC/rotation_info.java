package myJDBC;

/**
 * 用于日内回转计算累计和的类
 */
public class rotation_info {
    private double quantity_in=0;
    private double quantity_out=0;
    private String time="";
    private double amount_in=0;
    private double amount_out=0;

    public double getQuantity_in() {
        return quantity_in;
    }

    public void setQuantity_in(double quantity_in) {
        this.quantity_in = quantity_in;
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

    public double getAmount_in() {
        return amount_in;
    }

    public void setAmount_in(double amount_in) {
        this.amount_in = amount_in;
    }

    public double getAmount_out() {
        return amount_out;
    }

    public void setAmount_out(double amount_out) {
        this.amount_out = amount_out;
    }

    @Override
    public String toString() {
        return "rotation_info{" +
                "quantity_in=" + quantity_in +
                ", quantity_out=" + quantity_out +
                ", time='" + time + '\'' +
                ", amount_in=" + amount_in +
                ", amount_out=" + amount_out +
                '}';
    }
}
