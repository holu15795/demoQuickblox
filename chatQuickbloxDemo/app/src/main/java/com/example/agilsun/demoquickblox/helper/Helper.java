package com.example.agilsun.demoquickblox.helper;

import java.util.Random;

/**
 * *******************************************
 * * Created by HoLu on 28/02/2018.         **
 * * All rights reserved                    **
 * *******************************************
 */

public class Helper {
    static public String getToken(int chars) {
        String CharSet = "1234567890";
        String Token = "";
        for (int a = 1; a <= chars; a++) {
            Token += CharSet.charAt(new Random().nextInt(CharSet.length()));
        }
        return Token;
    }
}
