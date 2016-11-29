package easyrent.iiitd.com.easyrent_v1;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lenovo on 10/21/2016.
 */
public class SearchRes extends Activity {

    //ArrayList<Listing> arr=new ArrayList<Listing>();
   // CustomListAdapter adapter;

  //  ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.search_results);

     /*   setListData();

        Resources res=getResources();
        list=(ListView)findViewById(R.id.list_res);
        adapter=new CustomListAdapter(this,arr,res);
        list.setAdapter(adapter);
*/
        /*CustomListAdapter adapter=new CustomListAdapter(this,items,imgs);
        list=(ListView)findViewById(R.id.list_res);
        list.setAdapter(adapter);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"item clicked",Toast.LENGTH_SHORT).show();
            }
        });
        */

    }

   /* private void setListData() {
        String[] places={"Subhash nagar","Udhyog Vihar","Rama krishna marg","Gurgaon"};
        String[] prices={"2000","3500","2500","5000"};
        String[] rooms={"2","2","1","4"};
        String[] images={"img1","img2","img3","img1"};
        for(int i=0;i<4;i++){
            final ListModel lis=new ListModel();
            lis.setListingPlace(places[i]);
            lis.setListingPrice(prices[i]);
            lis.setNumOfRooms(rooms[i]);
            lis.setImage(images[i]);
            arr.add(lis);
        }

    }*/
}
