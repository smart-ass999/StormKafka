package myJDBC;
//定义风险结果数据对象
public class Result {

    private String exception_scenarios = "未知";
        private String exception_date = "未知";
        private String exception_time = "未知";
        private String market_sector = "未知";
        private String entrust_account = "未知";
        private String stock_symbol = "未知";
        private String description = "未知";
        private String risk_level = "null";




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

        public String getException_date() {
                return exception_date;
        }

        public void setException_date(String exception_date) {
                this.exception_date = exception_date;
        }

        public String getMarket_sector() {
                return market_sector;
        }

        public void setMarket_sector(String market_sector) {
                this.market_sector = market_sector;
        }

        public String getEntrust_account() {
                return entrust_account;
        }

        public void setEntrust_account(String entrust_account) {
                this.entrust_account = entrust_account;
        }

        public String getStock_symbol() {
                return stock_symbol;
        }

        public void setStock_symbol(String stock_symbol) {
                this.stock_symbol = stock_symbol;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getRisk_level() {
                return risk_level;
        }

        public void setRisk_level(String risk_level) {
                this.risk_level = risk_level;
        }
}
