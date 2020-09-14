package com.roottools.rootchecker

import android.os.Build
class K_utils {
    companion object {
        @JvmStatic
        fun getPhoneDeviceName():String {
            val model = Build.MODEL // returns model name
            return model;
        }

    }
}
