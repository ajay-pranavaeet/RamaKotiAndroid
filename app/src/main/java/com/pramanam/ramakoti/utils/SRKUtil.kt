package com.pranavaeet.sriramakoti.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

object SRKUtil {
    var PROPERTIES_FILE = "ezloct.properties"
    var SERVER_PROPERTY = "serverUrl"
    var IMAGE = "imageUrl"
    var status: Boolean? = null
    var productList: ArrayList<HashMap<String, Any>>? = null
    /**
     * read server url from property file
     * @param context
     * @return
     */
    fun readServerUrl(context: Context): String {
        val properties = Properties()
        var serverURL = ""
        try {
            properties.load(context.assets.open(PROPERTIES_FILE))
            serverURL = properties.getProperty(SERVER_PROPERTY)
            properties.setProperty(SERVER_PROPERTY,"")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        println("server url is ======$serverURL")
        return serverURL
    }

    fun getURLConnection(url: URL): HttpsURLConnection {
        return url.openConnection() as HttpsURLConnection
    }

    fun readLogoImageUrl(context: Context): String {
        val properties = Properties()
        var imageURL = ""
        try {
            properties.load(context.assets.open(PROPERTIES_FILE))
            imageURL = properties.getProperty(IMAGE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //String url = "https://mobieshop.pranavaeet.com/";
        println("imageURL is ======$imageURL")
        return imageURL
    }

    fun getBitmapFromURL(src:String): Bitmap? {
        try
        {
            Log.e("src", src)
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned")
            return myBitmap
        }
        catch (e:IOException) {
            e.printStackTrace()
            Log.e("Exception", e.message)
            val img: Bitmap?= null
            return img
        }
    }

    //validating emailid
    @TargetApi(Build.VERSION_CODES.FROYO)
    fun isValidEmail(email: String?): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //validating mobile number
    @TargetApi(Build.VERSION_CODES.FROYO)
    fun isValidPhoneNumber(phoneNumber: CharSequence): Boolean {
        val str = phoneNumber.toString().substring(0, 1)
        return if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length == 10 && str != "0") {
            Patterns.PHONE.matcher(phoneNumber).matches()
        } else false
    }

    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }// Find todays date

    /**
     * get current timestamp
     *
     * @return yyyy-MM-dd HH:mm:ss formate date as string
     */
    val currentTimeStamp: String?
        get() = try {
            val dateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentTimeStamp =
                dateFormat.format(Date()) // Find todays date
            println("##### current time ==$currentTimeStamp")
            currentTimeStamp
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    //store data as key value pair in shared preferences
    fun setPreference(
        key: String?,
        value: String?,
        context: Context
    ) {
        val prefs =
            context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }
    //store data as key value pair in shared preferences
    fun setPreferenceObject(
        key: String?,
        value: Set<String>,
        context: Context
    ) {
        val prefs =
            context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = prefs.edit()
        editor.putStringSet(key,value)
        editor.commit()
    }
    // get value from shared preferences
    fun getPreferenceObject(key: String?, context: Context): Set<String>? {
        var result:Set<String>
        val shared =
            context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        var default: Set<String> = listOf("").toSet()
        result = shared.getStringSet(key,default) as Set<String>
        return result
    }
    // get value from shared preferences
    fun getPreference(key: String?, context: Context): String? {
        var result = ""
        val shared =
            context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        result = shared.getString(key, "").toString()
        return result
    }

    fun clearPreference(context: Context){
        val prefs =
            context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    fun setLocPreference(
        key: String?,
        value: String?,
        context: Context
    ) {
        val prefs =
            context.getSharedPreferences("location", Context.MODE_PRIVATE)
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    // get value from shared preferences
    fun getLocPreference(key: String?, context: Context): String? {
        var result = ""
        val shared =
            context.getSharedPreferences("location", Context.MODE_PRIVATE)
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        result = shared.getString(key, "").toString()
        return result
    }

    //9711842097
// get value from shared preferences
    fun clearLocPreference(context: Context) {
        val result = ""
        val shared =
            context.getSharedPreferences("location", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.clear()
        editor.apply()
    }
/*
    fun displayCustomAlertFinish(
        context: Context,
        message: String?
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.message_pop_up)
        dialog.setCancelable(false)
        // set the custom dialog components - text and button
        val messageTV =
            dialog.findViewById<TextView>(R.id.message)
        messageTV.setText(message)
        val btnOk =
            dialog.findViewById<Button>(R.id.ok)
        // if button is clicked, close the custom dialog
        btnOk.setOnClickListener {
            dialog.dismiss()
            (context as Activity).finish()
        }
        dialog.show()
    }

    fun displayCustomAlert(
        context: Context,
        message: String?
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.message_pop_up)
        dialog.setCancelable(false)
        // set the custom dialog components - text and button
        val messageTV =
            dialog.findViewById<TextView>(R.id.message)
        messageTV.setText(message)
        val btnOk =
            dialog.findViewById<Button>(R.id.ok)
        // if button is clicked, close the custom dialog
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun displayCustomAlert(
        context: Context?,
        title: String?,
        message: String?
    ) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.customdialog)
        dialog.setCancelable(false)
        // set the custom dialog components - text and button
        val text = dialog.findViewById<View>(R.id.txt_title) as TextView
        val messagetxt =
            dialog.findViewById<View>(R.id.alertText) as TextView
        text.text = title
        messagetxt.text = message
        val dialogButton =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showCustomMapDialog(activity: Activity?) {
        val viewGroup = activity?.findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(activity).inflate(R.layout.address_map_dialog, viewGroup, false)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.show()
        val btnOk =
            dialogView.findViewById<Button>(R.id.buttonOk)
        btnOk.setOnClickListener { alertDialog.cancel() }
    }

    fun displayMessageAlert(activity: Activity?, message: String?) {
        val viewGroup = activity?.findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(activity).inflate(R.layout.message_pop_up, viewGroup, false)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.show()
        val messageTV =
            dialogView.findViewById<TextView>(R.id.message)
        messageTV.setText(message)
        val btnOk =
            dialogView.findViewById<Button>(R.id.ok)
        btnOk.setOnClickListener { alertDialog.cancel() }
    }*/

    fun displayAlert(context: Context?, message: String?) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setTitle("Alert")
        builder1.setMessage(message)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Ok"
        ) { dialog, id -> dialog.cancel() }
        val alert11 = builder1.create()
        alert11.show()
    }

    fun displayTranConfirm(context: Context?, message: String?) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setTitle("Seller Information")
        builder1.setMessage(message)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Ok"
        ) {
                dialog, id -> dialog.cancel()
        }
        val alert11 = builder1.create()
        alert11.show()
    }

    // convert from bitmap to byte array
    fun getBytes(bitmap:Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    fun getImage(image:ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size);
    }

    /*fun showPranavaCustomProgressDialog(activity: Activity?,indicator:String){
        val viewGroup = activity?.findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(activity).inflate(R.layout.test, viewGroup, false)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.show()
        val btnOk =
            dialogView.findViewById<Button>(R.id.ok)
        val imageView = dialogView.findViewById<ImageView>(R.id.animation)
        activity?.let { Glide.with(it).load("file:///android_asset/animatedpranavalogo.gif").into(imageView) }
        //btnOk.setOnClickListener { alertDialog.cancel() }
    }*/

    fun showDialog(mProgressDialog: ProgressDialog) { /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.show();
                mProgressDialog.setMessage(MobieShopConstants.PROGRESSBAR_MESSAGE);
                mProgressDialog.setCancelable(false);

            }
        });*/
        Handler(Looper.getMainLooper()).post {
            mProgressDialog.show()
            mProgressDialog.setMessage(Constants.PROGRESSBAR_MESSAGE)
            mProgressDialog.setCancelable(false)
        }
    }


    fun showToast(activity: Context?, message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun hideDialog(mProgressDialog: ProgressDialog) { /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.hide();
            }
        });*/
        Handler(Looper.getMainLooper()).post { mProgressDialog.hide() }
    }

    fun isThisByteArray(value: Any): Boolean {
        val flag = false
        if (value is ByteArray) {
            return true
        }
        return flag
    }

    fun encodeUnicode(string: String): ByteArray {
        val charset = Charsets.UTF_8
        val byteArray = string.toByteArray(charset)
        //val x = string.toU
        println("String to unicode->"+byteArray.contentToString()) // [72, 101, 108, 108, 111]
        //println("unicode to String->"+byteArray.toString(charset)) // Hello
        println("String to unicode without contentToString->"+byteArray.toString(charset))
        return byteArray
    }

    fun decodeUnicode(byteArray:ByteArray): String {

        //val str = "\"\\u0048\\u0065\\u006C\\u006C\\u006F World\""


        val charset = Charsets.UTF_8
        println("unicode to String->"+byteArray.toString(charset)) // Hello
        return byteArray.toString(charset)
    }

    fun makePerticularTextBold(boldText:String,normalText:String): SpannableString {
        val str = SpannableString(boldText + normalText)
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            boldText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return str
    }

    fun makeTymTextBold(normalText:String,time:String): SpannableString {
        val str = SpannableString(normalText + time)
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            time.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return str
    }

    fun makeTextBold(text:String): SpannableString {
        val str = SpannableString(text)
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return str
    }

