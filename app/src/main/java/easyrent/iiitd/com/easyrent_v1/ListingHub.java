package easyrent.iiitd.com.easyrent_v1;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class ListingHub  { //is a collection of tasks that are displayed to user and this collection accessed by all th activities that either displays or gets the details modified by user

    private static ListingHub listingHub;

    private ArrayList<Listing> listings;

    public ArrayList<Listing> getListings(){
        return listings;
    }

    public Listing getListing(UUID id){
        for(Listing t:listings){
            if(t.getId().equals(id))
                return t;
        }
        return null;
    }

    public static ListingHub get(Context context){
        if(listingHub==null){
            listingHub=new ListingHub(context);
        }
        return listingHub;
    }

    private ListingHub(Context context){
        listings=new ArrayList<Listing>();
        final DatabaseHelper db=new DatabaseHelper(context);


        Listing l1=new Listing(UUID.randomUUID(),"gurgaon",10000,2,"img1.jpg",5);
        Listing l2=new Listing(UUID.randomUUID(),"delhi",5000,3,"img2.jpg",5);
        Listing l3=new Listing(UUID.randomUUID(),"noida",2500,5,"img3.jpg",5);
        Listing l4=new Listing(UUID.randomUUID(),"naraina",8000,2,"img1.jpg",5);
        Listing l5=new Listing(UUID.randomUUID(),"punjabi bagh",7000,4,"img1.jpg",5);
        Listing l6=new Listing(UUID.randomUUID(),"gurgaon",6000,2,"img2.jpg",5);
        Listing l7=new Listing(UUID.randomUUID(),"govindpuri",9000,3,"img3.jpg",5);
        listings.add(l1);listings.add(l2);listings.add(l3);listings.add(l4);listings.add(l5);listings.add(l6);listings.add(l7);
        /*Cursor c=db.retrieve();
        c.moveToFirst();
        if(c.getCount()>0){ //indicates db is non empty

            do{

                String a=c.getString(c.getColumnIndex(DatabaseHelper.TableDef.ID));
                String b=c.getString(c.getColumnIndex(DatabaseHelper.TableDef.PLACE));
                int c1=c.getInt(c.getColumnIndex(DatabaseHelper.TableDef.PRICE));
                int d=c.getInt(c.getColumnIndex(DatabaseHelper.TableDef.NUM_ROOMS));
                String e=c.getString(c.getColumnIndex(DatabaseHelper.TableDef.IMAGE));
                int f=c.getInt(c.getColumnIndex(DatabaseHelper.TableDef.RATING));

                Listing t=new Listing();
                UUID id=UUID.fromString(a);
                t.setId(id);
                t.setListingPlace(b);
                t.setListingPrice(c1);
                t.setImage(e);
                t.setNumOfRooms(d);
                t.setRating(f);
                listings.add(t);
            }while(c.moveToNext());
        }*/

    }
}
