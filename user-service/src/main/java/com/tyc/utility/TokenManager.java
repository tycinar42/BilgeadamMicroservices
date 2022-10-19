package com.tyc.utility;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    public String generateToken(Long id) {
        Long time = System.currentTimeMillis();
        return "Token:" + time + ":" + id;
    }

    public Long getId(String token) {
        String[] datas = token.split(":");
        if(datas.length != 3) return null;
        if(!datas[0].equals("Token")) return null;
        /**
         * Token icinde zaman parametresi de gelir. Buna gore 30 sn
         * icinde token gecersiz kilinsin
         */
        Long time = Long.parseLong(datas[1]);
        Long now = System.currentTimeMillis();
        Boolean verifyTime = (now - time -(30*1000)) < 0;
        if(!verifyTime) return null;
        return Long.parseLong(datas[2]);
    }
}
