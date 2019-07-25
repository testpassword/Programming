package tale;

import java.util.Objects;

/**
 * Объект класса {@code Town} является городом, который может содержать различные
 * здания - объекты класса {@link Place}.
 * @author Артемий Кульбако
 * @version 2.0
 * @since 20.12.18
 */
public class Town {

    private String name;

    public Town(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@code GiganticPlantSociety} инкапсулирует данные «Общества гигантских растений» («ОГР»).
     */
    public static class GiganticPlantSociety {

        private static double stockPriceForBuying;
        private static double stockPriceForSold;
        private static final String companyDomain = " ОАО \"ОГР\"";
        private static TypeOfLocation type = TypeOfLocation.GIGANTIC_PLANT_SOCIETY;
        protected static WalletBalance storage = new WalletBalance(0, 0);

        public static void setPrices(double _stockPriceForBying, double _stockPriceForSold) {
            stockPriceForBuying = _stockPriceForBying;
            stockPriceForSold = _stockPriceForSold;
            System.out.println("> " + companyDomain + " установило цены на акции:");
            System.out.println("    Цена для покупки = " + stockPriceForBuying + " | Цена для продажи = " + stockPriceForSold);
        }

        public static void printStocks(int amount) {
            if (amount < 0) throw new IllegalArgumentException("Нельзя напечатать отрицательное число акций");
            storage.setStocks(storage.getStocks() + amount);
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

    /**
     * {@code Bank} инкапсулирует банковские данные.
     */
    public static class Bank {
        private static double bankRate;
        private static final String companyDomain = "Городской банк";
        private static TypeOfLocation type = TypeOfLocation.GIGANTIC_PLANT_SOCIETY;

        public static void setRate(double rate) {
            if (rate <= 0) throw new IllegalArgumentException("Процентная ставка не может быть <= 0");
            System.out.println(" > " + companyDomain + " установил процентную ставку по вкладам = " + (rate * 100) + "%");
            bankRate = 1 + rate;
        }

        public static double getRate() {
            return bankRate;
        }
    }

    /**
     * Объект класса {@code Place} описывает одно из зданий города.
     */
    public class Place {

        private final TypeOfLocation type;
        private double x;
        private double y;

        public Place(TypeOfLocation type, int x, int y){
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

    /**
     * {@code TypeOfLocation} описывает тип, к которому может принадлежать местоположение {@link Place}.
     */
    public enum TypeOfLocation {
        GIGANTIC_PLANT_SOCIETY,
        BANK,
        STREET
    }
}