import java.util.Objects;

public class Town {

    private String name = "Цветочный город";

    {
        LotActions townStat = new LotActions() {
            @Override
            public void showBalance() {

                System.out.println("> У города - " + name + " нет казны. Он живёт на энтузиазме жителей.");
            }
        };
        townStat.showBalance();
    }

    public static class GiganticPlantSociety {
        private static double stockPriceForBuying;
        private static double stockPriceForSold;
        private static final String companyDomain = " ОАО \"ОГР\"";
        private static TypeOfLocation type = TypeOfLocation.GIGANTIC_PLANT_SOCIETY;
        protected static WalletBalance storage;

        public static void setPrices(double _stockPriceForBying, double _stockPriceForSold) {
            stockPriceForBuying = _stockPriceForBying;
            stockPriceForSold = _stockPriceForSold;
            System.out.println("> " + companyDomain + " установило цены на акции:");
            System.out.println("    Цена для покупки = " + stockPriceForBuying + " | Цена для продажи = " + stockPriceForSold);
        }

        public static void printStocks(int amount) {
            if (amount < 0) throw new PrintStocksException(amount);
            storage = new WalletBalance(0, amount);
            System.out.println("> " + companyDomain + " напечатало " + amount + " акций");
        }

        public static double getStockPriceForBuying() {
            return stockPriceForBuying;
        }

        public static double getStockPriceForSold() {
            return stockPriceForSold;
        }

        public static void showBalance() {
            System.out.println("> Для продажи доступно " + storage.getStocks() + " акций");
        }
    }

    public static class Bank {
        private static double bankRate;
        private static final String companyDomain = "Городской банк";
        private static TypeOfLocation type = TypeOfLocation.GIGANTIC_PLANT_SOCIETY;

        public static void setRate(double rate) throws BankRateException{
            if (rate <= 0) throw new BankRateException(rate);
            bankRate = 1 + 0.01 * rate;
            System.out.println(" > " + companyDomain + " установил процентную ставку по вкладам = " + bankRate);
        }

        public static double getRate() {
            return bankRate;
        }
    }

    public class Place {

        private TypeOfLocation type;
        private double x;
        private double y;

        Place(TypeOfLocation type, int x, int y){
            this.type = type;
            this.x = x;
            this.y = y;
        }

        public TypeOfLocation getTypeOfPlace() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Place)) return false;
            Place place = (Place) o;
            return Double.compare(place.x, x) == 0 &&
                    Double.compare(place.y, y) == 0;
        }

        @Override
        public String toString() {
            return "Place{" +
                    "type=" + type +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}