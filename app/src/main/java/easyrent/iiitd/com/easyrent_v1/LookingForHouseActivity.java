package easyrent.iiitd.com.easyrent_v1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LookingForHouseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button mMapViewButton;
    private LatLng lat_lng_lfh=null;
    private ArrayList<LatLng> locationList = new  ArrayList<>();
    private TextView mDatePickerTextView;

    private EditText mSearchByLocalityEditText;
    private Button mListPropertiedBtn;

    private EditText mMoveInDateEditText;
    private RadioGroup mTenantTypeRadioGroup;
    private RadioButton mTenantTypeRadioBtn;
    private Spinner mHouseTypeSpinner, mBudgetSpinner;
    private CheckBox mFlatShareCheckBox;

    private int day_moveIn,month_moveIn,year_moveIn;
    private String dateString;

    int uid=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for_house);

        mMapViewButton = (Button) findViewById(R.id.mapViewBtn);
        mSearchByLocalityEditText = (EditText) findViewById(R.id.et_lfh_search);
        mMoveInDateEditText=((EditText) findViewById(R.id.editText_moveInDate));

        //-------------------------------------DROPDOWN LISTS------------------------------------//


        //Dropdown List for House Type
        mHouseTypeSpinner = (Spinner) findViewById(R.id.spinner_houseType);
        mHouseTypeSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        //Dropdown List for Budget
        mBudgetSpinner = (Spinner) findViewById(R.id.spinner_budget);
        mBudgetSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        //checkbox for flat share
        mFlatShareCheckBox= (CheckBox) findViewById(R.id.checkBox_flatShare);

        //Date picker text view
        mDatePickerTextView = (TextView) findViewById(R.id.editText_moveInDate);


        mListPropertiedBtn = (Button)findViewById((R.id.button_listProperties));

        mMapViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //retrieve list of lat longs from db and display on map
                locationList=getHardCodedLatList();
                Intent intent = new Intent(getApplicationContext(), MapsRentActivity.class);
                Bundle bundle_latlng = new Bundle();
                //bundle_latlng.putParcelable("LATLNG",lat_lng);
                locationList.add(lat_lng_lfh);
                bundle_latlng.putParcelableArrayList("LOCATION_LIST",locationList);

                intent.putExtras(bundle_latlng);
                startActivity(intent);




