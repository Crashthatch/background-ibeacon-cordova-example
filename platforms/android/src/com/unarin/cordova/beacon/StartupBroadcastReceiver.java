package com.unarin.cordova.beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("com.unarin.cordova.beacon", "StartupBroadcastReceiver starting BackgroundBeaconService...");
		//Start BackgroundBeaconService on boot.
		Intent startServiceIntent = new Intent(context.getApplicationContext(), BackgroundBeaconService.class);
		context.getApplicationContext().startService(startServiceIntent);
	}

}