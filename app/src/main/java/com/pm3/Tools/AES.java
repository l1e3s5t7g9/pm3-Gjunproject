package com.pm3.Tools;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by student on 2017/10/19.
 */

public final class AES {

    //AES encrypt
    public final static String encrypt(String key, String dat) {

        try {
            return AESCrypt.encrypt(key, dat);
        } catch (GeneralSecurityException e) {
            //handle error
            return null;
        }

    }

    //AES dncrypt
    public final static String decrypt(String key, String dat) {

        try {
            return AESCrypt.decrypt(key, dat);
        } catch (GeneralSecurityException e) {
            //handle error - could be due to incorrect password or tampered encryptedMsg
            return null;
        }

    }

}
