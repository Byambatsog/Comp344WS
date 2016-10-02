package com.comp344.ecommerce.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Byambatsog on 10/2/16.
 */
public abstract class PasswordGenerator {

    public enum RANDOM_PASSWORD_OPTION {LOWERCASE, UPPERCASE,NUMBERS,ALPHANUMERIC,ASCII};

    private final static char[] lowerCase = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private final static char[] upperCase = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private final static char[] numeric = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    private final static char[] printableAscii = new char[]{'!', '\"', '#', '$', '%', '(', ')', '*', '+', '-', '.', '/', '\'',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ':', '<', '=', '>', '?', '@',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private final static char[] alphaNumberic = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};



    public static String encryptPassword(String password, String sand){
        String md5=null;
        try{
            try{
                if(!StringUtils.hasText(sand)) sand="";
            }catch (Exception e){
                sand="";
            }
            String p=sand+password;
            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(p.getBytes(), 0, p.length());
            md5 = new BigInteger(1, mdEnc.digest()).toString(16);
            md5="00000000000000000000000000000000".substring(md5.length())+md5;

        }catch (NoSuchAlgorithmException nsae){
            //do nothing
        }
        return md5;
    }

    public static String getRandomPassword(RANDOM_PASSWORD_OPTION option, int length){
        Assert.notNull(option, "random password generation option not be null");
        SecureRandom wheel=null;
        try{
            wheel = SecureRandom.getInstance("SHA1PRNG");
        }catch (NoSuchAlgorithmException nsae){

        }

        StringBuffer buffer=new StringBuffer(length);
        switch (option){
            case LOWERCASE:{
                for (int i=0;i<length;i++){
                    int random=wheel.nextInt(lowerCase.length);
                    buffer.append(lowerCase[random]);
                }
                break;
            }
            case UPPERCASE:{
                for (int i=0;i<length;i++){
                    int random=wheel.nextInt(upperCase.length);
                    buffer.append(upperCase[random]);
                }
                break;
            }
            case NUMBERS:{
                for (int i=0;i<length;i++){
                    int random=wheel.nextInt(numeric.length);
                    buffer.append(numeric[random]);
                }
                break;
            }
            case ALPHANUMERIC:{
                for (int i=0;i<length;i++){
                    int random=wheel.nextInt(alphaNumberic.length);
                    buffer.append(alphaNumberic[random]);
                }
                break;
            }
            case ASCII:{
                for (int i=0;i<length;i++){
                    int random=wheel.nextInt(printableAscii.length);
                    buffer.append(printableAscii[random]);
                }
                break;
            }
        }
        return buffer.toString();
    }
}
