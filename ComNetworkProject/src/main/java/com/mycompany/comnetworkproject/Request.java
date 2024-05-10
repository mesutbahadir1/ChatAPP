/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comnetworkproject;

/**
 *
 * @author mesut
 */
public class Request implements java.io.Serializable {

    public Object content;
    String op;
    String sender;
    String reciever;

    public Request(String t) {
        this.op = t;
    }

    public Request(String op, Object objContent, String sender, String reciever) {
        this.op = op;
        this.content = objContent;
        this.sender = sender;
        this.reciever = reciever;
    }

    public Request(String op, Object objContent, String sender) {
        this.op = op;
        this.content = objContent;
        this.sender = sender;
    }

}
