import java.util.Objects;

public class GiganticPlantSociety implements StatusActions {
    private static double stockPriceForBuying;
    private static double stockPriceForSold;
    private static int id = 0;
    private int uID;
    protected PocketBalance storage;

    /*    GiganticPlantSociety(){
            System.out.println("> Общество Гигантский Растений сформировано");
        }
    */

    protected static void setPrices(double _stockPriceForBying, double _stockPriceForSold){
        stockPriceForBuying = _stockPriceForBying;
        stockPriceForSold = _stockPriceForSold;
        System.out.println("> Общество Гигантский Растений установило цены на акции:");
        System.out.println("    Цена для покупки = " + stockPriceForBuying + " | Цена для продажи = " + stockPriceForSold);
    }

    protected static double getStockPriceForBuying(){
        return stockPriceForBuying;
    }

    protected static double getStockPriceForSold(){
        return stockPriceForSold;
    }

    GiganticPlantSociety(PocketBalance storage){
        id++;
        uID = id;
        this.storage = storage;
        System.out.println("> Офис ОГР №" + uID + " открыт");
    }

    @Override
    public String toString() {
        return "GiganticPlantSociety{" +
                "uID=" + uID +
                ", storage=" + storage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiganticPlantSociety)) return false;
        GiganticPlantSociety that = (GiganticPlantSociety) o;
        return uID == that.uID &&
                Objects.equals(storage, that.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uID, storage);
    }

    @Override
    public void displayProhibition(){
        System.out.println("> В офисе ОГР можно приобретать и продавать акции");
    }

    @Override
    public void showBalance(){
        System.out.println("> Баланс отделения №" + uID + " = " + storage.getMoney());
    }
}