package com.yujianmeng.selp.grabble;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/** DISCLAIMER: the location codes are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 * (The locatr part, obsoleted methods are replaced with code from these guides:)
 * http://stackoverflow.com/questions/22493465/check-if-correct-google-play-service-available-unfortunately-application-has-s
 */

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
    private Button mButtonEagle2;
    private Button mButtonHelp2;
    private Button mButtonGrabble2;
    private Button mButtonMenu2;
    private RelativeLayout mPrompt;
    private ImageView mPromptYes;
    private GoogleApiClient mGoogleApiClient;

    private Marker marker;
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    private boolean leftHand;
    private boolean noHelp;
    private boolean levelBonus;
    private int level;
    private double grabDistance = 0.0003;
    private LatLng mMyLocation;//For camera
    private LatLng mMyLocation2;//Actual location
    private int[] mLetters = new int[26];
    private int mEagle;
    private int mGrabber;
    private float mMyBearing;
    private boolean promptOpened = false;
    private boolean zooming = false;//for test

    AchievementLab achievementLab;
    List<Achievement> achievements;
    MarkerLab markerLab;
    List<MyMarker> markerList;

    //Statistics
    private int collected;
    private int collectedToday;
    private float walked;
    private float walkedToday;
    private int eagleUsed;
    private int grabberUsed;
    private String weekDay;
    private boolean startedWalking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        mButtonEagle2 = (Button) findViewById(R.id.button_eagle2);
        mButtonHelp2 = (Button) findViewById(R.id.button_help2);
        mButtonGrabble2 = (Button) findViewById(R.id.button_grabble2);
        mButtonMenu2 = (Button) findViewById(R.id.button_menu2);

        mPrompt = (RelativeLayout) findViewById(R.id.prompt_layout);
        mPromptYes = (ImageView) findViewById(R.id.prompt_yes);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Log.i("LOCATION","Connected");
                        checkPermission();
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationRequest.setNumUpdates(5000);//Set times to track
                        locationRequest.setInterval(5000);//Set interval of track
                        locationRequest.setFastestInterval(1000);
                        LocationServices.FusedLocationApi
                                .requestLocationUpdates(mGoogleApiClient, locationRequest, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        Log.i("LOCATION", "Got a fix: " + location);
                                        if (!zooming) {
                                            //Doesn't Track when using eagle eye item
                                            if (startedWalking) {
                                                //Start to record statistics when start playing
                                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                                float range = distFrom(mMyLocation2, latLng);
                                                Log.i("TAG", latLng.toString());
                                                walked += range;
                                                walkedToday += range;
                                                CameraPosition temp = mMap.getCameraPosition();
                                                boolean inRange = isInRange(mMyLocation, latLng, 0.0005);
                                                if (inRange) {
                                                    mMyLocation2 = latLng;
                                                    mMyBearing = temp.bearing;
                                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                                            new CameraPosition.Builder(temp)
                                                                    .target(latLng)//Initial location
                                                                    .build()));
                                                } else {
                                                    mMyBearing = (float) newBearing(latLng);
                                                    mMyLocation = latLng;
                                                    mMyLocation2 = latLng;
                                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                                            new CameraPosition.Builder(temp)
                                                                    .target(mMyLocation)//Initial location
                                                                    .bearing(mMyBearing)
                                                                    .build()));
                                                }
                                            } else {
                                                //Move to first point
                                                mMyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                                mMyLocation2 = mMyLocation;
                                                CameraPosition testAngle = new CameraPosition.Builder()
                                                        .target(mMyLocation)
                                                        .zoom(19)
                                                        .bearing(0)
                                                        .tilt(45)
                                                        .build();
                                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(testAngle),
                                                        new GoogleMap.CancelableCallback() {
                                                            @Override
                                                            public void onFinish() {
                                                                enableControl();
                                                                startedWalking = true;
                                                            }//Return controls

                                                            @Override
                                                            public void onCancel() {
                                                            }
                                                        });
                                            }
                                        }


                                    }
                                });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.i("LOCATION","Connection suspended");
                        finish();
                    }
                })
                .build();
    }

    @Override
    protected void onResume() {
        SharedPreferences save = getSharedPreferences(PREF_SAVE, 0);
        for (int i = 0; i < 26; i++) {
            mLetters[i] = save.getInt("letter" + i, 3);
        }//Change the initial letter amount here.
        mEagle = save.getInt("eagle", 0);
        mGrabber = save.getInt("grabber", 0);
        collected = save.getInt("collected", 0);
        collectedToday = save.getInt("collectedToday", 0);
        walked = save.getFloat("walked", 0);
        walkedToday = save.getFloat("walkedToday", 0);
        eagleUsed = save.getInt("eagleUsed", 0);
        grabberUsed = save.getInt("grabberUsed", 0);
        leftHand = save.getBoolean("leftHand", false);
        noHelp = save.getBoolean("noHelp", false);
        levelBonus = save.getBoolean("levelBonus", true);
        level = save.getInt("level", 0);
        if (levelBonus) {
            grabDistance = 0.0003 * (1 + 0.01 * level);
        } else {
            grabDistance = 0.0003;
        }
        Log.i("MAP", "onResume called");
        super.onResume();
        updateUI();
        //http://stackoverflow.com/questions/22493465/check-if-correct-google-play-service-available-unfortunately-application-has-s
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = googleApiAvailability.getErrorDialog(this, errorCode, 0, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            errorDialog.show();
        }
    }

    @Override
    protected void onPause() {
        SharedPreferences save = getSharedPreferences(PREF_SAVE, 0);
        SharedPreferences.Editor editor = save.edit();
        for (int i = 0; i < 26; i++) {
            editor.putInt("letter" + i, mLetters[i]);
        }
        editor.putInt("eagle", mEagle);
        editor.putInt("grabber", mGrabber);
        editor.putInt("collected", collected);
        editor.putInt("collectedToday", collectedToday);
        editor.putFloat("walked", (walked));
        editor.putFloat("walkedToday", (walkedToday));
        editor.putInt("eagleUsed", eagleUsed);
        editor.putInt("grabberUsed", grabberUsed);
        editor.putString("weekDay", weekDay);
        editor.commit();
        Log.i("MAP", "onPause called");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.i("MAP", "onStart called");
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        Log.i("MAP", "onStop called");
        super.onStop();
        mGoogleApiClient.disconnect();
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!promptOpened) {
                promptOpened = true;
                mPrompt.setVisibility(View.VISIBLE);
                return false;
            }
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

        SharedPreferences save = getSharedPreferences(PREF_SAVE, 0);
        weekDay = save.getString("weekDay", "noDay");
        Log.i("MAP", "day:" + weekDay);
        readKml();

        achievementLab = AchievementLab.get(this);
        achievements = achievementLab.getAchievements();

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

        View.OnClickListener eagleOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEagle != 0) {
                    mEagle--;
                    eagleUsed++;
                    checkAchievement();
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
                    }, 5000);
                } else {
                    Toast.makeText(MapActivity.this, "You have no Eagle Eye left!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        View.OnLongClickListener eagleOnLongClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mEagle == 0) {
                    Toast.makeText(MapActivity.this, "You have no Eagle Eye left!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapActivity.this, "You have " + mEagle + " Eagle Eye left!",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        };
        View.OnClickListener grabbleOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityScrabble.class);
                startActivity(i);
            }
        };
        View.OnClickListener helpOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHelp.getVisibility() == View.VISIBLE) {
                    mHelp.setVisibility(View.INVISIBLE);
                } else {
                    mHelp.setVisibility(View.VISIBLE);
                }
            }
        };
        View.OnClickListener menuOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenu.getVisibility() == View.VISIBLE) {
                    mMenu.setVisibility(View.INVISIBLE);
                } else {
                    mMenu.setVisibility(View.VISIBLE);
                }
            }
        };
        mButtonEagle.setOnClickListener(eagleOnClick);
        mButtonEagle.setOnLongClickListener(eagleOnLongClick);
        mButtonGrabble.setOnClickListener(grabbleOnClick);
        mButtonHelp.setOnClickListener(helpOnClick);
        mButtonMenu.setOnClickListener(menuOnClick);
        mButtonEagle2.setOnClickListener(eagleOnClick);
        mButtonEagle2.setOnLongClickListener(eagleOnLongClick);
        mButtonGrabble2.setOnClickListener(grabbleOnClick);
        mButtonHelp2.setOnClickListener(helpOnClick);
        mButtonMenu2.setOnClickListener(menuOnClick);
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelp.setVisibility(View.INVISIBLE);
            }
        });

        mPromptYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptOpened = false;
                mPrompt.setVisibility(View.INVISIBLE);
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
                i.putExtra("EXTRA_PAGE", 1);
                startActivity(i);
            }
        });
        mMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), ActivityPager.class);
                i.putExtra("EXTRA_PAGE", 2);
                startActivity(i);
            }
        });
        mMenuAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), ActivityPager.class);
                i.putExtra("EXTRA_PAGE", 3);
                startActivity(i);
            }
        });

        //Override marker click event
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (isInRange(mMyLocation2, marker.getPosition(), grabDistance)) {
                    mLetters[Grabble.charToInt(Character.toLowerCase(marker.getTitle().charAt(0)))]++;
                    Log.i("TAG", marker.getSnippet());
                    MarkerLab.get(getApplicationContext()).updateMarkers(marker.getSnippet());
                    marker.remove();
                    markers.remove(marker);
                    collected++;
                    collectedToday++;
                    Toast.makeText(getApplicationContext(),
                            "Letter " + marker.getTitle() + " collected!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Too Far! \nYou have " + mGrabber + " Grabber left.",
                            Toast.LENGTH_SHORT).show();
                }
                checkAchievement();
                return true;
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                boolean moved = true;//----------------------TEST
                for (Marker m : markers) {
                    if (Math.abs(m.getPosition().latitude - latLng.latitude) < 0.00005 &&
                            Math.abs(m.getPosition().longitude - latLng.longitude) < 0.00005) {
                        //Adjust Click Range Here
                        if (mGrabber != 0) {
                            mLetters[Grabble.charToInt(Character.toLowerCase(m.getTitle().charAt(0)))]++;
                            Toast.makeText(MapActivity.this, "Letter " + m.getTitle() + " collected using grabber!",
                                    Toast.LENGTH_SHORT).show();
                            MarkerLab.get(getApplicationContext()).updateMarkers(marker.getSnippet());
                            m.remove();
                            markers.remove(m);
                            mGrabber--;
                            grabberUsed++;
                            collected++;
                            collectedToday++;
                            moved = false;//------------TEST
                        } else {
                            Toast.makeText(MapActivity.this, "You don't have any Grabber Left!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
                //TODO remove following move code.
                if (moved && !zooming) {
                    disableControl();
                    float range = distFrom(mMyLocation2, latLng);
                    Log.i("TAG",latLng.toString());
                    walked += range;
                    walkedToday += range;
                    CameraPosition temp = mMap.getCameraPosition();
                    boolean inRange = isInRange(mMyLocation, latLng, 0.0005);
                    if (inRange) {
                        mMyLocation2 = latLng;
                        mMyBearing = temp.bearing;
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder(temp)
                                        .target(latLng)//Initial location
                                        .build()),
                                new GoogleMap.CancelableCallback() {
                                    @Override
                                    public void onFinish() {
                                        enableControl();
                                    }//Return controls

                                    @Override
                                    public void onCancel() {
                                    }
                                });
                    } else {
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
                                    public void onFinish() {
                                        enableControl();
                                    }//Return controls

                                    @Override
                                    public void onCancel() {
                                    }
                                });
                    }
                }
                //TODO end of test move code
                checkAchievement();
            }
        });

        // --- finish initializing, starting app ---
        disableControl();//Prevent Cheat, re-enable during camera initialization
        checkPermission();

        //Set a Initial camera location, move to my location later.
        LatLng pointsamp1 = new LatLng(55.9414, -3.1884);//Temporary location before zoom to actual location
        mMyLocation = pointsamp1;//Location for camera
        mMyLocation2 = pointsamp1;//Location for actual position
        CameraPosition testAngle = new CameraPosition.Builder()
                .target(mMyLocation)//Initial location
                .zoom(16)//19 is normal
                .bearing(0)
                .tilt(45)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(testAngle));
    }

    private void updateUI(){
        //Update left hand mode/ Help Button
        if (leftHand){
            mButtonHelp.setVisibility(View.INVISIBLE);
            mButtonEagle.setVisibility(View.INVISIBLE);
            mButtonMenu.setVisibility(View.INVISIBLE);
            mButtonGrabble.setVisibility(View.INVISIBLE);
            mButtonHelp2.setVisibility(View.VISIBLE);
            mButtonEagle2.setVisibility(View.VISIBLE);
            mButtonMenu2.setVisibility(View.VISIBLE);
            mButtonGrabble2.setVisibility(View.VISIBLE);
        }else{
            mButtonHelp.setVisibility(View.VISIBLE);
            mButtonEagle.setVisibility(View.VISIBLE);
            mButtonMenu.setVisibility(View.VISIBLE);
            mButtonGrabble.setVisibility(View.VISIBLE);
            mButtonHelp2.setVisibility(View.INVISIBLE);
            mButtonEagle2.setVisibility(View.INVISIBLE);
            mButtonMenu2.setVisibility(View.INVISIBLE);
            mButtonGrabble2.setVisibility(View.INVISIBLE);
        }
        if (noHelp){
            mButtonHelp.setVisibility(View.INVISIBLE);
            mButtonHelp2.setVisibility(View.INVISIBLE);
        }
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

    public static float distFrom(LatLng latlng1, LatLng latlng2) {
        //http://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
        float lat1 = (float) latlng1.latitude;
        float lng1 = (float) latlng1.longitude;
        float lat2 = (float) latlng2.latitude;
        float lng2 = (float) latlng2.longitude;
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist;
    }

    private void checkAchievement(){
        String unlocked = "no";
        //Check achievement unlocked
        if (walked >= 1000){if(achievementLab.updateAchievement("Walker")){//This helps run speed?
            unlocked = "Walker";}}
        if (walked >= 10000){if(achievementLab.updateAchievement("Runner")){
            unlocked = "Runner";}}
        if (walked >= 42000){if(achievementLab.updateAchievement("Marathon Runner")){
            unlocked = "Marathon Runner";}}
        if (walked >= 100000){if(achievementLab.updateAchievement("Flash")){
            unlocked = "Flash";}}
        if (walked >= 500000){if(achievementLab.updateAchievement("Traveler")){
            unlocked = "Traveler";}}
        if (collected >= 20){if(achievementLab.updateAchievement("Collector")){
            unlocked = "Collector";}}
        if (collected >= 100){if(achievementLab.updateAchievement("Scavenger")){
            unlocked = "Scavenger";}}
        if (collected >= 500){if(achievementLab.updateAchievement("Pack Rat")){
            unlocked = "Pack Rat";}}
        if (collected >= 1000){if(achievementLab.updateAchievement("Connoisseur")){
            unlocked = "Connoisseur";}}
        if (collectedToday >= 1000){if(achievementLab.updateAchievement("Antiquarian")){
            unlocked = "Antiquarian";}}
        if (grabberUsed >= 5){if(achievementLab.updateAchievement("Grabber User")){
            unlocked = "Grabber User";}}
        if (grabberUsed >= 50){if(achievementLab.updateAchievement("Lazy Grabber User")){
            unlocked = "Lazy Grabber User";}}
        if (eagleUsed >= 5){if(achievementLab.updateAchievement("Peeping Tom")){
            unlocked = "Peeping Tom";}}
        if (eagleUsed >= 50){if(achievementLab.updateAchievement("Clairvoyant")){
            unlocked = "Clairvoyant";}}
        //Start construct Toast
        if (!unlocked.equals("no")){
            //Prompt the Player if they unlocked an achievement.
            //Borrows the achievement Layout of achievement list item.
            Toast t = new Toast(getApplicationContext());
            t.setGravity(Gravity.CENTER,0,150);
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View tl = inflater.inflate(R.layout.list_item_achievement,null);
            RelativeLayout rl2 = (RelativeLayout) tl.findViewById(R.id.achievement_brown_bg);
            RelativeLayout rl = (RelativeLayout) tl.findViewById(R.id.achievement_bg);
            rl.setBackgroundResource(R.drawable.design_achievement_bg_t);
            rl2.setBackgroundResource(R.color.colorTransparent);
            ImageView im = (ImageView) tl.findViewById(R.id.achievement_image);
            TextView tv1 = (TextView) tl.findViewById(R.id.achievement_name);
            TextView tv2 = (TextView) tl.findViewById(R.id.achievement_descript);
            TextView tv3 = (TextView) tl.findViewById(R.id.achievement_time);
            Typeface font = Typeface.createFromAsset(getAssets(), "generica_bold.otf");
            Achievement a = new Achievement();
            a.setmName(unlocked);
            im.setImageResource(a.getImageString());
            tv1.setTypeface(font);
            tv2.setTypeface(font);
            tv1.setText("Achievement Unlocked!");
            tv1.setTextSize(10);
            tv2.setText("\"" + unlocked + "\"");
            tv2.setTextSize(15);
            tv3.setText("");
            t.setView(tl);
            t.show();
        }
    }

    private boolean readKml(){
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime()).toLowerCase();
        //Case 0: No internet:
        if (!isOnline()){
            Toast.makeText(MapActivity.this, "You have no Internet Connection!",
                    Toast.LENGTH_LONG).show();
            if (this.weekDay.equals("noDay")){
                return false;
            }else{
                markerLab = MarkerLab.get(getApplicationContext());
                markerList = markerLab.getMarkers();
                addMarkers();
                return true;
            }
        }
        //Case 1: first time start;
        if (this.weekDay.equals("noDay")){
            Toast.makeText(MapActivity.this, "Downloading Letters, this might took a while",
                    Toast.LENGTH_LONG).show();
            //http://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog
            final DownloadTask downloadTask = new DownloadTask(this);
            downloadTask.execute("http://www.inf.ed.ac.uk/teaching/courses/selp/coursework/" + weekDay + ".kml");
            this.weekDay = weekDay;
            return true;
        }
        //Case 2: Change of Day
        if (!weekDay.equals(this.weekDay)){
            Toast.makeText(MapActivity.this, "Downloading New Letters, this might took a while",
                    Toast.LENGTH_LONG).show();
            SharedPreferences save = getSharedPreferences(PREF_SAVE, 0);
            SharedPreferences.Editor editor = save.edit();
            editor.putBoolean("deleteFlag",true);
            editor.commit();
            //http://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog
            final DownloadTask downloadTask = new DownloadTask(this);
            downloadTask.execute("http://www.inf.ed.ac.uk/teaching/courses/selp/coursework/" + weekDay + ".kml");
            this.weekDay = weekDay;
            collectedToday = 0;
            walkedToday = 0;
            return true;
        }
        //Case 3: Normal Open
        markerLab = MarkerLab.get(getApplicationContext());
        markerList = markerLab.getMarkers();
        addMarkers();
        return false;
    }

    //http://stackoverflow.com/questions/15259860/how-do-i-handle-the-issue-of-having-no-internet-for-my-application
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {return true;}
        return false;
    }

    private void addMarkers(){
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

    //http://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            Log.i("DOWNLOAD","Started");
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            //http://stackoverflow.com/questions/18256521/android-calendar-get-current-day-of-week-as-string
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar calendar = Calendar.getInstance();
            String weekDay = dayFormat.format(calendar.getTime()).toLowerCase();
            Log.i("DOWNLOAD","Date made");
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                //http://stackoverflow.com/questions/10329779/httpresponse-code-302
                Log.i("DOWNLOAD", "EXPECTED:" + sUrl[0]);
                Log.i("DOWNLOAD", "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage());
                if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP
                        || connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
                        || connection.getResponseCode() ==    HttpURLConnection.HTTP_SEE_OTHER){
                    String newURL = connection.getHeaderField("Location");
                    Log.i("DOWNLOAD","FOUND:" + newURL);
                    url = new URL(newURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                }else{
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        return "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage();
                    }
                }
                int fileLength = connection.getContentLength();
                Log.i("DOWNLOAD",String.valueOf(fileLength));
                input = connection.getInputStream();
                output = new FileOutputStream(
                        Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Android/data/com.yujianmeng.selp.grabble/" + weekDay + ".kml");
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
                markerLab = MarkerLab.get(getApplicationContext());
                markerList = markerLab.getMarkers();
            } catch (Exception e) {
                Log.i("ERROR",e.toString());
                return e.toString();
            } finally {
                Log.i("DOWNLOAD","Completed?");
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String ignored){
            //http://stackoverflow.com/questions/13079645/android-how-to-wait-asynctask-to-finish-in-mainthread
            myHandler.sendEmptyMessage(0);
        }
    }

    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:addMarkers();break;
                default:break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermission(){
        //http://stackoverflow.com/questions/30549561/how-to-check-grants-permissions-at-run-time
        int loc = ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            this.requestPermissions(listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),PackageManager.PERMISSION_GRANTED);
        }
    }
}
