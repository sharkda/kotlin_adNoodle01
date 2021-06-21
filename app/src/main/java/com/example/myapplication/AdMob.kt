package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

const val TAG:String = "gad"
const val Interstitial_Id_Test = "ca-app-pub-3940256099942544/1033173712"
const val Interstitial_Id = "ca-app-pub-9528408865218303/6493432911"

fun loadInterstitial(){
    Log.d(TAG,"loadInterstital")
}

class AdMan(val context:Context) {

    companion object{
        fun adMobConfig() {
            val devices = listOf<String>(AdRequest.DEVICE_ID_EMULATOR)
            Log.d(TAG,"adMobConfig() ${devices} <- AdMob devices")
            val requestConfig = RequestConfiguration.Builder()
                .setTestDeviceIds(devices)
                .build()
            MobileAds.setRequestConfiguration(requestConfig)
        }
        fun setAdListener0(adView:AdView){
            adView.adListener = object: AdListener(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.e(tEmerald, "onAdFailedToload ${p0.toString()}")
                }

                override fun onAdLoaded() {
                    Log.i(tEmerald,"onAdLoaded")
                }
            }
        }
    }

    private var mInterstitialAd:InterstitialAd? = null
    private var adRequest = AdRequest.Builder().build()
    private final val adId = Interstitial_Id_Test

    fun load(){
        Log.d(TAG, "load")
        InterstitialAd.load(context, adId, adRequest,
        object : InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.e(TAG, "onAdInterstitialFailedToload $adError?.message")
                mInterstitialAd = null
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "AdInterstitial was loaded.")
                mInterstitialAd = interstitialAd
                setCallback()
            }
        })
    }

    fun setCallback(){
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Interstitial was dismissed")
                load()
            }
            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Interstitial failed to show")
            }
            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Interstitial showed fullscreen content")
                mInterstitialAd = null;
            }
        }
    }
    fun showAd(activity: Activity){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}