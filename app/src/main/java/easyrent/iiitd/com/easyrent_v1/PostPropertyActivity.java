package easyrent.iiitd.com.easyrent_v1;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PostPropertyActivity extends AppCompatActivity {

    private Button mShowMapButton;
    private EditText mLocationViewET;
    private String mCurrentPhotoPath;
    private ImageView imgCamera;
    //private ImageView imgVideo;
    private ImageView imgGallery;

    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int RESULT_LOAD_IMAGE = 2;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private ImageView proceedButton;
    private RadioGroup radioAvailabilityGroup;
    private RadioButton radioAvailabilityButton;

    // private Place sendPlace = null;
    private LatLng lat_lng=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);

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
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        //Dropdown List for Furnished Type
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        //Dropdown List for Tenant Preference
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        radioAvailabilityGroup = (RadioGroup) findViewById(R.id.radioAvailability);
        int selectedId = radioAvailabilityGroup.getCheckedRadioButtonId();

        // find the radio button by returned id
        radioAvailabilityButton = (RadioButton) findViewById(selectedId);


        mShowMapButton=(Button)findViewById(R.id.showMapBtn);
        mLocationViewET=(EditText)findViewById(R.id.locationView);
        mShowMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle bundle_latlng = new Bundle();
                bundle_latlng.putParcelable("LATLNG",lat_lng);
                intent.putExtra("BUNDLE",bundle_latlng);
                //intent.putExtra("call_from","PostPropertyActivity");
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

                Intent intent = new Intent(getApplicationContext(), PostDoneActivity.class);
                startActivity(intent);
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
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Toast.makeText(getApplicationContext(), "Selected Image Path -" + picturePath, Toast.LENGTH_LONG).show();

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
        return image;
    }



}
