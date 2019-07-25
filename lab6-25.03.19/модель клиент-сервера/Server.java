import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ServerSocket ss = new ServerSocket(7777)) {
            System.out.println("ServerSocket awaiting connections...");
            try (Socket socket = ss.accept()) {
                System.out.println("Connection from " + socket + "!");
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
                    objectOutputStream.writeObject("Connection establish.");
                    while (true) {
                        String message = (String) objectInputStream.readObject();
                        System.out.println("Received [" + message + "] from: " + socket);
                        message = "Answer ::: " + message + "\ntest string";
                        objectOutputStream.writeObject(message);
                    }
                }
            }
        }
    }
}
