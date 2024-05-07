/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comnetworkproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author mesut
 */
public class Client {
    Socket client_socket;
    String server;
    int roomId;
  
    ChatPage chatScreen;
    String name;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    int port;
    String room;

    public Client(ChatPage chatScreen) {
        this.chatScreen = chatScreen;
    }

    public void Start(String user) throws IOException {
        this.port=5001;
        this.server = "localhost";
        client_socket = new Socket(server, port);
        sInput = new ObjectInputStream(client_socket.getInputStream());
        sOutput = new ObjectOutputStream(client_socket.getOutputStream());
        this.room="room page";   
        name = user;
       
    }

    public void SendMessage(Message msg) {
        try {
            sOutput.writeObject(msg);
            sOutput.flush();
        } catch (IOException ex) {
            System.out.println("Exception writing to server: " + ex);
        }

    }
    public void disconnect() {
        try {
            //tüm nesneleri kapatıyoruz
            if (sInput != null) {
                sInput.close();
            }
            if (sOutput != null) {
                sOutput.close();
            }

            if (client_socket != null) {
                client_socket.close();
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

    }

   
    
}
