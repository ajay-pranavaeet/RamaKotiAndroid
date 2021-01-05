package com.pramanam.ramakoti

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.pranavaeet.sriramakoti.utils.ConnectionDetector
import com.pranavaeet.sriramakoti.utils.SRKUtil
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 5000 // 3 sec
    var simpleVideoView: VideoView? = null

    // declaring a null variable for MediaController
    var mediaControls: MediaController? = null
    var connectionDetector: ConnectionDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        CookieHandler.setDefault(CookieManager(null, CookiePolicy.ACCEPT_ALL))
        connectionDetector = ConnectionDetector.getInstance(applicationContext)
        //val imageView = findViewById<ImageView>(R.id.animated_loader)
        //Glide.with(this).asGif().load(R.raw.ezloctanimatedgif2).into(imageView)
        //showSplash()
    }

    fun showSplashVideo(){
        simpleVideoView = findViewById<View>(R.id.splash_video) as VideoView

        /*if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls?.setAnchorView(this.simpleVideoView)
        }

        // set the media controller for video view
        simpleVideoView?.setMediaController(mediaControls)
        mediaControls?.hide()*/
        // set the absolute path of the video file which is going to be played
        simpleVideoView!!.setVideoURI(
            Uri.parse("android.resource://"
                + packageName + "/" + R.raw.splash_video))

        simpleVideoView?.requestFocus()

        // starting the video
        simpleVideoView?.start()

        // display a toast message
        // after the video is completed
        simpleVideoView?.setOnCompletionListener {
            Toast.makeText(applicationContext, "Video completed",
                Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LandingScreenActivity::class.java))
        }

        // display a toast message if any
        // error occurs while playing the video
        simpleVideoView?.setOnErrorListener { mp, what, extra ->
            Toast.makeText(applicationContext, "An Error Occured " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun showSplash() {
        Handler().postDelayed({
            val userId = SRKUtil.getPreference("userId", this)
            if (userId != "") {
                startActivity(Intent(this, MainActivity::class.java))
            } else if (connectionDetector!!.isConnectedToInternet) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                //SRKUtil.displayCustomAlertFinish(this, Constants.NO_INTERNET)
            }
        }, SPLASH_TIME_OUT)
    }

    override fun onResume() {
        super.onResume()
        showSplashVideo()
    }
}

