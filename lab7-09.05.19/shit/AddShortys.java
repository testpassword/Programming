Gson gson = new Gson();
        Shorty[] citizen = new Shorty[5];
        citizen[0] = new Shorty("Neznaika", new WalletBalance(43, 228), new Coordinates(6, 6), new Date(), 2);
        citizen[1] = new Shorty("Zvezdochka", new WalletBalance(21, 210), new Coordinates(2, 1), new Date(), 1);
        citizen[2] = new Shorty("Knopochka", new WalletBalance(0, 700), new Coordinates(3, 4), new Date(), 2);
        citizen[3] = new Shorty("gangstar Muhamor", new WalletBalance(0, 3200), new Coordinates(2, 1), new Date(), 3);
        citizen[4] = new Shorty("Miga", new WalletBalance(57, 1488), new Coordinates(6, 6), new Date(), 1);
        for (Shorty s: citizen) System.out.println(gson.toJson(s, Shorty.class));