package com.yujianmeng.selp.grabble;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String PREF_SAVE = "MySaveFile";

    private GoogleMap mMap;
    private Button mButtonLeftTurn;
    private Button mButtonRightTurn;
    private Button mButtonEagle;
    private Button mButtonHelp;
    private Button mButtonGrabble;
    private Button mButtonMenu;
    private RelativeLayout mMenu;
    private ImageView mMenuAchievement;
    private ImageView mMenuStatistics;
    private ImageView mMenuSettings;
    private ImageView mMenuAbout;
    private ImageView mHelp;
    private GoogleApiClient mGoogleApiClient;

    private Marker marker;
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    private LatLng mMyLocation;//For camera
    private LatLng mMyLocation2;//Actual location
    private int[] mLetters = new int[26];
    private int mEagle;
    private int mGrabber;
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
    protected void onResume(){
        super.onResume();
        SharedPreferences save = getSharedPreferences(PREF_SAVE, 0);
        for (int i = 0 ; i < 26 ; i++) {mLetters[i] = save.getInt("letter" + i,3);}//Change the initial letter amount here.
        mEagle = save.getInt("eagle",0);
        mGrabber = save.getInt("grabber",0);
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences save = getSharedPreferences(PREF_SAVE, 0);
        SharedPreferences.Editor editor = save.edit();
        for (int i = 0 ; i < 26 ; i++) {editor.putInt("letter" + i,mLetters[i]);}
        editor.putInt("eagle",mEagle);
        editor.putInt("grabber",mGrabber);
        editor.commit();
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

        //TODO complete readKML
        readKml();
        //TODO implement map,remove dummy code
        //Dummy code, add a randonm point in GS and zoom to it
        //Dummy markers - 4 corners
        /*
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
        */
        //Sqaure markers
        LatLng pointsamp1 = new LatLng(55.943, -3.189);
        //TEMP start location
        //TODO end of map dummy code

        //Initialize buttons on the map
        mButtonLeftTurn = (Button) findViewById(R.id.button_left_turn);
        mButtonRightTurn = (Button) findViewById(R.id.button_right_turn);
        mButtonEagle = (Button) findViewById(R.id.button_eagle);
        mButtonHelp = (Button) findViewById(R.id.button_help);
        mButtonGrabble = (Button) findViewById(R.id.button_grabble);
        mButtonMenu = (Button) findViewById(R.id.button_menu);
        mMenu = (RelativeLayout) findViewById(R.id.map_layout_menu);
        mHelp = (ImageView) findViewById(R.id.map_help);
        mMenuAchievement = (ImageView) findViewById(R.id.map_menu_achievement);
        mMenuStatistics = (ImageView) findViewById(R.id.map_menu_statistics);
        mMenuSettings = (ImageView) findViewById(R.id.map_menu_settings);
        mMenuAbout = (ImageView) findViewById(R.id.map_menu_about);

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
        mButtonEagle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEagle != 0){
                    mEagle--;
                CameraPosition temp = mMap.getCameraPosition();
                zooming = true;
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder(temp).zoom(temp.zoom - 1).build()
                ));
                disableControl();//Disable the control temporarily to prevent cheat.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CameraPosition temp = mMap.getCameraPosition();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder(temp).zoom(temp.zoom + 1).build()
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
                }, 5000);}else{
                    Toast.makeText(MapActivity.this, "You have no Eagle Eye left!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        mButtonEagle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mEagle == 0){
                    Toast.makeText(MapActivity.this, "You have no Eagle Eye left!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MapActivity.this, "You have " + mEagle + " Eagle Eye left!",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
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
        //http://stackoverflow.com/questions/28177882/is-it-possible-to-go-to-a-specific-page-of-a-viewpager-from-other-activity
        mMenuAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), ActivityPager.class);
                startActivity(i);
            }
        });
        mMenuStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), ActivityPager.class);
                i.putExtra("EXTRA_PAGE",1);
                startActivity(i);
            }
        });
        mMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), ActivityPager.class);
                i.putExtra("EXTRA_PAGE",2);
                startActivity(i);
            }
        });
        mMenuAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), ActivityPager.class);
                i.putExtra("EXTRA_PAGE",3);
                startActivity(i);
            }
        });

        //Override marker click event
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(isInRange(mMyLocation2,marker.getPosition(),0.0003)){
                    mLetters[Grabble.charToInt(Character.toLowerCase(marker.getTitle().charAt(0)))]++;
                    Log.i("TAG",marker.getSnippet());
                    MarkerLab.get(getApplicationContext()).updateMarkers(marker.getSnippet());
                    marker.remove();
                    markers.remove(marker);
                    Toast.makeText(getApplicationContext(),
                        "Letter " + marker.getTitle() + " collected!",
                        Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Too Far! \nYou have " + mGrabber + " Grabber left.",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                boolean moved = true;//----------------------TEST
                if (mGrabber != 0){
                    mGrabber--;
                for(Marker m : markers){
                    if(Math.abs(m.getPosition().latitude  - latLng.latitude)  < 0.00005 &&
                       Math.abs(m.getPosition().longitude - latLng.longitude) < 0.00005) {
                                                //Adjust Click Range Here
                        mLetters[Grabble.charToInt(Character.toLowerCase(m.getTitle().charAt(0)))]++;
                        Toast.makeText(MapActivity.this, "Letter " + m.getTitle() + " collected using grabber!",
                                Toast.LENGTH_SHORT).show();
                        MarkerLab.get(getApplicationContext()).updateMarkers(marker.getSnippet());
                        m.remove();
                        markers.remove(m);
                        moved = false;//------------TEST
                        break;
                    }
                }
                }else{
                    Toast.makeText(MapActivity.this, "You don't have any Grabber Left!",
                            Toast.LENGTH_SHORT).show();
                }
                //TODO remove following move code.
                if (moved && !zooming){
                disableControl();
                CameraPosition temp = mMap.getCameraPosition();
                boolean inRange = isInRange(mMyLocation,latLng,0.0005);
                if(inRange){
                    mMyLocation2 = latLng;
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
                    mMyLocation2 = latLng;
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
        mMyLocation2 = pointsamp1;


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

    private boolean isInRange(LatLng latLng, LatLng latLng2, double range){
        double lat = Math.abs(latLng.latitude  - latLng2.latitude);
        double lng = Math.abs(latLng.longitude  - latLng2.longitude);
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

    private void readKml(){
        MarkerLab markerLab = MarkerLab.get(getApplicationContext());
        List<MyMarker> markerList = markerLab.getMarkers();
        for (MyMarker m : markerList){
            if (!m.ismCollected()){
                marker = mMap.addMarker(new MarkerOptions()
                        .position(m.getLatLng())
                        .title(m.getmName() + "")
                        .snippet(m.getmDescription())
                        .icon(BitmapDescriptorFactory.fromResource(getImageString(m.getmName()))));
                markers.add(marker);
            }
        }
    }

    private int getImageString(char name){
        switch (name){
            case 'A':return R.drawable.map_pickable_a;
            case 'B':return R.drawable.map_pickable_b;
            case 'C':return R.drawable.map_pickable_c;
            case 'D':return R.drawable.map_pickable_d;
            case 'E':return R.drawable.map_pickable_e;
            case 'F':return R.drawable.map_pickable_f;
            case 'G':return R.drawable.map_pickable_g;
            case 'H':return R.drawable.map_pickable_h;
            case 'I':return R.drawable.map_pickable_i;
            case 'J':return R.drawable.map_pickable_j;
            case 'K':return R.drawable.map_pickable_k;
            case 'L':return R.drawable.map_pickable_l;
            case 'M':return R.drawable.map_pickable_m;
            case 'N':return R.drawable.map_pickable_n;
            case 'O':return R.drawable.map_pickable_o;
            case 'P':return R.drawable.map_pickable_p;
            case 'Q':return R.drawable.map_pickable_q;
            case 'R':return R.drawable.map_pickable_r;
            case 'S':return R.drawable.map_pickable_s;
            case 'T':return R.drawable.map_pickable_t;
            case 'U':return R.drawable.map_pickable_u;
            case 'V':return R.drawable.map_pickable_v;
            case 'W':return R.drawable.map_pickable_w;
            case 'X':return R.drawable.map_pickable_x;
            case 'Y':return R.drawable.map_pickable_y;
            case 'Z':return R.drawable.map_pickable_z;
            default:return R.drawable.map_pickable;
        }
    }
}
