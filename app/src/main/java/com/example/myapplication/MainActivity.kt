package com.example.myapplication

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.size
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener

const val tEmerald:String = "emerald"
const val Banner_Id = "ca-app-pub-9528408865218303/2046950046"
const val Banner_Id_Test = "ca-app-pub-3940256099942544/6300978111"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var mBannerContainer:FrameLayout
    lateinit var mAdView : AdView
    val bannerId = Banner_Id_Test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mBannerContainer = binding.adViewContainer

        Log.d(tEmerald,"MobileAds.Init")
        MobileAds.initialize(this, object : OnInitializationCompleteListener {
            override fun onInitializationComplete(initStatus: InitializationStatus?) {
                Log.i(tEmerald, "MobileAds.onInitializationComplete $initStatus)")
            }
        })
        AdMan.adMobConfig()
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        setupBanner()
        binding.adViewContainer.addView(mAdView)
        binding.adViewContainer.setBackgroundColor(
            ContextCompat.getColor(this, R.color.purple_200)
        )

    }

    private fun setupBanner(){
        mAdView = AdView(this)
        mAdView.adSize = adSize //AdSize.BANNER
        mAdView.adUnitId = bannerId
        AdMan.setAdListener0(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    // Determine the screen width (less decorations) to use for the ad width.
    // If the ad hasn't been laid out, default to the full screen width.
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = mBannerContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}