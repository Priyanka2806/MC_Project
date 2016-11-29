package easyrent.iiitd.com.easyrent_v1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo on 11/4/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    final String str="database helper";
    public static class TableDef{   //provides names for the columns used
        static final String TABLE_NAME="Listings";
        static final String ID="id";
        static final String PLACE="place";
        static final String PRICE="price";
        static final String NUM_ROOMS="rooms";
        static final String IMAGE="image";
        static final String RATING="rating";


    }
    static final String TEXT_TYPE="text",INT_TYPE="integer";

    static final String CREATE_SQL="CREATE TABLE "+TableDef.TABLE_NAME+" ("
            +TableDef.ID+" "+TEXT_TYPE+" PRIMARY KEY,"
            +TableDef.PLACE+" "+TEXT_TYPE+","
            +TableDef.PRICE+" "+INT_TYPE+","
            +TableDef.NUM_ROOMS+" "+INT_TYPE+","
            +TableDef.IMAGE+" "+TEXT_TYPE+","+
            TableDef.RATING+" "+INT_TYPE+");";

    static final String DELETE_DB="DROP TABLE IF EXISTS "+TableDef.TABLE_NAME+";";
    static final int VERSION=1;

    public DatabaseHelper(Context context) {
        super(context, TableDef.TABLE_NAME, null, VERSION);  //creates db

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DELETE_DB);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String id,String place,int price,int numOfRooms, String image,int rating){  //inserts new record in the db
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableDef.ID,id);
        cv.put(TableDef.PLACE,place);
        cv.put(TableDef.PRICE,price);
        cv.put(TableDef.NUM_ROOMS,numOfRooms);
        cv.put(TableDef.IMAGE,image);
        cv.put(TableDef.RATING,rating);
        long r=db.insert(TableDef.TABLE_NAME,null,cv);
        if (r==-1)
            return false;
        else
            return true;
    }

    public boolean update(String id,String place,int price,int numOfRooms, String image,int rating){  //updates the record in the  db
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableDef.PLACE,place);
        String selection=TableDef.ID+"=?";
        String[] selection_arg={id};
        long r1=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);

        cv=new ContentValues();
        cv.put(TableDef.PRICE,price);
        long r2=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);

        cv=new ContentValues();
        cv.put(TableDef.NUM_ROOMS,numOfRooms);
        long r3=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);

        cv=new ContentValues();
        cv.put(TableDef.IMAGE,image);
        long r4=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);

        cv=new ContentValues();
        cv.put(TableDef.RATING,rating);
        long r5=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);


        if(r1>0&&r2>0&&r3>0&&r4>0&&r5>0)
            return true;
        else
            return false;
    }

    public Cursor retrieve(){               //retrieves all the records from the db
        SQLiteDatabase db=this.getReadableDatabase();
        String[] projection={TableDef.ID,TableDef.PLACE,TableDef.PRICE,TableDef.NUM_ROOMS,TableDef.IMAGE,TableDef.RATING};
        Cursor c=db.query(TableDef.TABLE_NAME,projection,null,null,null,null,TableDef.ID);
        return c;
    }

    public Cursor retrieveRec(String id){       //retrieves record with given id from db
        Cursor c=null;
        SQLiteDatabase db=this.getReadableDatabase();
        String[] projection={TableDef.ID,TableDef.PLACE,TableDef.PRICE,TableDef.NUM_ROOMS,TableDef.IMAGE,TableDef.RATING};
        Log.d(str, "After projection statement");
        String selection=TableDef.ID+"= ? ";
        String[] selection_arg={id};
        c=db.query(TableDef.TABLE_NAME,projection,selection,selection_arg,null,null,null,null);
        Log.d(str, "After query statement");
        return c;
    }

    public boolean delete(String id){           //deletes record with given id from db
        SQLiteDatabase db=this.getReadableDatabase();
        String selection=TableDef.ID+"= ? ";
        String[] selection_arg={id};
        long r1=db.delete(TableDef.TABLE_NAME, selection, selection_arg);
        if(r1>0)
            return true;
        else
            return  false;
    }


}
