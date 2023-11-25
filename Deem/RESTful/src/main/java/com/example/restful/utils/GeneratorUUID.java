package com.example.restful.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeneratorUUID {

    public enum TypeImage {profile_icon, message_image, news_image }
    private static GeneratorUUID instance;

    private GeneratorUUID() {}

    public static GeneratorUUID getInstance() {
        if (instance == null)
            instance = new GeneratorUUID();

        return instance;
    }

    public String generateUUIDForIcon(String author) {
        return generateUUID(TypeImage.profile_icon, "", author);
    }
    public String generateUUIDForMessage(String date, String author) {
        return generateUUID(TypeImage.message_image, date, author);
    }
    public String generateUUIDForNews(String date, String author) {
        return generateUUID(TypeImage.news_image, date, author);
    }

    public String generateUUID(TypeImage type, String date, String author) {
        //String password = APIManager.getManager().myAccount.getPassword(); //ключ для шифровки uuid только для личного доступа

        String unId = "type=" + type.toString() + "&author=" + author/* + "&password=" + password*/;

        if (type == TypeImage.message_image || type == TypeImage.news_image)
            unId += "&date=" + date;

        String uuid = SHAHashing.hashString(unId);

        return uuid;
    }

    static class SHAHashing {
        public static String hashString(String input) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

                StringBuilder hexString = new StringBuilder();
                for (byte b : encodedHash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}




