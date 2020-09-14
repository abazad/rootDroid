package com.roottools.rootchecker

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.roottools.rootchecker.ConstantsUtilities.Constants
import com.roottools.rootchecker.ConstantsUtilities.RootUtilities
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.net.Uri
import android.view.View



class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var rooted: Boolean = false
    private var rootGivenBool: Boolean = false
    private var busyBoxInstalledBool: Boolean = false
    private var rootversionBool: Boolean = false
    fun currentVersion(): String? {
        val release = Build.VERSION.RELEASE.replace("(\\d+[.]\\d+)(.*)".toRegex(), "$1").toDouble()
        var codeName = "Unsupported" //below Jelly bean OR above Oreo
        if (release >= 4.1 && release < 4.4) codeName = "Jelly Bean" else if (release < 5) codeName = "Kit Kat" else if (release < 6) codeName = "Lollipop" else if (release < 7) codeName = "Marshmallow" else if (release < 8) codeName = "Nougat" else if (release < 9) codeName = "Oreo" else if (release < 10) codeName = "Pie" else if (release < 11) codeName = "Android Q" else if (release < 12) codeName = "Android Future 12"
        return codeName + " v" + release + ", API Level: " + Build.VERSION.SDK_INT
        Root_Given_Text.text = currentVersion()

    }
    fun linkopener(view: View) {
        val url = "https://www.androidroottool.com/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    /** Returns the consumer friendly device name  */
    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true
        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }
        return phrase.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Run the async task
        getRootData()
    }


    fun getRootData() {
        //Coroutine
        launch(Dispatchers.Default) {
            try {
                rooted = RootUtilities.isRootAvailable()
                rootGivenBool = RootUtilities.isRootGiven()
                busyBoxInstalledBool = RootUtilities.isBusyBoxInstalled()

                //UI Thread
                withContext(Dispatchers.Main) {
                    if (rooted) {
                        Root_Text_Desc.text = Constants.DEVICE_ROOTED
                        Root_Text_Desc.setTextColor(Color.parseColor("#00E676"))

                        Device_Rooted.text = getDeviceName()
                        Root_Path_text.text = RootUtilities.findBinaryLocation()

                        if (rootGivenBool)
                            Root_Given_Text.text = currentVersion()
                        else
                            Root_Given_Text.text = currentVersion()


                        if (rooted) {
                            Root_Given_Text.text =currentVersion()
                        } else {
                            Root_Given_Text.text = currentVersion()
                        }

                        if (busyBoxInstalledBool)
                            busy_Box_Installed.text = Constants.YES
                        else
                            busy_Box_Installed.text = Constants.NO

                    } else {
                        Root_Path_text.text = Constants.SYMBOL_HYPHEN
                        Device_Rooted.text = getDeviceName()
                        busy_Box_Installed.text = Constants.NO
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}
