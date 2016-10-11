package com.yujianmeng.selp.grabble;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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

        //Camera setting
        CameraPosition testAngle = new CameraPosition.Builder()
                .target(pointsamp1)
                .zoom(17)
                .bearing(0)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(testAngle));

        //mMap.addMarker(new MarkerOptions().position(george).title("TESTPOINT"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(george));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(george,18));
    }
}
