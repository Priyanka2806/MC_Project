package easyrent.iiitd.com.easyrent_v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 10/21/2016.
 */
public class SearchProp extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_property);
        List<String> tenant_type=new ArrayList<String>();
        tenant_type.add("only boy");tenant_type.add("only girl");tenant_type.add("any");tenant_type.add("Select tenant type");

        //setting adapter to the spinner
        Spinner tenant_spin=(Spinner)findViewById(R.id.tenant_spinner);
        HintAdapter adapter=new HintAdapter(this,tenant_type,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tenant_spin.setAdapter(adapter);
        tenant_spin.setSelection(adapter.getCount());


        List<String> list1=new ArrayList<String>();
        list1.add("Bed");list1.add("Room");list1.add("House");list1.add("Select Booking type");

        //setting adapter to the spinner
        Spinner booking_type=(Spinner)findViewById(R.id.booking_kind_spinner);
        HintAdapter adapter1=new HintAdapter(this,list1,android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        booking_type.setAdapter(adapter1);
        booking_type.setSelection(adapter1.getCount());

        List<String> list2=new ArrayList<String>();
        list2.add("2000-4000");list2.add("4000-8000");list2.add(">8000");list2.add("Select budget");

        //setting adapter to the spinner
        Spinner budget_spin=(Spinner)findViewById(R.id.budget_spinner);
        HintAdapter adapter2=new HintAdapter(this,list2,android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budget_spin.setAdapter(adapter2);
        budget_spin.setSelection(adapter2.getCount());

        Button search=(Button)findViewById(R.id.search_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ListingActivityPro.class);
                startActivity(i);
            }
        });
    }
}
