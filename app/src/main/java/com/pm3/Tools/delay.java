package com.pm3.Tools;

/**
 * Created by Felix on 2017/10/13.
 */

public final class delay {

    public final static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