//                Intent intent = new Intent(getApplicationContext(), MapsRentActivity.class);
//                intent.putParcelableArrayListExtra("LAT_LIST", sentLatArray);
//                startActivity(intent);
                //startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        mSearchByLocalityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace(view);
            }
        });


        mListPropertiedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------Code for DB-------------------------------
                String latit="",longit="",point_structure="";
                String tenant_type="";
                String house_type="";
                String budget="";
                String flatShare_option="false";

                String rent_locality = mSearchByLocalityEditText.getText().toString();

                if(rent_locality.equals("") )
                {
                    Toast.makeText(getApplicationContext(), "Enter the necessary details!!", Toast.LENGTH_LONG).show();
                }
                else {
                    //Latitude longitude to be stored as point
                    latit = String.valueOf(lat_lng_lfh.latitude);
                    longit = String.valueOf(lat_lng_lfh.longitude);

                    point_structure = "POINT(" + longit + " " + latit + ")";
                }
                // Parse the input date
                if(mMoveInDateEditText.equals(""))
                {

                }
                else
                {
                    SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy");
                    Date inputDate = null;
                    try {
                        inputDate = fmt.parse(String.valueOf(day_moveIn) + "-" + String.valueOf(month_moveIn) + "-" + String.valueOf(year_moveIn));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Create the MySQL datetime string
                    fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    dateString = fmt.format(inputDate);
                }
                try
                {
                    //Radio buttons
                    mTenantTypeRadioGroup = (RadioGroup) findViewById(R.id.radioGroupTenantType);
                    int selectedId = mTenantTypeRadioGroup.getCheckedRadioButtonId();
                    // find the radio button by returned id
                    mTenantTypeRadioBtn = (RadioButton) findViewById(selectedId);
                    tenant_type=mTenantTypeRadioBtn.getText().toString();

                    house_type=mHouseTypeSpinner.getSelectedItem().toString();
                    budget=mBudgetSpinner.getSelectedItem().toString();

                    if(mFlatShareCheckBox.isChecked())
                    {
                        flatShare_option="true";
                    }

                    //Store the values in database
                    String[] json_string={"store_tenant_details",String.valueOf(uid),rent_locality,dateString,tenant_type,house_type,budget,flatShare_option,latit,longit,point_structure};
                    new JSONTask(json_string).execute("store_tenant_details");


//                    Intent listPropertiesIntent = new Intent(getApplicationContext(),ListingActivityPro.class);
//                    startActivity(listPropertiesIntent);
                }

                catch(NullPointerException e)
                {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields before proceeding.", Toast.LENGTH_LONG).show();
                }
                //----------------------------------------Code for DB-------------------------------
            }
        });

    }

    //https://www.studytutorial.in/android-google-places-api-tutorial-to-search-google-places
    public void findPlace(View view) {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(getApplicationContext(), "Repairable exception! Update your play-services!", Toast.LENGTH_LONG).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getApplicationContext(), "Map service not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        Log.w("DatePicker","Date ================================================= " + year);
        day_moveIn=day;
        month_moveIn=month;
        year_moveIn=year;
        mMoveInDateEditText.setText(" " + day + "/" + month + "/" + year);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrieve the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                ((TextView) findViewById(R.id.et_lfh_search))
                        .setText(place.getName());
                //  place.getAddress() +"\n" + place.getPhoneNumber()
                LatLng temp_latlng = place.getLatLng();
                lat_lng_lfh = temp_latlng;
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    private ArrayList<LatLng> getHardCodedLatList() {

        ArrayList<LatLng> hardCodedLatList= new ArrayList<>();
        hardCodedLatList.add(new LatLng(28.7041, 77.1025));
        hardCodedLatList.add(new LatLng(10.8505, 76.2711));
        hardCodedLatList.add(new LatLng(15.2993, 74.1240));

        return hardCodedLatList;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dpFragment = new DatePickerFragmentClass();
        dpFragment.show(getSupportFragmentManager(), "datePicker");
    }


    //----------------------------------------------DB Class below----------------------------------------------------------

    public class JSONTask extends AsyncTask<String,String,String> {

        String[] string;
        public JSONTask(String[] string)
        {
            this.string=string;
        }

        @Override
        protected String doInBackground(String... strings) {    //loads the content of web page whose url is given

            String reg_url="jdbc:mysql://192.168.0.105:3307/test";
            String uname="test123";
            String pass="test";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn= DriverManager.getConnection(reg_url,uname,pass);

                Statement stmt=conn.createStatement();
                //uid,rent_locality,dateString,tenant_type,house_type,budget,flatShare_option,latit,longit,point_structure
                //1=uid,2=rent_locality, 3=dateString,4=tenant_type,5=house_type,6=budget,7=flatShare_option,8=latit,9=longit,10=point_structure
                String str="Insert into tenant_info(uid,address,tenant_type,house_type,move_in_date,flatshare,budget,latitude,longitude,lat_lng) values('" + string[1] + "','" + string[2] + "','" + string[4] + string[5] + "','" + string[3] + "','" + string[7] + string[6] + "','" + string[8] + string[9] + "','" + "GeoFromText(" +string[10] +")" + "');";
                int res=stmt.executeUpdate(str);
                Log.d("value of res: ",String.valueOf(res));
                if(res>0)
                    return "successful insertion";
                else
                    return "no";

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }



            return null;


        }



        @Override
        protected void onPostExecute(String s) {    //the received content is then displayed in the textView
            super.onPostExecute(s);
            if(s==null)
                Toast.makeText(getApplicationContext(),"nothing goin",Toast.LENGTH_LONG).show();
            else
            if(s.equalsIgnoreCase("0"))
                Toast.makeText(getApplicationContext(),"wrong credentials entered",Toast.LENGTH_LONG).show();
            else
            if(s.equalsIgnoreCase("1")) {//launch
                Intent listPropertiesIntent = new Intent(getApplicationContext(),ListingActivityPro.class);
                startActivity(listPropertiesIntent);
            }
            else
            {
                Log.d("inside opPost", s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }

}






