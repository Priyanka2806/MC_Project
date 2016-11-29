package easyrent.iiitd.com.easyrent_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SharingActivity extends AppCompatActivity {

    public static List<SharingList> sharingList = new ArrayList<>();
    private RecyclerView recyclerView;
   private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        Toast.makeText(getApplicationContext(), "main sharing activity hoon!", Toast.LENGTH_LONG).show();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MyAdapter(sharingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareListData();
           }
              private void prepareListData() {
                    SharingList item = new SharingList("Person 1" , "blah blah");
                    sharingList.add(item);

                           item = new SharingList("Person 2" , "blah blah");
                    sharingList.add(item);

                           item = new SharingList("Person 3" , "blah blah");
                    sharingList.add(item);
                         item = new SharingList("Person 4" , "blah blah");
                    sharingList.add(item);
                          item = new SharingList("Person 5" , "blah blah");
                    sharingList.add(item);

                            mAdapter.notifyDataSetChanged();
                   }

              }
