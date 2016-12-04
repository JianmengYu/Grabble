package com.yujianmeng.selp.grabble;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mButtonLeftTurn;
    private Button mButtonRightTurn;
    private Button mButtonEagle;
    private Button mButtonHelp;
    private Button mButtonGrabble;
    private Button mButtonMenu;
    //Dummy menu image
    private ImageView mMenu;
    private ImageView mHelp;
    private GoogleApiClient mGoogleApiClient;

    private Marker marker;
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    private LatLng mMyLocation;
    private float mMyBearing;
    private boolean zooming = false;//for test


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (mMenu.getVisibility() == View.VISIBLE) {
                mMenu.setVisibility(View.INVISIBLE);
            } else {
                mMenu.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //TODO implement map,remove dummy code
        //Dummy code, add a randonm point in GS and zoom to it
        //Dummy markers - 4 corners
        LatLng george1 = new LatLng(55.946, -3.184);
        MarkerOptions marker1 = new MarkerOptions()
                .position(george1)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point1");
        marker = mMap.addMarker(marker1);
        markers.add(marker);
        LatLng george2 = new LatLng(55.942, -3.192);
        MarkerOptions marker2 = new MarkerOptions()
                .position(george2)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point2");
        marker = mMap.addMarker(marker2);
        markers.add(marker);

        LatLng george3 = new LatLng(55.946, -3.192);
        MarkerOptions marker3 = new MarkerOptions()
                .position(george3)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point3");
        marker = mMap.addMarker(marker3);
        markers.add(marker);

        LatLng george4 = new LatLng(55.942, -3.184);
        MarkerOptions marker4 = new MarkerOptions()
                .position(george4)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point4");
        marker = mMap.addMarker(marker4);
        markers.add(marker);

        //Sqaure markers
        LatLng pointsamp1 = new LatLng(55.943, -3.189);
        MarkerOptions markersamp1 = new MarkerOptions()
                .position(pointsamp1)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point5");
        marker = mMap.addMarker(markersamp1);
        markers.add(marker);

        LatLng pointsamp2 = new LatLng(55.94352, -3.18944);
        MarkerOptions markersamp2 = new MarkerOptions()
                .position(pointsamp2)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point6");
        marker = mMap.addMarker(markersamp2);
        markers.add(marker);

        LatLng pointsamp3 = new LatLng(55.94378, -3.18912);
        MarkerOptions markersamp3 = new MarkerOptions()
                .position(pointsamp3)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point7");
        marker = mMap.addMarker(markersamp3);
        markers.add(marker);

        //TODO end of map dummy code

        //Initialize buttons on the map
        mButtonLeftTurn = (Button) findViewById(R.id.button_left_turn);
        mButtonRightTurn = (Button) findViewById(R.id.button_right_turn);
        mButtonEagle = (Button) findViewById(R.id.button_eagle);
        mButtonHelp = (Button) findViewById(R.id.button_help);
        mButtonGrabble = (Button) findViewById(R.id.button_grabble);
        mButtonMenu = (Button) findViewById(R.id.button_menu);
        mMenu = (ImageView) findViewById(R.id.mapdummymenu);
        mHelp = (ImageView) findViewById(R.id.map_help);

        mButtonLeftTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition temp = mMap.getCameraPosition();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder(temp).bearing(temp.bearing - 30).build()
                ));
            }
        });
        mButtonLeftTurn.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                CameraPosition temp = mMap.getCameraPosition();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder(temp).bearing(temp.bearing - 90).build()
                ));
                return true;
            }
        });
        mButtonRightTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition temp = mMap.getCameraPosition();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder(temp).bearing(temp.bearing + 30).build()
                ));
            }
        });
        mButtonRightTurn.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                CameraPosition temp = mMap.getCameraPosition();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder(temp).bearing(temp.bearing + 90).build()
                ));
                return true;
            }
        });
        //TODO add long click to check item number
        mButtonEagle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition temp = mMap.getCameraPosition();
                zooming = true;
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder(temp).zoom(temp.zoom - 2).build()
                ));
                disableControl();//Disable the control temporarily to prevent cheat.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CameraPosition temp = mMap.getCameraPosition();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder(temp).zoom(temp.zoom + 2).build()
                                ),
                                new GoogleMap.CancelableCallback() {
                                    @Override
                                    public void onFinish() {
                                        enableControl();
                                        zooming = false;
                                    }//Return controls

                                    @Override
                                    public void onCancel() {
                                    }
                                });
                    }
                }, 5000);
            }
        });
        mButtonGrabble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityScrabble.class);
                startActivity(i);
            }
        });
        mButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHelp.getVisibility() == View.VISIBLE) {
                    mHelp.setVisibility(View.INVISIBLE);
                } else {
                    mHelp.setVisibility(View.VISIBLE);
                }
            }
        });
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelp.setVisibility(View.INVISIBLE);
            }
        });
        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenu.getVisibility() == View.VISIBLE) {
                    mMenu.setVisibility(View.INVISIBLE);
                } else {
                    mMenu.setVisibility(View.VISIBLE);
                }
            }
        });
        //TODO change menu image into functional buttons
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), ActivityPager.class);
                startActivity(i);
            }
        });

        //Override marker click event
        //TODO complete implement of marker click event
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();
                //TODO add distance limit
                Toast.makeText(getApplicationContext(),
                        "Letter A collected!",
                        Toast.LENGTH_SHORT).show();
                        markers.remove(marker);
                return true;
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                boolean moved = true;//----------------------TEST
                for(Marker m : markers){
                    //TODO adjust item use case
                    if(Math.abs(m.getPosition().latitude  - latLng.latitude)  < 0.00005 &&
                       Math.abs(m.getPosition().longitude - latLng.longitude) < 0.00005) {
                                                //Adjust Click Range Here
                        Toast.makeText(MapActivity.this, "Letter A collected using grabber!",
                                Toast.LENGTH_SHORT).show();
                        m.remove();
                        markers.remove(m);
                        moved = false;//------------TEST
                        break;
                    }
                }
                //TODO remove following move code.
                if (moved && !zooming){
                disableControl();
                CameraPosition temp = mMap.getCameraPosition();
                boolean inRange = isInRange(latLng,0.0005);
                if(inRange){
                    mMyBearing = temp.bearing;
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder(temp)
                                    .target(latLng)//Initial location
                                    .build()),
                            new GoogleMap.CancelableCallback() {
                                @Override
                                public void onFinish() {enableControl();}//Return controls
                                @Override
                                public void onCancel() {}
                            });
                }else{
                    mMyBearing = (float) newBearing(latLng);
                    mMyLocation = latLng;
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder(temp)
                                    .target(mMyLocation)//Initial location
                                    .bearing(mMyBearing)
                                    .build()),
                            new GoogleMap.CancelableCallback() {
                                @Override
                                public void onFinish() {enableControl();}//Return controls
                                @Override
                                public void onCancel() {}
                            });
                }}
                //TODO end of test move code
            }
        });

        // --- finish initializing, starting app ---
        disableControl();//Prevent Cheat, re-enable during camera initialization

        //TODO get my location;
        /*
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
             ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {return;}
        mMap.setMyLocationEnabled(true);
        Location myLocation = mMap.getMyLocation();//Useless!
        LatLng myLatLng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
        */

        mMyLocation = pointsamp1;
        CameraPosition testAngle = new CameraPosition.Builder()
                .target(mMyLocation)//Initial location
                .zoom(19)
                .bearing(0)
                .tilt(45)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(testAngle),
                new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {enableControl();}//Return controls
                    @Override
                    public void onCancel() {}
                });
    }

    private void disableControl(){
        mButtonLeftTurn.setEnabled(false);
        mButtonRightTurn.setEnabled(false);
        mButtonEagle.setEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }
    private void enableControl(){
        mButtonLeftTurn.setEnabled(true);
        mButtonRightTurn.setEnabled(true);
        mButtonEagle.setEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        //Disable gestures and widgets, except the bearing one
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
    }

    private boolean isInRange(LatLng latLng, double range){
        double lat = Math.abs(mMyLocation.latitude  - latLng.latitude);
        double lng = Math.abs(mMyLocation.longitude  - latLng.longitude);
        return (range > Math.sqrt(Math.pow(lat,2) + Math.pow(lng,2)));
    }

    private double newBearing(LatLng latLng){
        //http://stackoverflow.com/questions/9457988/bearing-from-one-coordinate-to-another
        //with corrected toDegrees function;
        double srcLat = Math.toRadians(mMyLocation.latitude);
        double dstLat = Math.toRadians(latLng.latitude);
        double dLng = Math.toRadians(latLng.longitude - mMyLocation.longitude);

        double returnRad = Math.atan2(Math.sin(dLng) * Math.cos(dstLat),
                            Math.cos(srcLat) * Math.sin(dstLat) -
                            Math.sin(srcLat) * Math.cos(dstLat) * Math.cos(dLng));
        return Math.toDegrees((returnRad + 2 * Math.PI) % (2 * Math.PI));
    }
}
