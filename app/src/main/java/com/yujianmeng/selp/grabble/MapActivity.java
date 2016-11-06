package com.yujianmeng.selp.grabble;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mButtonLeftTurn;
    private Button mButtonRightTurn;
    private Button mButtonEagle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        //TODO implement map
        //Dummy code, add a randonm point in GS and zoom to it

        //Dummy markers - 4 corners
        LatLng george1 = new LatLng(55.946,-3.184);
        MarkerOptions marker1 = new MarkerOptions()
                .position(george1)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point1");
        mMap.addMarker(marker1);

        LatLng george2 = new LatLng(55.942,-3.192);
        MarkerOptions marker2 = new MarkerOptions()
                .position(george2)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point2");
        mMap.addMarker(marker2);

        LatLng george3 = new LatLng(55.946,-3.192);
        MarkerOptions marker3 = new MarkerOptions()
                .position(george3)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point3");
        mMap.addMarker(marker3);

        LatLng george4 = new LatLng(55.942,-3.184);
        MarkerOptions marker4 = new MarkerOptions()
                .position(george4)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point4");
        mMap.addMarker(marker4);

        //Sqaure markers
        LatLng pointsamp1 = new LatLng(55.943,-3.189);
        MarkerOptions markersamp1 = new MarkerOptions()
                .position(pointsamp1)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point5");
        mMap.addMarker(markersamp1);

        LatLng pointsamp2 = new LatLng(55.94352,-3.18944);
        MarkerOptions markersamp2 = new MarkerOptions()
                .position(pointsamp2)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point6");
        mMap.addMarker(markersamp2);

        LatLng pointsamp3 = new LatLng(55.94378,-3.18912);
        MarkerOptions markersamp3 = new MarkerOptions()
                .position(pointsamp3)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickable_sample))
                .title("Point7");
        mMap.addMarker(markersamp3);

        //Initialize buttons on the map
        mButtonLeftTurn = (Button) findViewById(R.id.button_left_turn);
        mButtonRightTurn = (Button) findViewById(R.id.button_right_turn);
        mButtonEagle = (Button) findViewById(R.id.button_eagle);

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
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder(temp).zoom(temp.zoom - 2).build()
                ));
                disableControl();//Disable the control temporarily to prevent cheat.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        CameraPosition temp = mMap.getCameraPosition();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder(temp).zoom(temp.zoom + 2).build()
                        ),
                                new GoogleMap.CancelableCallback() {
                                    @Override
                                    public void onFinish() {enableControl();}//Return controls
                                    @Override
                                    public void onCancel() {}
                                });
                    }
                },5000);
            }
        });
        // --- finish initializing, starting app ---
        disableControl();//Prevent Cheat, re-enable during camera initialization

        CameraPosition testAngle = new CameraPosition.Builder()
                .target(pointsamp1)
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
        //TODO finish this after adding other button
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
}
