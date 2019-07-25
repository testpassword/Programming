package server.managers;

import com.jcraft.jsch.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.System.exit;

public class Tunnel {
    private String host;
    private String user;
    private String password;
    private int port;
    private String tunnelRemoteHost;
    private int tunnelLocalPort;
    private int tunnelRemotePort;

    /**
     * SSH Tunnel constructor
     *
     * @param host             Target SSH host, which you will be connected     (example: "helios.se.ifmo.ru")
     * @param user             User on target SSH host                          (example: s244444)
     * @param password         Password on target SSH host
     * @param port             Port on target SSH host                          (example: 2222)
     * @param tunnelRemoteHost The host from server, which you want to connect  (example: "pg")
     * @param tunnelLocalPort  The port on your machine                         (example: 5454)
     * @param tunnelRemotePort The port on SSH server                           (example: 5432)
     */
    public Tunnel(String host,
                  String user,
                  String password,
                  int port,
                  String tunnelRemoteHost,
                  int tunnelLocalPort,
                  int tunnelRemotePort) {

        this.user = user;
        this.password = password;
        this.port = port;
        this.host = host;
        try {
            InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            System.out.println("Некорректный ssh-хост.");
            e.printStackTrace();
        }

        this.tunnelRemotePort = tunnelRemotePort;
        this.tunnelRemoteHost = tunnelRemoteHost;
        this.tunnelLocalPort = tunnelLocalPort;
    }

    /**
     * Establish SSH connection and set port forwarding L
     * @return assigned port from connection
     */
    public int connect() {
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            localUserInfo lui = new localUserInfo();
            session.setUserInfo(lui);
            //CONNECT SSH
            System.out.println("Устанавливается ssh подключение к " + host + " : " + port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(3000);
            //DO PORT FORWARDING
            int assingedPort = session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
            System.out.println("Подключение установлено.");
            System.out.println("localhost:" + assingedPort + " -> " + tunnelRemoteHost + ":" + tunnelRemotePort);
            return assingedPort;
        } catch (JSchException e) {
            System.out.println("Ошибка подключения через ssh-туннель.");
            e.printStackTrace();
        }
        exit(-1);
        return -1;
    }

    class localUserInfo implements UserInfo {
        String passwd;
        public String getPassword() { return passwd; }
        public boolean promptYesNo(String str) { return true; }
        public String getPassphrase() { return null; }
        public boolean promptPassphrase(String message) { return true; }
        public boolean promptPassword(String message) { return true; }
        public void showMessage(String message) { }
    }
}
