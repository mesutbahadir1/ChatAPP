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
public class Server extends Thread {

    public ServerSocket serverSocket;
    public ArrayList<SClient> clientList;
    public Thread listen;
    public boolean isListening = false;
    public ObjectOutputStream clientOutput;
    public ObjectInputStream clientInput;
    public int clientId = 0;
    public ArrayList<String> chats;
    public int port;

    public Server(int serverPort) throws IOException {
        this.port = serverPort;
        this.serverSocket = new ServerSocket(this.port);
        this.clientList = new ArrayList<>();
        this.chats = new ArrayList<>();

    }

    public void listenServer() {
        isListening = true;
        this.start();
    }

    public void DisconnectClient(SClient client) {
        this.clientList.remove(client);
        ServerPage.lst_clientsModel.removeAllElements();
        for (SClient sClient : clientList) {
            String cinfo = sClient.socket.getInetAddress().toString() + ":" + sClient.socket.getPort();
            ServerPage.lst_clientsModel.addElement(cinfo);
        }
    }

    public void reqToAll(Request req) {
        clientList.forEach(client -> {
            try {
                client.sOutput.writeObject(req);
            } catch (Exception ex) {
                System.out.println("ex:" + ex);

            }
        });
    }

    public void reqToChat(SClient client, Request req) {

        try {
            client.sOutput.writeObject(req);
        } catch (Exception ex) {
            System.out.println("ex:" + ex);
        }
    }

    public void Stop() {
        try {
            this.isListening = false;
            this.serverSocket.close();
            System.out.println("Server Stopped");
        } catch (Exception ex) {
            System.out.println("ex:" + ex);
        }
    }

    public void reqToClient(SClient client, Request req) {

        try {
            client.sOutput.writeObject(req);
        } catch (Exception ex) {
            System.out.println("ex:" + ex);
        }

    }

    public void reqToClientWithIndex(int index, String req) {
        try {
            this.clientList.get(index).sOutput.writeObject(req);
        } catch (Exception ex) {
            System.out.println("ex:" + ex);
        }

    }

    @Override
    public void run() {

        try {
            while (this.isListening) {
                Socket clientSocket = serverSocket.accept();
                this.clientId++;
                SClient nsclient = new SClient(this, clientSocket, this.clientId);
                this.clientList.add(nsclient);
                nsclient.Listen();
            }

        } catch (Exception ex) {
            System.out.println("ex:" + ex);
        }
    }

}
