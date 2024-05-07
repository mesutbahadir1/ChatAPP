/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comnetworkproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mesut
 */
public class Server extends Thread{

    public ServerSocket serverSocket;
    public ArrayList<SClient> clientList;
    public Thread listen;
    public boolean isListening = false;
    public ObjectOutputStream clientOutput;
    public ObjectInputStream clientInput;
   
    public int clientId=0;
    public ArrayList<String> rooms;

    public Server(int serverPort) throws IOException {
        this.serverSocket = new ServerSocket(5001);
        this.clientList = new ArrayList<>();
        this.rooms = new ArrayList<>();

    }

    public void listenServer() {
        isListening=true;
        this.start();
    }

    public void DisconnectClient(SClient client) {
        this.clientList.remove(client);
         Frm_Server.lst_clientsModel.removeAllElements();
        for (SClient sClient : clientList) {
            String cinfo = sClient.clientSocket.getInetAddress().toString() + ":" + sClient.clientSocket.getPort();
            Frm_Server.lst_clientsModel.addElement(cinfo);
        }
    }

    
    public void Stop() {
        try {
            this.isListening = false;
            this.serverSocket.close();
            System.out.println("Server Stopped");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        try {
            while (this.isListening) {
                System.out.println("Server waiting client...");
                Socket clientSocket = serverSocket.accept(); //blocking
                this.clientId++;
                SClient newClient = new SClient(this, clientSocket, this.clientId);
                this.clientList.add(newClient);
                newClient.ListenClient();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
