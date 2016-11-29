package easyrent.iiitd.com.easyrent_v1;

import java.sql.Blob;
import java.util.UUID;

/**
 * Created by lenovo on 10/21/2016.
 */
public class Listing {

    private UUID id;
    private String listingPlace;
    private int listingPrice;
    private int numOfRooms;
    private String image;
    private int rating;

    public Listing(UUID id,String listingPlace,int listingPrice,int numOfRooms,String image,int rating){
        this.rating=rating;this.numOfRooms=numOfRooms;this.listingPrice=listingPrice;this.listingPlace=listingPlace;this.image=image;
        this.id=id;

    }
    public void setImage(String image) {
        this.image = image;
    }

    public void setListingPlace(String listingPlace) {
        this.listingPlace = listingPlace;
    }

    public void setListingPrice(int listingPrice) {
        this.listingPrice = listingPrice;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public String getImage() {
        return image;
    }

    public String getListingPlace() {
        return listingPlace;
    }

    public int getListingPrice() {
        return listingPrice;
    }

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public int getRating() {return rating;}

    public void setRating(int rating) {this.rating = rating;}

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}
}
