package com.tagfans.plugin;

import java.util.List;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import tw.org.iii.beaconcontentsdk.BeaconContent;
import tw.org.iii.beaconcontentsdk.BeaconContentService;
import tw.org.iii.beaconcontentsdk.json.get_beacon_list.AppBeacon;
import tw.org.iii.beaconcontentsdk.json.push_message.Coupons;
import tw.org.iii.beaconcontentsdk.json.push_message.Products;
import tw.org.iii.beaconcontentsdk.json.push_message.Push_message;
import tw.org.iii.beaconcontentsdk.json.push_message.Result_content;

public class iiibeacon extends CordovaPlugin {
    private final String TAG = "iiibeacon";
    private boolean initialized = false;
    private ServiceResultReceiver scanWithKeyReceiver;
    private String server_ip = "iiibeacon.net";
    private String app_key = "a2872b54542d23ee51ec0437a997a114b7b0201a";

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("init")) {
            if (!initialized) {
                Activity activity = this.cordova.getActivity();
                scanWithKeyReceiver = new ServiceResultReceiver(null);
                Intent serviceIntent = new Intent(activity, BeaconContentService.class);
                serviceIntent.putExtra("server_ip", server_ip); // must provide
                serviceIntent.putExtra("app_key", app_key); // must provide
                // detect_timer is not necessary, default will update every 3 minutes.
                serviceIntent.putExtra("detect_timer", 120*1000); // in milliseconds, set to 2 minutes in this demo.
                serviceIntent.putExtra("receiver", scanWithKeyReceiver); // must provide

                activity.startService(serviceIntent);

                initialized = true;
            }
        } else if (action.equals("greet")) {

            String name = data.getString(0);
            String message = "Hello, " + name;
            callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }

    class ServiceResultReceiver extends ResultReceiver {
        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            Log.d(TAG, ""+resultCode);
            Log.d(TAG, resultData.toString());
            // result code 300: got beacon in the list.
            if (resultCode == 300) {
                String beaconJson = resultData.getString("beaconJson");
                Log.e(TAG, beaconJson);
                Gson gson = new Gson();
                AppBeacon = gson.fromJson(beaconJson, AppBeacon.class);
                String target_beacon = AppBeacon.getBeaconID();
                Log.e(TAG, target_beacon);
            }
        }
    }
}
