/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

import com.mycompany.messagelib.Message;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1
 */
public class ClientListener implements Runnable {

    private final Connect connection;
    Socket socket;
    String uid;
    Map<String, Connect> map;
    
    public ClientListener(Connect connection, String id, ConcurrentHashMap<String, Connect> map) {
        this.connection = connection;
        this.uid = id;
        this.map = map;
        map.put(id, connection);
        this.socket = connection.getSocket();
    }

    public void run() {
        System.out.println("Connection received from " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = connection.getOut();
            sendMessage(out, new Message(uid, "Connection sucsessful"));
            in = connection.getIn();
            Message msg = null;
            ObjectOutputStream tempOut;
            do {
                try {
                    msg = (Message) in.readObject();
                    if (msg.getMsg().equals("bye")) {
                        sendMessage(out, new Message(uid, "bye"));
                        break;
                    }
                    System.out.println("received from client>" + msg.getMsg());
                    for(Connect c: map.values()){
                        tempOut = c.getOut();
                        sendMessage(tempOut, msg);
                    }
                    
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    break;
                }
                
            } while (!"bye".equals(msg.getMsg())||socket.isConnected());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            map.remove(uid);
            closeStreamQuietly(in);
            closeStreamQuietly(out);
            closeSocketQuietly(socket);
        }
    }
    
    private void sendMessage(ObjectOutputStream out, Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("send from server>" + msg.getMsg());
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeStreamQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ex) {
            System.out.println("Can't close stream: " + ex.getMessage());
        }
    }

    private void closeSocketQuietly(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception ex) {
            System.out.println("Can't close socket: " + ex.getMessage());
        }
    }
}
