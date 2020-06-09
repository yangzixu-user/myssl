package com.koal.ssl;


import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;


/**
 * @author admin
 */
public class SSLService extends Thread {
    private Socket socket;

    public SSLService(Socket socket){
        this.socket= socket;
    }

    @Override
    public void run(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            String data = reader.readLine();
            writer.println(data);
            writer.close();
            reader.close();
        } catch (IOException e) {

        }
    }

    private static String SERVER_KEY_STORE = "E:\\work\\myssl\\servers_ks";
    private static String SERVER_KEY_STORE_PASSWORD="123123";




    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        System.setProperty("javax.net.ssl.trustStore",SERVER_KEY_STORE);
        SSLContext context = SSLContext.getInstance("TLS");
        KeyStore ks = KeyStore.getInstance("jceks");
        ks.load(new FileInputStream(SERVER_KEY_STORE),null);
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks,SERVER_KEY_STORE_PASSWORD.toCharArray());
        context.init(kf.getKeyManagers(),null,null);
        ServerSocketFactory factory = context.getServerSocketFactory();
        ServerSocket _socket= factory.createServerSocket(8443);
        ((SSLServerSocket) _socket).setNeedClientAuth(false);

        while (true){
            new SSLService(_socket.accept()).start();
        }
    }
}


