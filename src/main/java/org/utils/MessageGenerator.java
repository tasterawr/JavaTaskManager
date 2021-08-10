package org.utils;

public class MessageGenerator {
    private static String message = "";

    public static String getMessage(){
        if (!message.equals("")){
            String mes = message;
            message = "";
            return mes;
        }
        else return message;
    }

    public static void setMessage(String mes){
        message = mes;
    }
}
