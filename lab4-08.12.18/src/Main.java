//var15432.1669

public class Main {
    public static void main(String[] args){
        Town.GiganticPlantSociety.setPrices(3, 2);
        try {
            Town.GiganticPlantSociety.printStocks(430);
        }
        catch (PrintStocksException ex){
            ex.printStackTrace();
            Town.GiganticPlantSociety.printStocks(ex.getAbsQuantity());
        }
        Town planet = new Town();
        Town.Place office = planet.new Place(TypeOfLocation.GIGANTIC_PLANT_SOCIETY, 1, 0);
        Town.Place cityBank = planet.new Place(TypeOfLocation.BANK, 5, 9);
        Town.Place darkStreet = planet.new Place(TypeOfLocation.STREET, 1, 1);
        Shorty Miga = new Shorty(office, new WalletBalance(500, 0), new BankBalance(0), "Miga");
        Shorty Lupa = new Shorty(office, new WalletBalance(300, 0), new BankBalance(0), "Pupa");
        Shorty Pupa = new Shorty(office, new WalletBalance(250, 0), new BankBalance(0), "Lupa");
        Shorty Kojima = new Shorty(office, new WalletBalance(12000, 0), new BankBalance(0), "Хидео");
        Shorty Kulbako = new Shorty(darkStreet, new WalletBalance(23, 0), new BankBalance(0), "Пугалол");
        try {
            Town.Bank.setRate(8);
        }
        catch (BankRateException ex1){
            ex1.printStackTrace();
        }
        Lupa.buyStocks(5);
        Pupa.buyStocks(2);
        Kojima.buyStocks(270);
        Miga.shakeHand(Kojima);
        Pupa.toldStory(Miga);
        Kojima.move(darkStreet);
        Kulbako.robShorty(Kojima);
        Miga.showBalance();
        Miga.move(cityBank);
        Miga.putMoneyToBank(Miga.getMoney());
        Miga.showBalance();
        }
    }