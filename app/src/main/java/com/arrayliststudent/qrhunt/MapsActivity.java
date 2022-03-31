package com.arrayliststudent.qrhunt;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "Map is ready!");
        mMap = googleMap;

        if (locationPermissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    public GoogleMap mMap;
    public float[][] codeList;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private LatLng deviceLocation;

    private boolean locationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();

        getLocationPermission();
        initMap();
    }

    /**
     * initialize map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    /**
     * ask for location permission
     */
    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * purse permission status, initialize map
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    locationPermissionGranted = true;
                    //initMap();
                }
            }
        }
    }


    /**
     * if location permission is granted, get device current location and add marker to map
     */
    private void getDeviceLocation(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if(locationPermissionGranted){
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            if(currentLocation != null){
                                deviceLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                                addMarker(deviceLocation,15f);
                            }
                            else {
                                getDeviceLocation();
                            }
                        }
                    }
                });
            }else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No Permission",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        }catch (SecurityException securityException){
            Log.d(TAG,"getDeviceLocation: SecurityException: " + securityException.getMessage());
        }
    }

    /**
     * Move Camera to device current location, add all markers to the map
     * @param latLng
     * @param zoom
     */
    private void addMarker(LatLng latLng, float zoom){
        if(locationPermissionGranted){
            Log.d(TAG,"Current location is latitude: " + latLng.latitude + ", longitude: " + latLng.longitude);
            //mMap.addMarker(new MarkerOptions().position(deviceLocation).title("Marker"));
            /*add all code markers to the map at once*/
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = db.collection("ScannableCodes");
            collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                    assert queryDocumentSnapshots != null;
                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        List<Double> codeLocation;
                        Map<String,Object> code = doc.getData();
                        for(Map.Entry<String, Object> pair : code.entrySet()) {
                            String key = pair.getKey();
                            if (pair.getKey().equals("Location")) {
                                codeLocation = (List<Double>) pair.getValue();
                                mMap.addMarker(new MarkerOptions().position(new LatLng(codeLocation.get(0),codeLocation.get(1))));
                                Log.d(TAG,"*******Adding code marker to: latitude: " + codeLocation.get(0) + ", longitude: " + codeLocation.get(1));
                            }
                        }
                    }
                }
            });

            /*ScannableCode code = firebaseData.getCode("1ef4e838a9aa1a80dcc2a3af4fd57190f8a91c3bf373c85142f2941687ebf127");
            mMap.addMarker(new MarkerOptions().position(new LatLng(code.getLocation()[0],code.getLocation()[1])).title("Marker"));
            Log.d(TAG,"*******Adding code marker to: latitude: " + code.getLocation()[0] + ", longitude: " + code.getLocation()[1]);*/
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deviceLocation,15f));
        }

    }
}
