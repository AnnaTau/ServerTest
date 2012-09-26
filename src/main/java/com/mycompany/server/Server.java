/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author 1
 */
public class Server {

    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, Connect> connectionMap;

    public Server() {
        connectionMap = new ConcurrentHashMap<String, Connect>();
        try {
            serverSocket = new ServerSocket(2004, 10);
            System.out.println("Server socket created for localhost:2004. Max connections is 10.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void start() {
        if (serverSocket == null) {
            System.out.println("Server can't start because server socket is null");
            return;
        }
        try {
            while (true) {
                System.out.println("Waiting for connection");
                Socket connection = serverSocket.accept();
                Connect connect = new Connect(connection);
                ClientListener client = new ClientListener(connect, genId(), connectionMap);
                Thread clientThread = new Thread(client);
                clientThread.start();

                Thread.sleep(50);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String genId() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }
}
