package com.example.mexpense.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.mexpense.model.Expenses;
import com.example.mexpense.model.Trips;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private static Context context;
    private static final String DATABASE_NAME = "MExpense.db";
    private static final int DATABASE_VERSION = 8;

    private static final String TABLE_TRIP_NAME = "trips";
    private static final String COLUMN_TRIPID = "tripId";
    private static final String COLUMN_TRIPNAME = "tripName";
    private static final String COLUMN_DATEofTRIP = "tripDate";
    private static final String COLUMN_DESTINATION = "tripDestination";
    private static final String COLUMN_NOTorRISK = "tripNotOrRisk";
    private static final String COLUMN_DESCRIPTION = "tripDescription";

    private static final String TABLE_EXPENSE_NAME = "expenses";
    private static final String COLUMN_EXPENSEID = "expenseId";
    private static final String COLUMN_EXPENSETYPE = "expenseType";
    private static final String COLUMN_AMOUNT = "expenseAmount";
    private static final String COLUM_DATEofEXPENSE = "expenseDate";
    private static final String COLUMN_COMMENT = "expenseComment";
    private static final String COLUMN_EXPENSETRIPID = "tripId";


    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private void createTable(SQLiteDatabase sqliteDb) {
        String tripQuery = "CREATE TABLE " + TABLE_TRIP_NAME +
                " (" + COLUMN_TRIPID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRIPNAME + " TEXT, " +
                COLUMN_DATEofTRIP + " TEXT, " +
                COLUMN_DESTINATION + " TEXT, " +
                COLUMN_NOTorRISK + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT);";

        sqliteDb.execSQL(tripQuery);

        String expenseQuery = "CREATE TABLE " + TABLE_EXPENSE_NAME +
                " (" + COLUMN_EXPENSEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXPENSETYPE + " TEXT, " +
                COLUMN_AMOUNT + " INTEGER, " +
                COLUM_DATEofEXPENSE + " TEXT, " +
                COLUMN_COMMENT + " TEXT, " +
                COLUMN_EXPENSETRIPID + " INTEGER, " + " FOREIGN KEY (" + COLUMN_EXPENSETRIPID + ") REFERENCES " + TABLE_TRIP_NAME + "(" + COLUMN_TRIPID + "));";

        sqliteDb.execSQL(expenseQuery);
    }


    @Override
    public void onCreate(SQLiteDatabase sqliteDb) {
        createTable(sqliteDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbTrip, int i, int i1) {
        dropAndRecreate(dbTrip);
    }

    private void dropAndRecreate(SQLiteDatabase sqliteDb) {
        sqliteDb.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_NAME);
        sqliteDb.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP_NAME);
        onCreate(sqliteDb);
    }

    public long addTrip(Trips trip) {
        long insertId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIPNAME, trip.getmTripName());
        values.put(COLUMN_DATEofTRIP, trip.getmTripDate());
        values.put(COLUMN_DESTINATION, trip.getmTripDestination());
        values.put(COLUMN_NOTorRISK, trip.getmTripRiskAssessment());
        values.put(COLUMN_DESCRIPTION, trip.getmTripDescription());

        // Inserting Row
        insertId = db.insert(TABLE_TRIP_NAME, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    public long addExpense(Expenses expense) {
        long insertId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSETYPE, expense.getmExpenseType());
        values.put(COLUM_DATEofEXPENSE, expense.getmExDate());
        values.put(COLUMN_AMOUNT, expense.getmExAmount());
        values.put(COLUMN_COMMENT, expense.getmExComment());
        values.put(COLUMN_EXPENSETRIPID, expense.getmExTripId());

        // Inserting Row
        insertId = db.insert(TABLE_EXPENSE_NAME, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    public List<Trips> getAllTrip() {
        final String query = "SELECT * FROM " + TABLE_TRIP_NAME;
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
                    trip.setmTripDestination(cursor.getString(3));
                    trip.setmTripRiskAssessment(cursor.getString(4));
                    trip.setmTripDescription(cursor.getString(5));
                    // Adding object to list
                    list.add(trip);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public List<Trips> getTripByExpense(int tripId) {
        final String query = "SELECT * FROM " + TABLE_TRIP_NAME + " WHERE " + COLUMN_TRIPID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        final List<Trips> list = new ArrayList<>();
        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(tripId)});
            if (cursor.moveToFirst()) {
                do {
                    Trips trip = new Trips();
                    trip.setmId(cursor.getInt(0));
                    trip.setmTripName(cursor.getString(1));
                    trip.setmTripDate(cursor.getString(2));
                    trip.setmTripDestination(cursor.getString(3));
                    trip.setmTripRiskAssessment(cursor.getString(4));
                    trip.setmTripDescription(cursor.getString(5));
                    // Adding object to list
                    list.add(trip);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    //get all expense by trip id
    public List<Expenses> getAllExpense(int tripId) {
        SQLiteDatabase db = this.getReadableDatabase();
        final String query = "SELECT * FROM "+ TABLE_EXPENSE_NAME +" WHERE " + COLUMN_EXPENSETRIPID + " = ?";
        final List<Expenses> list = new ArrayList<>();
        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(tripId)});
            if (cursor.moveToFirst()) {
                do {
                    Expenses expense = new Expenses();
                    expense.setmExId(cursor.getInt(0));
                    expense.setmExpenseType(cursor.getString(1));
                    expense.setmExAmount(Integer.parseInt(cursor.getString(2)));
                    expense.setmExDate(cursor.getString(3));
                    expense.setmExComment(cursor.getString(4));
                    expense.setmExTripId(Integer.parseInt(cursor.getString(5)));

                    // Adding object to list
                    list.add(expense);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public long updateTrip(Trips trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIPNAME, trip.getmTripName());
        values.put(COLUMN_DATEofTRIP, trip.getmTripDate());
        values.put(COLUMN_DESTINATION, trip.getmTripDestination());
        values.put(COLUMN_NOTorRISK, trip.getmTripRiskAssessment());
        values.put(COLUMN_DESCRIPTION, trip.getmTripDescription());

        return db.update(TABLE_TRIP_NAME, values, "tripId=?", new String[]{String.valueOf(trip.getmId())});
    }

    public long updateExpense(Expenses expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSEID, expense.getmExId());
        values.put(COLUMN_EXPENSETYPE, expense.getmExpenseType());
        values.put(COLUMN_AMOUNT, expense.getmExAmount());
        values.put(COLUM_DATEofEXPENSE, expense.getmExDate());
        values.put(COLUMN_COMMENT, expense.getmExComment());

        return db.update(TABLE_EXPENSE_NAME, values, "expenseId=?", new String[]{String.valueOf(expense.getmExId())});
    }

    public long deleteTrip(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRIP_NAME, "tripId=?", new String[]{row_id});
    }

    public long deleteExpense(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXPENSE_NAME, "expenseId=?", new String[]{row_id});
    }

    public void deleteAllTrip() {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_NAME);
        dropAndRecreate(db);
    }
}