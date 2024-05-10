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

/**
 *
 * @author mesut
 */
public class SClient extends Thread {

    public Server server;
    public Socket socket;
    public int id;
    public ObjectOutputStream sOutput;
    public ObjectInputStream sInput;
    public boolean isListening = false;
    String chat;
    String name;

    public SClient(Server server, Socket clientSocket, int id) {
        try {
            this.server = server;
            this.socket = clientSocket;
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
            this.id = id;
            this.chat = "start";

        } catch (Exception ex) {
            System.out.println("ex:" + ex);
        }

    }

    public void Listen() {
        isListening = true;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (this.isListening) {
                Request req = (Request) this.sInput.readObject();

                if (req.op.equals("Connect")) {
                    ServerPage.lst_clientsModel.addElement(req.content.toString());
                    this.name = req.content.toString();
                    Request updateClients = new Request("lstClientUpdate");
                    ArrayList<String> users = new ArrayList<>();
                    for (int i = 0; i < server.clientList.size(); i++) {
                        users.add(server.clientList.get(i).name);
                    }
                    updateClients.content = users;
                    server.reqToAll(updateClients);
                } else if (req.op.equals("generateChat")) {
                    String chatName = req.content.toString();
                    server.chats.add(chatName);
                    Request chatListUpdate = new Request("lstRoomUpdate");
                    chatListUpdate.content = server.chats;
                    server.reqToAll(chatListUpdate);
                } else if (req.op.equals("addUserChat")) {
                    String search = req.reciever;
                    for (int i = 0; i < server.clientList.size(); i++) {
                        if (server.clientList.get(i).name.equals(search)) {
                            server.reqToClient(server.clientList.get(i), req);
                        }
                    }
                } else if (req.op.equals("addChat")) {
                    String r = chat;
                    for (int i = 0; i < server.clientList.size(); i++) {
                        if (r.equals(server.clientList.get(i).chat)) {
                            server.reqToChat(server.clientList.get(i), req);
                        }
                    }
                } else if (req.op.equals("refreshChat")) {
                    Request chatListUpdate = new Request("lstRoomUpdate");
                    chatListUpdate.content = server.chats;
                    server.reqToAll(chatListUpdate);
                } else if (req.op.equals("connectChat")) {
                    this.chat = req.content.toString();
                }
            }

        } catch (Exception ex) {
            System.out.println("ex: " + ex);
        } finally {
            this.server.DisconnectClient(this);
        }

    }

    //clientı kapatan fonksiyon
    public void Disconnect() {
        try {
            this.isListening = false;
            this.socket.close();
            this.sInput.close();
            this.sOutput.close();
            this.server.DisconnectClient(this);

        } catch (Exception ex) {
            System.out.println("ex:" + ex);
        }
    }

}
