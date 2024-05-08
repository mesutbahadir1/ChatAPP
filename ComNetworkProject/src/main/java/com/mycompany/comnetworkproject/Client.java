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
public class Client {

    Socket socket;
    String ip;
    String chat;
    Connect cn;
    String name;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    int port;

    public Client(ChatPage chatScreen) {
    }

    public void ConnectToServer(String user) throws IOException {
        this.port = 5001;
        this.ip = "localhost";
        this.chat = "start";
        this.name = user;
        try {
            socket = new Socket(ip, port);
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            cn = new Connect(this);
            cn.start();
        } catch (Exception e) {
            System.out.println("e :" + e);
        }

        Request reqName = new Request("Connect");
        reqName.o = user;
        SendMessage(reqName);
    }

    public void SendMessage(Request req) {
        try {
            sOutput.writeObject(req);
            sOutput.flush();
        } catch (Exception err) {
            System.out.println("Exception writing to server: " + err);
        }

    }

    public void disconnect() {
        try {
            //tüm nesneleri kapatıyoruz
            if (sInput != null ||sOutput != null) {
                sInput.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

    }

    class Connect extends Thread {

        Client client;

        Connect(Client c) {
            client = c;
        }

        @Override
        public void run() {
            while (client.socket.isConnected()) {
                try {
                    Request req = (Request) (client.sInput.readObject());
                    if (req.op.equals("lstClientUpdate")) {
                        ArrayList clients = (ArrayList) req.o;
                        ChatPage.userList.removeAllElements();
                        for (int i = 0; i < clients.size(); i++) {
                            ChatPage.userList.addElement(clients.get(i).toString());
                        }
                    } 
                    else if (req.op.equals("lstRoomUpdate")) {
                        ArrayList<String> chatList = (ArrayList) req.o;
                        ChatPage.chats = chatList;
                        ChatPage.chatList.removeAllElements();
                        for (int i = 0; i < chatList.size(); i++) {
                            ChatPage.chatList.addElement(chatList.get(i));
                        }
                    } 
                    else if (req.op.equals("addUserChat")) {
                        ChatPage.userInfoList.addElement(req.sender + " : " + req.o.toString());
                    } 
                    else if (req.op.equals("addChat")) {
                        ChatPage.chatInfoList.addElement(req.sender + " : " + req.o.toString());
                    }
                } 
                catch (Exception ex) {
                    System.out.println("ex: "+ ex);
                }
            }

        }

    }
}
