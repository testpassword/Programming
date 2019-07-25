import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Starting client module.\nConnecting to server ...");
        try (Socket outcoming = new Socket(InetAddress.getLocalHost(), 7777)) {
            outcoming.setSoTimeout(10000);
            try (ObjectOutputStream toServer = new ObjectOutputStream(outcoming.getOutputStream());
                 ObjectInputStream fromServer = new ObjectInputStream(outcoming.getInputStream());
                 Scanner fromKeyboard = new Scanner(System.in)) {
                System.out.println((String) fromServer.readObject());
                String command;
                while (!(command = fromKeyboard.nextLine()).equals("exit")) {
                    toServer.writeObject(command);
                    System.out.println((String) fromServer.readObject());
                }
                System.out.println("Closing socket and terminating program.");
            } catch (ClassNotFoundException e) {
                e.getMessage();
            }
        }  catch (IOException e) {
            System.out.println("Could not connect. Something went wrong.");
        }
    }
}