package com.example.mexpense.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.mexpense.model.Trips;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private static Context context;
    private static final String DATABASE_NAME = "TripManagement.db";
    private static final int DATABASE_VERSION = 0;

    private static final String TABLE_NAME = "trips";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TRIPNAME = "tripName";
    private static final String COLUMN_DATEofTRIP = "datetripDate";
    private static final String COLUMN_DESCRIPTION = "tripDestination";

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;


//    public DBManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase dbTrip) {
        createTables(dbTrip);
    }

    private void createTables(SQLiteDatabase dbTrip) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRIPNAME + " TEXT, " +
                COLUMN_DATEofTRIP + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT);";
        dbTrip.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbTrip, int i, int i1) {
        dropAndRecreate(dbTrip);
    }

    private void dropAndRecreate(SQLiteDatabase dbTrip) {
        dbTrip.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(dbTrip);
    }

    public long add(Trips trip) {
        long insertId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIPNAME, trip.getmTripName());
        values.put(COLUMN_DATEofTRIP, trip.getmTripDate());
        values.put(COLUMN_DESCRIPTION, trip.getmTripDescription());

        // Inserting Row
        insertId = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    public List<Trips> getAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        final List<Trips> list = new ArrayList<>();
        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Trips trip = new Trips();
                    trip.setmId(cursor.getInt(0));
                    trip.setmTripName(cursor.getString(1));
                    trip.setmTripDate(cursor.getString(2));
                    trip.getmTripDescription();
                    // Adding object to list
                    list.add(trip);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public long update(Trips trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIPNAME, trip.getmTripName());
        values.put(COLUMN_DATEofTRIP, trip.getmTripDate());
        values.put(COLUMN_DESCRIPTION, trip.getmTripDescription());

        return db.update(TABLE_NAME, values, "_id=?", new String[]{String.valueOf(trip.getmId())});
    }

    public long delete(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
    }

    void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_NAME);
        dropAndRecreate(db);
    }
}