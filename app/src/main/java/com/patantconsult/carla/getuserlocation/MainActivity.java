package com.patantconsult.carla.getuserlocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;

    String provider;
    private static final int PERMISSION_REQUEST_CODE= 1;


    @Override
    public Object getSystemService(String name) {
        return super.getSystemService(name);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case 0:{

                if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {

               Toast message = Toast.makeText(getApplicationContext() ,"Access Granted. You can now access loaction data", Toast.LENGTH_LONG);

                message.show();
                }

                else {

                    Toast message = Toast.makeText(getApplicationContext() ,"Access Denied.  You cannot access location data", Toast.LENGTH_LONG);

                    message.show();

                }
               break;
            }



        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        if (checkPermission()){
            locationManager.requestLocationUpdates(provider, 400, 1, this);}
       else {

       locationManager.removeUpdates(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//Check permission to access
    private boolean checkPermission (){
        Location location;
        int resultGPS = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int resultLocation = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);
        if (resultGPS == PackageManager.PERMISSION_GRANTED  || resultLocation == PackageManager.PERMISSION_GRANTED){

            Log.i("permission", "permission granted");
            return true;

        } else {
            Log.i("permission", "permission denied");
            return false;
        }

    }



//Request permission to location services sought
    private void requestPermission(){


        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                    Toast message = Toast.makeText(getApplicationContext(), "Location  allows for accessing location information. Kindly enable location in your settinhs to proceed, ", Toast.LENGTH_LONG);

                    message.show();


                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);


                }



            }


        }catch(Exception e){

        }


    }
    //Insert on resume cod
    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPermission()){

            requestPermission();
        }

   else {

            if (checkPermission()){
                locationManager.requestLocationUpdates(provider, 400,1,this);}

        }

    }


    @Override
    protected void onPause() {
        super.onPause();

         if (!checkPermission()){

             requestPermission();
         }

        else {
             locationManager.removeUpdates(this);
         }





    }

    @Override
    public void onLocationChanged(Location location) {
        Double longitude = location.getLongitude();
        Double latitude = location.getLatitude();
        Log.i("long", longitude.toString());
        Log.i("lat", latitude.toString());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
