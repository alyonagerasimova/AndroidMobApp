package com.mobdev.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

class MyLocationListener implements LocationListener {

    static Location imHere;
    Context context;

    public MyLocationListener(Context context){
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public static Location SetUpLocationListener(Context context) {
        try{
            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new MyLocationListener(null);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, locationListener);
            imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return imHere;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location loc) {
        imHere = loc;
        loc.getLatitude();
        loc.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "GPS disabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "GPS enabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
