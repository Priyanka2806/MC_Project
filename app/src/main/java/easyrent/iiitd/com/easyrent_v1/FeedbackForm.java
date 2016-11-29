package easyrent.iiitd.com.easyrent_v1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;

public class FeedbackForm extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_form);

        Spinner durationSpin=(Spinner)findViewById(R.id.duration_spinner);
        Spinner amenitiesSpin=(Spinner)findViewById(R.id.amenities_spinner);
        Spinner powerSpin=(Spinner)findViewById(R.id.backup_spinner);
        Spinner furnishSpin=(Spinner)findViewById(R.id.furnish_spinner);
        Spinner periodicMSpin=(Spinner)findViewById(R.id.maintenance_spinner);
        Spinner hassleSpin=(Spinner)findViewById(R.id.hassle_spinner);
        Spinner spaceSpin=(Spinner)findViewById(R.id.spacious_spinner);
        Spinner safeSpin=(Spinner)findViewById(R.id.safety_spinner);

        String[] str= getResources().getStringArray(R.array.duration_type);

        HintAdapter adapter= new HintAdapter(this, Arrays.asList(str),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        durationSpin.setAdapter(adapter);
        durationSpin.setSelection(adapter.getPosition("Select"));

        str= getResources().getStringArray(R.array.yesno_type);
        adapter= new HintAdapter(this, Arrays.asList(str),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        amenitiesSpin.setAdapter(adapter);
        amenitiesSpin.setSelection(adapter.getPosition("Select"));
        powerSpin.setAdapter(adapter);
        powerSpin.setSelection(adapter.getPosition("Select"));

        str= getResources().getStringArray(R.array.furnishing_type);
        adapter= new HintAdapter(this, Arrays.asList(str),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        furnishSpin.setAdapter(adapter);
        furnishSpin.setSelection(adapter.getPosition("Select"));

        str= getResources().getStringArray(R.array.maintenance_type);
        adapter= new HintAdapter(this, Arrays.asList(str),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodicMSpin.setAdapter(adapter);
        periodicMSpin.setSelection(adapter.getPosition("Select"));

        str= getResources().getStringArray(R.array.rate_type);
        adapter= new HintAdapter(this, Arrays.asList(str),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hassleSpin.setAdapter(adapter);
        hassleSpin.setSelection(adapter.getPosition("Select"));
        spaceSpin.setAdapter(adapter);
        spaceSpin.setSelection(adapter.getPosition("Select"));
        str= getResources().getStringArray(R.array.safe_type);
        adapter= new HintAdapter(this, Arrays.asList(str),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        safeSpin.setAdapter(adapter);
        safeSpin.setSelection(adapter.getPosition("Select"));


    }


}
