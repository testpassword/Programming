//var15432
public class Main {
    public static void main(String[] args){
        GiganticPlantSociety.setPrices(100, 80);
        GiganticPlantSociety office1 = new GiganticPlantSociety(new PocketBalance(0, 500));
        Shorty unnamed1 = new Shorty(TypeOfLocation.GIGANTIC_PLANT_SOCIETY, new PocketBalance(Math.random() * 7000, 0), new BankBalance(Math.random() * 300));
        unnamed1.showBalance();
        Shorty unnamed2 = new Shorty(TypeOfLocation.GIGANTIC_PLANT_SOCIETY, new PocketBalance(Math.random() * 7000, 0), new BankBalance(0));
        unnamed2.showBalance();
        Shorty unnamed3 = new Shorty(TypeOfLocation.GIGANTIC_PLANT_SOCIETY, new PocketBalance(Math.random() * 7000, 0), new BankBalance(Math.random() * 532));
        unnamed3.showBalance();
        unnamed1.buyStocks((int)(Math.random() * 20), office1);
        unnamed2.buyStocks(5, office1);
        unnamed3.buyStocks(43, office1);
        office1.showBalance();
        Shorty Miga = new Shorty(TypeOfLocation.BANK, new PocketBalance(office1.storage.getMoney(), 0), new BankBalance(0), "Miga");
        office1.storage.setMoney(0);
        Miga.putMoneyToBank();
        unnamed3.soldStocks(2, office1);
    }
}