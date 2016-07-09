package com.example.rahul.timetable;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CourseDbAdapter{

    public static final String KEY_ROWID = "_id";
    public static final String KEY_CODE = "code";
    public static final String KEY_NAME = "name";
    public static final String KEY_ROOM = "room";
    public static final String KEY_TIME = "time";

    private static final String TAG = "CourseDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "Bitsh";
    private static final String SQLITE_TABLE = "Courses";
    private static final int DATABASE_VERSION = 5;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_CODE + "," +
                    KEY_NAME + "," +
                    KEY_ROOM + "," +
                    KEY_TIME + "," +
                    " UNIQUE (" + KEY_CODE +"));";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public CourseDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public CourseDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createCourse(String code, String name,
                               String room, String time) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CODE, code);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_ROOM, room);
        initialValues.put(KEY_TIME, time);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllCourses() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchCoursesByCode(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_CODE, KEY_NAME, KEY_ROOM, KEY_TIME},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_CODE, KEY_NAME, KEY_ROOM, KEY_TIME},
                    KEY_CODE + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCourses() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_CODE, KEY_NAME, KEY_ROOM, KEY_TIME},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCourses() {

        createCourse("BIO F110 P1", "BIOLOGY LABORATORY",  "A122", "T 6 7");
        createCourse("BIO F110 P2", "BIOLOGY LABORATORY",  "A122", "T 8 9");
        createCourse("BIO F110 P3", "BIOLOGY LABORATORY", "A122", "W 3 4");
        createCourse("BIO F110 P4", "BIOLOGY LABORATORY",  "A122", "W 8 9");
        createCourse("BIO F110 P5", "BIOLOGY LABORATORY",  "A122", "TH 6 7");
        createCourse("BIO F110 P6", "BIOLOGY LABORATORY",  "A122", "TH 8 9");
        createCourse("BIO F110 P7", "BIOLOGY LABORATORY",  "A122", "F 6 7");
        createCourse("BIO F110 P8", "BIOLOGY LABORATORY",  "A122", "F 8 9");

        createCourse("BIO F111 L1", "GENERAL BIOLOGY", "F105", "T Th S 2");
        createCourse("BIO F111 T1", "GENERAL BIOLOGY", "G201", "M 1");
        createCourse("BIO F111 T2", "GENERAL BIOLOGY",  "G202", "M 1");
        createCourse("BIO F111 T3", "GENERAL BIOLOGY",  "G203", "M 1");
        createCourse("BIO F111 T4", "GENERAL BIOLOGY", "G204", "M 1");
        createCourse("BIO F111 T5", "GENERAL BIOLOGY", "G205", "M 1");
        createCourse("BIO F111 T6", "GENERAL BIOLOGY",  "G201", "W 7");
        createCourse("BIO F111 T7", "GENERAL BIOLOGY",  "G202", "W 7");
        createCourse("BIO F111 T8", "GENERAL BIOLOGY",  "G203", "W 7");

        createCourse("BITS F110 L1", "ENGINEERING GRAPHICS",  "F105", "F 5");
        createCourse("BITS F110 P1", "ENGINEERING GRAPHICS", "D208", "M 3 4");
        createCourse("BITS F110 P2", "ENGINEERING GRAPHICS",  "D208", "M 6 7");
        createCourse("BITS F110 P3", "ENGINEERING GRAPHICS",  "D208", "M 8 9");
        createCourse("BITS F110 P4", "ENGINEERING GRAPHICS", "D208", "T 3 4");
        createCourse("BITS F110 P5", "ENGINEERING GRAPHICS",  "D208", "T 6 7");

        createCourse("CHEM F110 P1", "CHEMISTRY LABORATORY",  "B124", "M 3 4");
        createCourse("CHEM F110 P2", "CHEMISTRY LABORATORY",  "B124", "W 3 4");
        createCourse("CHEM F110 P3", "CHEMISTRY LABORATORY",  "B124", "TH 3 4");
        createCourse("CHEM F110 P4", "CHEMISTRY LABORATORY", "B124", "F 3 4");
        createCourse("CHEM F110 P5", "CHEMISTRY LABORATORY",  "B124", "F 6 7");
        createCourse("CHEM F110 P6", "CHEMISTRY LABORATORY",  "B124", "F 8 9");
        createCourse("CHEM F110 P7", "CHEMISTRY LABORATORY",  "B124", "S 3 4");


        createCourse("CHEM F111 L1", "GENERAL CHEMISTRY",  "F102", "T Th S 2");
        createCourse("CHEM F111 T1", "GENERAL CHEMISTRY",  "G101", "M W 1");
        createCourse("CHEM F111 T2", "GENERAL CHEMISTRY",  "G102", "M W 1");
        createCourse("CHEM F111 T3", "GENERAL CHEMISTRY",  "G103", "M W 1");
        createCourse("CHEM F111 T4", "GENERAL CHEMISTRY",  "G104", "M W 1");
        createCourse("CHEM F111 T5", "GENERAL CHEMISTRY",  "G105", "M W 1");
        createCourse("CHEM F111 T6", "GENERAL CHEMISTRY", "G106", "M W 1");
        createCourse("CHEM F111 T7", "GENERAL CHEMISTRY", "G108", "M W 1");

        createCourse("CS F111 L1", "COMPUTER PROGRAMMING",  "F102", "M W 5");
        createCourse("CS F111 L2", "COMPUTER PROGRAMMING",  "F105", "M W 5");
        createCourse("CS F111 L3", "COMPUTER PROGRAMMING",  "F103", "M W 5");
        createCourse("CS F111 P1", "COMPUTER PROGRAMMING",  "B229", "M W 8");
        createCourse("CS F111 P2", "COMPUTER PROGRAMMING",  "B229", "M W 9");
        createCourse("CS F111 P3", "COMPUTER PROGRAMMING",  "B211", "T S 3");
        createCourse("CS F111 P4", "COMPUTER PROGRAMMING",  "B229", "T Th 7");
        createCourse("CS F111 P5", "COMPUTER PROGRAMMING",  "B229", "T Th 8");
        createCourse("CS F111 P6", "COMPUTER PROGRAMMING",  "B229", "T Th 9");
        createCourse("CS F111 P7", "COMPUTER PROGRAMMING",  "B211", "W F 3");
        createCourse("CS F111 P8", "COMPUTER PROGRAMMING",  "B229", "W F 7");
        createCourse("CS F111 P9", "COMPUTER PROGRAMMING",  "B211", "W F 9");
        createCourse("CS F111 P10", "COMPUTER PROGRAMMING",  "B211", "Th S 4");
        createCourse("CS F111 P11", "COMPUTER PROGRAMMING",  "B211", "Th S 5");



        createCourse("EEE F111 L1", "ELECTRICAL SCIENCES",  "F102", "M W F 2");
        createCourse("EEE F111 L2", "ELECTRICAL SCIENCES",  "F105", "M W F 2");
        createCourse("EEE F111 T1", "ELECTRICAL SCIENCES",  "G104", "Th 1");
        createCourse("EEE F111 T2", "ELECTRICAL SCIENCES",  "G108", "Th 1");
        createCourse("EEE F111 T3", "ELECTRICAL SCIENCES",  "G105", "Th 1");
        createCourse("EEE F111 T4", "ELECTRICAL SCIENCES",  "G106", "Th 1");
        createCourse("EEE F111 T5", "ELECTRICAL SCIENCES",  "G107", "Th 1");
        createCourse("EEE F111 T6", "ELECTRICAL SCIENCES",  "G205", "Th 1");
        createCourse("EEE F111 T7", "ELECTRICAL SCIENCES",  "G207", "Th 1");
        createCourse("EEE F111 T8", "ELECTRICAL SCIENCES",  "G206", "Th 1");
        createCourse("EEE F111 T9", "ELECTRICAL SCIENCES",  "G208", "Th 1");

        createCourse("MATH F112 L1", "MATHEMATICS II",  "G101", "M W F 3");
        createCourse("MATH F112 L2", "MATHEMATICS II",  "G201", "M W F 3");
        createCourse("MATH F112 L3", "MATHEMATICS II",  "G108", "M W F 3");
        createCourse("MATH F112 L4", "MATHEMATICS II",  "G101", "M W F 7");
        createCourse("MATH F112 L5", "MATHEMATICS II",  "F107", "M W F 9");
        createCourse("MATH F112 L6", "MATHEMATICS II",  "F108", "M W F 9");
        createCourse("MATH F112 L7", "MATHEMATICS II",  "G101", "T Th S 4");
        createCourse("MATH F112 L8", "MATHEMATICS II",  "G108", "T Th S 4");
        createCourse("MATH F112 L9", "MATHEMATICS II",  "G101", "T Th S 5");
        createCourse("MATH F112 T1", "MATHEMATICS II",  "F202", "T 1");
        createCourse("MATH F112 T2", "MATHEMATICS II",  "G204", "T 1");
        createCourse("MATH F112 T3", "MATHEMATICS II",  "G206", "T 1");
        createCourse("MATH F112 T4", "MATHEMATICS II",  "G105", "T 1");
        createCourse("MATH F112 T5", "MATHEMATICS II",  "F107", "T 1");
        createCourse("MATH F112 T6", "MATHEMATICS II",  "F108", "T 1");
        createCourse("MATH F112 T7", "MATHEMATICS II",  "G101", "T 1");
        createCourse("MATH F112 T8", "MATHEMATICS II",  "G107", "T 1");
        createCourse("MATH F112 T9", "MATHEMATICS II",  "G201", "T 1");

        createCourse("MATH F113 L1", "PROBABILITY AND STATISTICS",  "G101", "M W F 4");
        createCourse("MATH F113 L2", "PROBABILITY AND STATISTICS",  "F108", "M W F 7");
        createCourse("MATH F113 L3", "PROBABILITY AND STATISTICS",  "G101", "M W F 8");
        createCourse("MATH F113 L4", "PROBABILITY AND STATISTICS",  "G105", "M W F 8");
        createCourse("MATH F113 L5", "PROBABILITY AND STATISTICS",  "F109", "T Th S 3");
        createCourse("MATH F113 L6", "PROBABILITY AND STATISTICS",  "G102", "T Th S 3");
        createCourse("MATH F113 L7", "PROBABILITY AND STATISTICS",  "G102", "T Th S 5");
        createCourse("MATH F113 L8", "PROBABILITY AND STATISTICS",  "G105", "T Th S 5");
        createCourse("MATH F113 L9", "PROBABILITY AND STATISTICS",  "F108", "T Th S 5");
        createCourse("MATH F113 T1", "PROBABILITY AND STATISTICS",  "G101", "F 1");
        createCourse("MATH F113 T2", "PROBABILITY AND STATISTICS",  "F108", "F 1");
        createCourse("MATH F113 T3", "PROBABILITY AND STATISTICS",  "G201", "F 1");
        createCourse("MATH F113 T4", "PROBABILITY AND STATISTICS",  "F202", "F 1");
        createCourse("MATH F113 T5", "PROBABILITY AND STATISTICS",  "F107", "F 1");
        createCourse("MATH F113 T6", "PROBABILITY AND STATISTICS",  "F109", "F 1");
        createCourse("MATH F113 T7", "PROBABILITY AND STATISTICS",  "G106", "F 1");
        createCourse("MATH F113 T8", "PROBABILITY AND STATISTICS",  "G207", "F 1");
        createCourse("MATH F113 T9", "PROBABILITY AND STATISTICS",  "G108", "F 1");

        createCourse("ME F110 P1", "WORKSHOP PRACTICE",  "WS", "M W 3 4");
        createCourse("ME F110 P2", "WORKSHOP PRACTICE",  "WS", "M W 6 7");
        createCourse("ME F110 P3", "WORKSHOP PRACTICE",  "WS", "T Th 3 4");
        createCourse("ME F110 P4", "WORKSHOP PRACTICE",  "WS", "T Th 6 7");
        createCourse("ME F110 P5", "WORKSHOP PRACTICE",  "WS", "T Th 8 9");

        createCourse("PHY F110 P1", "PHYSICS LABORATORY",  "A222", "M 3 4");
        createCourse("PHY F110 P2", "PHYSICS LABORATORY",  "A222", "M 6 7");
        createCourse("PHY F110 P3", "PHYSICS LABORATORY",  "A222", "M 8 9");
        createCourse("PHY F110 P4", "PHYSICS LABORATORY",  "A222", "T 3 4");
        createCourse("PHY F110 P5", "PHYSICS LABORATORY",  "A222", "T 6 7");
        createCourse("PHY F110 P6", "PHYSICS LABORATORY",  "A222", "T 8 9");
        createCourse("PHY F110 P7", "PHYSICS LABORATORY",  "A222", "W 3 4");
        createCourse("PHY F110 P8", "PHYSICS LABORATORY",  "A222", "W 6 7");
        createCourse("PHY F110 P9", "PHYSICS LABORATORY",  "A222", "W 8 9");
        createCourse("PHY F110 P10", "PHYSICS LABORATORY",  "A222", "Th 3 4");
        createCourse("PHY F110 P11", "PHYSICS LABORATORY",  "A222", "Th 6 7");
        createCourse("PHY F110 P12", "PHYSICS LABORATORY",  "A222", "Th 8 9");
        createCourse("PHY F110 P13", "PHYSICS LABORATORY",  "A222", "F 3 4");
        createCourse("PHY F110 P14", "PHYSICS LABORATORY",  "A222", "F 6 7");
        createCourse("PHY F110 P15", "PHYSICS LABORATORY",  "A222", "F 8 9");

    }

}