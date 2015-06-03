package com.unarin.cordova.beacon;

import android.app.Application;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import org.altbeacon.beacon.*;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.service.BeaconService;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 * Created by Tom on 01/06/2015.
 *
 * For reference only. Not used by the actual plugin- superseded by BackgroundBeaconService which doesn't require
 * the cordova plugin to take over the whole Application.
 */
public class BackgroundBeaconApplication extends Application implements BootstrapNotifier {
	private BackgroundPowerSaver backgroundPowerSaver;
	private BeaconManager iBeaconManager;
	private RegionBootstrap regionBootstrap;

	public void onCreate() {
		Log.d("com.unarin.cordova.beacon", "BACKGROUND: Creating BackgroundBeaconApplication.");
		super.onCreate();
		// Simply constructing this class and holding a reference to it
		// enables auto battery saving of about 60%
		backgroundPowerSaver = new BackgroundPowerSaver(this);
		iBeaconManager = BeaconManager.getInstanceForApplication(this);
		iBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
		iBeaconManager.setBackgroundBetweenScanPeriod(15000l);
		iBeaconManager.setBackgroundScanPeriod(5000l);
		iBeaconManager.setDebug(true);

		//Start the Services that will keep checking for beacons in the background, even if the application is closed (swiped away from the app-switcher).
		Intent startServiceIntent = new Intent(this.getApplicationContext(), BeaconService.class);
		this.getApplicationContext().startService(startServiceIntent);
		startServiceIntent = new Intent(this.getApplicationContext(), BeaconIntentProcessor.class);
		this.getApplicationContext().startService(startServiceIntent);

		Region region = new Region("backgroundRegion", Identifier.parse("02424C49-5350-4F00-9DBF-3F5307B1159A"), null, null);
		regionBootstrap = new RegionBootstrap(this, region);
		Log.d("com.unarin.cordova.beacon", "BACKGROUND: Created RegionBootstrap in BackgroundBeaconApplication.");
	}

	@Override
	public void didEnterRegion(Region region) {
		Log.d("com.unarin.cordova.beacon", "BackgroundBeaconApplication.didEnterRegion called!");
	}

	@Override
	public void didExitRegion(Region region) {
		Log.d("com.unarin.cordova.beacon", "BackgroundBeaconApplication.didExitRegion called!");
	}

	@Override
	public void didDetermineStateForRegion(int i, Region region) {
		Log.d("com.unarin.cordova.beacon", "BackgroundBeaconApplication.didDetermineStateForRegion called!");
	}
}