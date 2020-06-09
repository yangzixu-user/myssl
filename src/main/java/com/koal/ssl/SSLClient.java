package com.koal.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author admin
 */
public class SSLClient {

    private static String CLIENT_KEY_STORE="E:\\work\\myssl\\client_ks";

    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.ssl.trustStore",CLIENT_KEY_STORE);
        System.setProperty("javax.net.debug","ssl,handshake");

        SSLClient client = new SSLClient();

        Socket s = client.clientWithoutCert();


        PrintWriter writer = new PrintWriter(s.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer.println("hello");
        writer.flush();
        System.out.println(reader.readLine());
        s.close();
    }
    private Socket clientWithoutCert() throws IOException {
        SocketFactory sf = SSLSocketFactory.getDefault();
        Socket s = sf.createSocket("localhost",8443);
        return s;
    }
}

