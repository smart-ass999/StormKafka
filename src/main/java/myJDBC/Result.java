package myJDBC;
//定义风险结果数据对象
public class Result {

        String exception_scenarios = "未知";
        String exception_date = "未知";
        String exception_time = "未知";
        String market_sector = "未知";
        String entrust_account = "未知";
        String stock_symbol = "未知";
        String description = "未知";
        String risk_level = "未知";

        public String getException_scenarios() {
                return exception_scenarios;
        }

        public void setException_scenarios(String exception_scenarios) {
                this.exception_scenarios = exception_scenarios;
        }

        public String getException_time() {
                return exception_time;
        }

        public void setException_time(String exception_time) {
                this.exception_time = exception_time;
        }

}
