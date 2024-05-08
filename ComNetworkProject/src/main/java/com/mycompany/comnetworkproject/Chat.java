/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comnetworkproject;

/**
 *
 * @author mesut
 */
public class Chat {

    String name;
    Client c;
    int id;
    int count;

    public Chat(int chatId, String chatName, int userCount) {
        this.id = chatId;
        this.name = chatName;
        this.count = userCount;
    }

    public Chat(String name, Client chatCreator) {
        this.name = name;
        this.c = chatCreator;
    }

}
