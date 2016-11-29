package easyrent.iiitd.com.easyrent_v1;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListingFragment extends Fragment {    //fragment that displays the list of listings using recycler view

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private Toolbar toolbar;
    private ImageButton newTaskBtn;
    private final int REQ=2;
    private View view;
    private final String SAVED_LAYOUT="saved_layout";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        this.view=view;
        return view;
    }

    private void updateUI() {   //updates the ui with the recent values
        ListingHub th=ListingHub.get(getActivity());
        ArrayList<Listing> listings=th.getListings();

        if(adapter==null){
            adapter=new RecyclerAdapter(listings);
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(); //whenever activity resumes execution it must display the new attributes for all the listings
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView lstPlace;   //defines holder for each item in the recycler view
        TextView lstPrice;
        TextView lstNumOfRooms;
        TextView lstRating;
        ImageView lstImage;
        Listing listing;



        public RecyclerViewHolder(View itemView) {  //initialisation
            super(itemView);
            itemView.setOnClickListener(this);

            lstPrice=(TextView)itemView.findViewById(R.id.listing_price);
            lstPlace=(TextView)itemView.findViewById(R.id.listing_place);
            lstRating=(TextView)itemView.findViewById(R.id.rating);
            lstNumOfRooms=(TextView)itemView.findViewById(R.id.num_rooms_avail);
            lstImage=(ImageView)itemView.findViewById(R.id.listing_icon);

        }

        public void bindTask(Listing t){   //binding the holder components with the values of the task
            listing=t;
            lstPlace.setText(listing.getListingPlace());
            //String m="\u20B9";
            String a="\u20B9"+String.valueOf(listing.getListingPrice());
            lstPrice.setText(a);
            a="\uD83D\uDC99"+String.valueOf(listing.getRating());
            lstRating.setText(a);
            a="\u2022"+" "+listing.getNumOfRooms()+" rooms available";
            lstNumOfRooms.setText(a);
            lstImage.setImageResource(R.drawable.img1);
        }

        @Override
        public void onClick(View view) {

            Log.d("inside listing fragment", "ok");
            Intent i = new Intent(getActivity(), DetailedListing.class);
            startActivity(i);
        }
    }
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

        ArrayList<Listing> listings;


        public RecyclerAdapter(ArrayList<Listing> listings){
            this.listings=listings;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mylist,parent,false);
            RecyclerViewHolder holder=new RecyclerViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {

            Listing t=listings.get(position);
            holder.bindTask(t);
        }

        @Override
        public int getItemCount() {
            return listings.size();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {  //to save the content during screen rotation
        super.onSaveInstanceState(outState);
        Log.d("TASK_LIST", "In onSaveInstance");
        outState.putParcelable(SAVED_LAYOUT,((RecyclerView)view.findViewById(R.id.recycler_view)).getLayoutManager().onSaveInstanceState());

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {  //to restore the content after screen rotation
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null){

            ((RecyclerView)view.findViewById(R.id.recycler_view)).getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(SAVED_LAYOUT));
        }
    }
}
