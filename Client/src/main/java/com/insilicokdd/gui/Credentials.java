package com.insilicokdd.gui;

public class Credentials {
    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString() {
        return "{\r\n" +
                "  \"username\":" + "\"" + username + "\",\r\n" +
                "  \"password\":" + "\"" + password + "\"" + "\r\n" +
                "}";
    }


}