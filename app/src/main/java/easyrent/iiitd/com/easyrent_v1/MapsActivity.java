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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng received_latlng=null;

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

        Bundle bundle = getIntent().getParcelableExtra("BUNDLE");
        received_latlng = bundle.getParcelable("LATLNG");

        if(received_latlng!=null)
        {
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );
            mMap.addMarker(new MarkerOptions().position(received_latlng).title("Your location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(received_latlng));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter your location!", Toast.LENGTH_LONG).show();
        }



    }
}
