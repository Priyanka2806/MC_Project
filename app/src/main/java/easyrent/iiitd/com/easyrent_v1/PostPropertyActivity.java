package easyrent.iiitd.com.easyrent_v1;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PostPropertyActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button mShowMapButton;
    private EditText mLocationViewET;
    private String mCurrentPhotoPath="";
    private ImageView imgCamera;
    //private ImageView imgVideo;
    private ImageView imgGallery;

    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int RESULT_LOAD_IMAGE = 2;
    private Spinner mSpinner_houseType_post;
    private Spinner mSpinner_furnished_post;
    private Spinner mSpinner_tenantPref_post;
    private ImageView proceedButton;
    private RadioGroup radioAvailabilityGroup;
    private RadioButton radioAvailabilityButton;

    //To get details
    private EditText locationText;
    private Spinner houseType;
    private EditText addressText;

    //For date picker
    private int day_from,month_from,year_from;
    private EditText mFromDate_EditText;

    // private Place sendPlace = null;
    private LatLng lat_lng=null;
    private ArrayList<LatLng> ll_List = new  ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);

        mFromDate_EditText = (EditText)findViewById(R.id.from);

        //--------------------------------------------CAMERA---------------------------------------

        imgCamera = (ImageView) findViewById(R.id.camera);
        imgCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {

                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "easyrent.iiitd.com.easyrent_v1.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                    System.out.println("Exiting takePhoto button function");
                }
                galleryAddPic();
            }
        });

        imgGallery = (ImageView) findViewById(R.id.gallery);
        imgGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });


        //-------------------------------------DROPDOWN LISTS------------------------------------//


        //Dropdown List for House Type
        mSpinner_houseType_post = (Spinner) findViewById(R.id.spinner_houseType_post);
        mSpinner_houseType_post.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        //Dropdown List for Furnished Type
        mSpinner_furnished_post = (Spinner) findViewById(R.id.spinner_furnished_post);
        mSpinner_furnished_post.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        //Dropdown List for Tenant Preference
        mSpinner_tenantPref_post = (Spinner) findViewById(R.id.spinner_tenantPref_post);
        mSpinner_tenantPref_post.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        radioAvailabilityGroup = (RadioGroup) findViewById(R.id.radioAvailability);
        int selectedId = radioAvailabilityGroup.getCheckedRadioButtonId();

        // find the radio button by returned id
        radioAvailabilityButton = (RadioButton) findViewById(selectedId);


        mShowMapButton=(Button)findViewById(R.id.showMapBtn);
        mLocationViewET=(EditText)findViewById(R.id.locationView);
        mShowMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ll_List=getHardCodedLatList();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle bundle_latlng = new Bundle();
                //bundle_latlng.putParcelable("LATLNG",lat_lng);
                ll_List.add(lat_lng);
                bundle_latlng.putParcelableArrayList("LL_LIST",ll_List);

                intent.putExtras(bundle_latlng);
                startActivity(intent);
            }
        });

        mLocationViewET.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findPlace(v);
            }
        });

        proceedButton = (ImageView)findViewById(R.id.next);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String latit_post="",longit_post="",point_structure_post="";
                String house = "";
                String location = "";
                String address = "";
                //String availableFrom = "";
                String photoURL = "";
                String dateString_from="";

                houseType = (Spinner)findViewById(R.id.spinner_houseType_post);
                locationText = (EditText)findViewById(R.id.locationView);
                addressText = (EditText)findViewById(R.id.addressView);

                house = houseType.toString();
                location = locationText.getText().toString();
                address = addressText.getText().toString();
                photoURL = mCurrentPhotoPath;



                if(house.equals("") || location.equals("") || address.equals("") || photoURL.equals("")) {
                    Toast.makeText(getApplicationContext(), "Cannot proceed without entering the necessary details!!", Toast.LENGTH_LONG).show();
                }
                else{
                    //Store these details in Database.
                    //Latitude longitude to be stored as point
                    latit_post = String.valueOf(lat_lng.latitude);
                    longit_post = String.valueOf(lat_lng.longitude);

                    point_structure_post = "POINT(" + longit_post + " " + latit_post + ")";


                    if(mFromDate_EditText.equals(""))
                    {

                    }
                    else
                    {
                        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy");
                        Date inputDate = null;
                        try {
                            inputDate = fmt.parse(String.valueOf(day_from) + "-" + String.valueOf(month_from) + "-" + String.valueOf(year_from));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Create the MySQL datetime string
                        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dateString_from = fmt.format(inputDate);
                    }

                    //Toast.makeText(getApplicationContext(), "Details entered to the database.", Toast.LENGTH_LONG).show();

                    try
                    {
                        //Store the values in database
                        //String[] json_string={"store_tenant_details",String.valueOf(uid),rent_locality,dateString,tenant_type,house_type,budget,flatShare_option,latit,longit,point_structure};
                        //new JSONTask(json_string).execute("store_tenant_details");

                    }
                    catch(NullPointerException e)
                    {

                    }
                    Intent intent = new Intent(getApplicationContext(), PostDoneActivity.class);
                    startActivity(intent);
                }
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
            Toast.makeText(getApplicationContext(), "Repairable exception!", Toast.LENGTH_LONG).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getApplicationContext(), "Map service not available!", Toast.LENGTH_LONG).show();
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrieve the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                ((TextView) findViewById(R.id.locationView))
                        .setText(place.getName());
                //  place.getAddress() +"\n" + place.getPhoneNumber()
                LatLng temp_latlng=place.getLatLng();
                lat_lng=temp_latlng;
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mCurrentPhotoPath = cursor.getString(columnIndex);
            cursor.close();

            Toast.makeText(getApplicationContext(), "Selected Image Path -" + mCurrentPhotoPath, Toast.LENGTH_LONG).show();

        }

    }

    private void galleryAddPic() {
        System.out.println("Inside galleryAddPics function");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        System.out.println("Exiting galleryAddPics function");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Toast.makeText(getApplicationContext(), "Selected Image Path -" + mCurrentPhotoPath, Toast.LENGTH_LONG).show();
        return image;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        Log.w("DatePicker","Date ================================================= " + year);
        day_from=day;
        month_from=month;
        year_from=year;
        mFromDate_EditText.setText(" " + day + "/" + month + "/" + year);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dpFragment = new DatePickerFragmentClass();
        dpFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private ArrayList<LatLng> getHardCodedLatList() {

        ArrayList<LatLng> hardCodedLatList= new ArrayList<>();
        hardCodedLatList.add(new LatLng(-31.952854, 115.857342));
        hardCodedLatList.add(new LatLng(-33.87365, 151.20689));
        hardCodedLatList.add(new LatLng(-27.47093, 153.0235));

        return hardCodedLatList;
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
