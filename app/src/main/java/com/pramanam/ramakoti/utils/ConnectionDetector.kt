package com.pranavaeet.sriramakoti.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * This class is used to detect if the device is connected to internet or not.
 * Only one instance of the class is available throughout the application. The instance is created using
 * singleton pattern.
 */
class ConnectionDetector // private constructor to avoid creation of new objects.
private constructor(// The application context of the application.
    private val context: Context// loop through the NetworkInfo of each network and check if its state is CONNECTED.// get the information of all the networks the device is connected to.// get access to the connectivity manager.
) {
    // check for successful access to connectivity manager.

    /**
     * This method is used to check if the device is connected to internet or not.
     * To use this method we need android.permission.ACCESS_NETWORK_STATE in the manifest
     * @return true if connected to internet,false if not connected to internet.
     */
    val isConnectedToInternet: Boolean
        get() { // get access to the connectivity manager.
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // check for successful access to connectivity manager.
            if (connectivity != null) { // get the information of all the networks the device is connected to.
                val info = connectivity.allNetworkInfo
                if (info != null) // loop through the NetworkInfo of each network and check if its state is CONNECTED.
                    for (anInfo in info) if (anInfo.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
            }
            return false
        }

    companion object {
        // The instance of the ConnectionDetector.
        private var sInstance: ConnectionDetector? = null
        // Object used to lock the synchronized block of the getInstance(Context context) method.
        private val lock = Any()

        /**
         * This method is used to create a new instance of the ConnectionDetector or
         * get the existing instance of the ConnectionDetector.
         * @param context the activity from which this method is called.
         * @return the instance of the ConnectionDetector, sInstance.
         */
        fun getInstance(context: Context): ConnectionDetector? { // using lazy initialization, check sInstance for null. If null create a new object and return,
// else return the already available object.
            if (sInstance == null) {
                synchronized(lock) {
                    if (sInstance == null) { // while creating the connection detector create it with the applicationContext
                        sInstance =
                            ConnectionDetector(context.applicationContext)
                    }
                }
            }
            return sInstance
        }
    }

}