/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comnetworkproject;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author mesut
 */
public class RandomStringGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        return new BigInteger(length * 5, random).toString(32);
    }
}
