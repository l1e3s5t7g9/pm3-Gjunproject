package com.pm3.Tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by student on 2017/10/19.
 */

public final class FileRW {

    public static void fileW(File file, String data) {

        try {

            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                fw.write(data);
            } finally {
                fw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String fileR(File file) {
        // 判斷是否有檔案
        if (!file.exists()) {
            return null;
        }

        FileReader fr = null;
        char[] rxbuf = new char[100];  //需要預留一個空間讀取
        try {

            try {
                fr = new FileReader(file);
                fr.read(rxbuf);
            } finally {
                fr.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(rxbuf);

    }

    public static void fileBufW(File file, String data) {

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String fileBufR(File file) {

        StringBuilder rxbuf = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null) {
                rxbuf.append(s);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rxbuf.toString();

    }

}
