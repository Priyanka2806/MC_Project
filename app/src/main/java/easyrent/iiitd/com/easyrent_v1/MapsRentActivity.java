package easyrent.iiitd.com.easyrent_v1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//======================LookingForHouseActivity calls this MapsRentActivity====================
public class MapsRentActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> receivedLatArray = new ArrayList<LatLng>();
    ArrayList<LatLng> receivedLatList = new ArrayList<LatLng>();

    private LatLng recievedLocation;
    private String MAPS_TAG="MapsRentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_rent);
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
        //recievedLocation = getIntent().getParcelableExtra("LATLNG");
        receivedLatArray = getIntent().getParcelableArrayListExtra("LOCATION_LIST");
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 8.0f ) );

        if(receivedLatArray.isEmpty())
        {
            Log.d("MAPS_TAG","Empty location");
            Toast.makeText(getApplicationContext(), "Empty location list :/", Toast.LENGTH_LONG).show();

        }
        else
        {
            //receivedLatArray.add(recievedLocation);
            int size=receivedLatArray.size();

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(receivedLatArray.get(size-1),14.0f));
            for(int i = 0 ; i < size ; i++ )
            {
                LatLng temp_ll = receivedLatArray.get(i);
                Log.d("MAPS_TAG", String.valueOf(receivedLatArray.get(i)));
                mMap.addMarker(new MarkerOptions().position(temp_ll).title("Tag + Budget + Address"));

            }
//            mMap.addMarker(new MarkerOptions().position(recievedLocation).title("15k"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(recievedLocation));
        }



    }
}
