package com.example.dell1.lesson15maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;  //m stands for member variable of googlemap
    //this is hungarian notation

    LatLng lastknownloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //loads the map asynchronously
        mapFragment.getMapAsync(this);    //elsath download nhi karta
        //network call is made which downloads the contents of the map
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//
//        sydney = new LatLng(-34, 151);
//        delhi = new LatLng(28.35,77.62);
//
//
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
////        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,20.0f));
//
////        mMap.animateCamera(CameraUpdateFactory.zoomIn());
////        mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("hello").draggable(true));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10.0f));
//
//        markerend = sydney;
//        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//                MarkerOptions newMarkerOptions=new MarkerOptions().position(markerend).title(marker.getId()).draggable(true);
//                mMap.addMarker(newMarkerOptions);
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerend,10.0f));
//                markerend=marker.getPosition();
//            }
//
//            @Override
//            public void onMarkerDrag(Marker marker) {
//
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//                 MarkerOptions markerOptions=new MarkerOptions().position(marker.getPosition()).title(marker.getId()).draggable(true);
//                 mMap.addMarker(markerOptions);
//                 mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),10.0f));
//                PolylineOptions polylineOptions=new PolylineOptions().add(markerend,marker.getPosition());
//                mMap.addPolyline(polylineOptions);
//            }
//        });
//
//    }

    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if(checkLocationPermission()){
            attachLocationListener();
        }
        
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    attachLocationListener();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @SuppressLint("MissingPermission")
    private void attachLocationListener() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000,
                    25,
                    this);
        }
        else{
            Toast.makeText(this,"sorry",Toast.LENGTH_LONG).show();
            Log.e("TAG","sorry");
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng mycurrentloc=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions().position(mycurrentloc).title("mycurrentlocation").draggable(true);
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mycurrentloc,15.0f));
        
        if(lastknownloc==null){
            lastknownloc=mycurrentloc;
        }
        else{

            PolylineOptions polylineOptions=new PolylineOptions().add(mycurrentloc,lastknownloc);
            mMap.addPolyline(polylineOptions);
            lastknownloc=mycurrentloc;
//            draw polyline between
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