/*    fun showNotification(context: Context, messageBody: String?) {
        val defaultSound =
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notify)
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val NOTIFICATION_CHANNEL_ID = "101"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val notificationChannel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Notification",
                    NotificationManager.IMPORTANCE_MAX
                )
            //Configure Notification Channel
            notificationChannel.description = "EZLOCT Notifications"
            notificationChannel.enableLights(true)
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("EZLOCT")
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(messageBody)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
        notificationManager.notify(1, notificationBuilder.build())
    }*/

    fun getCroppedBitmap(bitmap:Bitmap):Bitmap {
        val output = Bitmap.createBitmap(bitmap.getWidth(),
            bitmap.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())
        paint.setAntiAlias(true)
        canvas.drawARGB(0, 0, 0, 0)
        paint.setColor(color)
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(
            (bitmap.getWidth() / 2).toFloat(), (bitmap.getHeight() / 2).toFloat(),
            (bitmap.getWidth() / 2).toFloat(), paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)
        val bmp = Bitmap.createScaledBitmap(output, bitmap.getWidth(), bitmap.getHeight(), false);
        return bmp
        //return output
    }

    @SuppressLint("LongLogTag")
    fun getCompleteAddressString(
        LATITUDE: Double,
        LONGITUDE: Double,
        context: Activity
    ): Address? {
        var strAdd:Address? = null
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses =
                geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                strAdd = addresses[0]
                //strAdd = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                /*cityS = addresses[0].locality
                stateS = addresses[0].adminArea
                countryS = addresses[0].countryName
                postalCodeS = addresses[0].postalCode*/
                Log.w(
                    "My Current loction address",
                    strAdd.getAddressLine(0) + "<- address"
                )
                //displayMessageAlert(context, "Address returned!")
            } else {
                Log.w("My Current loction address", "No Address returned!")
                //displayMessageAlert(context, "Can not get address in this location")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w("My Current loction address", "Can not get Address!")
            //displayMessageAlert(context, "Unable to get Address at this moment, Please try again")
        }
        return strAdd
    }

    fun getLocationFromAddress(context:Context, strAddress:String): Address? {
        val coder = Geocoder(context)
        val address:List<Address>
        var p1:Address? = null
        try
        {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null)
            {
                return null
            }
            val location = address.get(0)
            location.getLatitude()
            location.getLongitude()
            p1 = location
            return p1
        }catch (e:Exception){
            println("Exception occured while getting location from address ->$e")
        }
        return p1
    }



    /*fun getByteArray(bitmap:Bitmap): ByteArray? {
        val resizedProfileBM = RequestHandler.getResizedBitmap(bitmap, 100)
        val byteArray = resizedProfileBM?.let { it1 -> getBytes(it1) }
        return byteArray
    }*/


    /*fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }*/
}