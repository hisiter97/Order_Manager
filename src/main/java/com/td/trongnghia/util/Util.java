/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author TRONGNGHIA
 */
public class Util {
    public static String getMd5Hash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return new String(messageDigest.digest(input.getBytes(StandardCharsets.UTF_8)));
    }
}
