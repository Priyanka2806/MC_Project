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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//======================PostPropertyActivity calls this Maps activity====================
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LatLng received_latlng=null;

    ArrayList<LatLng> receivedLatArray = new ArrayList<LatLng>();
    ArrayList<LatLng> receivedLatList = new ArrayList<LatLng>();

    private LatLng recievedLocation;
    private String MAPS_TAG="MapsRentActivity";
    private Marker markerForTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker and move the camera
        //LatLng sydney = new LatLng(-34, 151);

        //Intent from PostPropertyActivity

        //------------old code--------------------

//        Bundle bundle = getIntent().getParcelableExtra("BUNDLE");
//        received_latlng = bundle.getParcelable("LATLNG");
//
//        if(received_latlng!=null)
//        {
//            mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );
//            mMap.addMarker(new MarkerOptions().position(received_latlng).title("Your location"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(received_latlng));
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(), "Enter your location!", Toast.LENGTH_LONG).show();
//        }


        receivedLatArray = getIntent().getParcelableArrayListExtra("LL_LIST");
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

            for(int i = 0 ; i < size ; i++ )
            {
                LatLng temp_lList = receivedLatArray.get(i);
                Log.d("MAPS_TAG", String.valueOf(receivedLatArray.get(i)));
                markerForTag=mMap.addMarker(new MarkerOptions().position(temp_lList).title(" Landlord address"));
                markerForTag.setTag(i);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(temp_lList));
            }
//            mMap.addMarker(new MarkerOptions().position(recievedLocation).title("15k"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(recievedLocation));
        }



    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        // Retrieve the data from the marker.
        Integer clickTag = (Integer) marker.getTag();

        //Find clickTag in the database and display the address and the price of the property
        marker.setTitle("----Budget----" + "/n----Address----");

        //Redirect to list view when any marker is clicked

        // Check if a click count was set, then display the click count.
//        if (clickTag != null) {
//            clickTag = clickTag + 1;
//            marker.setTag(clickTag);
//            Toast.makeText(this,
//                    marker.getTitle() +
//                            " has been clicked " + clickTag + " times.",
//                    Toast.LENGTH_SHORT).show();
//        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
