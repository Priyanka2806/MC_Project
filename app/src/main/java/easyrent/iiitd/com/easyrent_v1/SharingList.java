package easyrent.iiitd.com.easyrent_v1;

/**
 * Created by riya on 27/11/16.
 */
public class SharingList {



    private static int id;
    private String name, details;

    public SharingList() {
    }

    public SharingList(String name,  String details) {
        this.id = ++this.id;
        this.name = name;
        this.details = details;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }



}