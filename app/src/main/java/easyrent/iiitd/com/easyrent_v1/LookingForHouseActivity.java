package easyrent.iiitd.com.easyrent_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LookingForHouseActivity extends AppCompatActivity {
    private Button mMapViewButton;
    static ArrayList<LatLng> sentLatArray = new ArrayList<>();
    //Hardcoded lat long values for places for demo--------------------------------------------
    LatLng lajpat = new LatLng(28.5677, 77.2433);
    LatLng gurgaon = new LatLng(28.4595, 77.0266);
    LatLng ludhiana = new LatLng(30.9010, 75.8573);

    //--------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for_house);
        mMapViewButton = (Button) findViewById(R.id.mapViewBtn);
        //Hardcoded places added to array list for demo----------------------------------------
        sentLatArray.add(lajpat);
        sentLatArray.add(gurgaon);
        sentLatArray.add(ludhiana);
        //----------------------------------------------------------------


        mMapViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsRentActivity.class);
                intent.putParcelableArrayListExtra("LAT_LIST", sentLatArray);
                startActivity(intent);
                //startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }
}
