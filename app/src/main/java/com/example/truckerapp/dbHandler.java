package com.example.truckerapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "fueldb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "fueldata";
    private static final String ID_COL = "id";
    private static final String PRICEPERLTR_COL = "priceperltr";
    private static final String LITRE_COL = "litre";
    private static final String KM_COL = "km";
    private static final String PRICE_COL = "price";
    private static final String AVGFUEL_COL = "avgfuel";
    private static final String AVGECO_COL = "avgeco";

    // creating a constructor for our database handler.
    public dbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase mydb) {
        // creating an sqlite query and setting  column names along with their data types
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRICEPERLTR_COL + " NUMBER," + LITRE_COL + " NUMBER," + KM_COL  + " NUMBER," + PRICE_COL + " NUMBER," +AVGFUEL_COL+"NUMBER," +AVGECO_COL+" NUMBER )";

        mydb.execSQL(query);  // calling a exec sql method to execute above sql query
    }

    // this method is use to add new course to our sqlite database.
    public void addNewData(String fuelrsperltr, String fuellitres, String fueldkm, String fueldprice) {

        // creating a variable for our sqlite database and calling writable method as we are writing data in our database.
        SQLiteDatabase mydb = this.getWritableDatabase();

        // creating a variable for content values.
        ContentValues values = new ContentValues();

        //  passing all values along with its key and value pair.
        values.put(PRICEPERLTR_COL, fuelrsperltr);
        values.put(LITRE_COL, fuellitres);
        values.put( KM_COL, fueldkm);
        values.put(PRICE_COL, fueldprice);

        // after adding all values we are passing content values to our table.
        mydb.insert(TABLE_NAME, null, values);
        mydb.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        mydb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(mydb);
    }
}