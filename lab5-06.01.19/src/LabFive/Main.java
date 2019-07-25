package LabFive;

import java.io.*;

/**
 * @author Kulbako Artemy 265570
 * @version 3.0
 */

public class Main {

    public static void main(String[] args) throws IOException {
        Commander commander = new Commander(new CollectionManager(System.getenv("Collman_Path")));
        commander.interactiveMod();
    }
}