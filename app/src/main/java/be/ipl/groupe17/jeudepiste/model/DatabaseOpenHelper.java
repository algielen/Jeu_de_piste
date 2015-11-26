package be.ipl.groupe17.jeudepiste.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

/**
 * Created by Alexandre on 25-11-15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String ZONES_TABLE_NAME = "zones";
    private static final String ZONES_KEY_PRIMARY_KEY = "zone_id";
    private static final String ZONES_KEY_LATITUDE = "latitude";
    private static final String ZONES_KEY_LONGITUDE = "longitude";
    private static final String ZONES_KEY_RAYON = "rayon";
    private static final String ZONES_TABLE_CREATE =
            "CREATE TABLE " + ZONES_TABLE_NAME + " (" +
                    ZONES_KEY_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ZONES_KEY_LATITUDE + " DOUBLE NOT NULL, "
                    + ZONES_KEY_LONGITUDE + " DOUBLE NOT NULL, "
                    + ZONES_KEY_RAYON + " INTEGER NOT NULL, "
                    + " );";


    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ZONES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS zones");
        this.onCreate(db);
    }

    //source : http://hmkcode.com/android-simple-sqlite-database-tutorial/
    public void addZone(Zone zone){
        //for logging
        Log.d("addZone", zone.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        Location location = zone.getLocation();
        values.put(ZONES_KEY_LATITUDE, location.getLatitude());
        values.put(ZONES_KEY_LONGITUDE, location.getLongitude());
        values.put(ZONES_KEY_RAYON, zone.getRayon());

        // 3. insert
        db.insert(ZONES_TABLE_NAME,
                null,
                values);

        // 4. close
        db.close();
    }
}
