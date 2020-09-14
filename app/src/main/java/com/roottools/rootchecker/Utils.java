package com.roottools.rootchecker;


public class Utils {

    public static String getDeviceName(){
        String deviceName = android.os.Build.MODEL; // returns model name
        return deviceName;
    }

}

