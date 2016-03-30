package com.fragilebytes.profile.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fragilebytes.profile.Profile;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "profilesList01";

    private static final String TABLE_PROFILES = "profiles";

    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_BIRTH_DATE = "birthDate";
    private static final String KEY_NATIONALITY = "nationality";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROFILES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_BIRTH_DATE + " TEXT,"
                + KEY_NATIONALITY + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_IMAGE + " BLOB" + ")";
        database.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        onCreate(database);
    }

    public void addProfile(Profile profile) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, profile.getFirstName());
        values.put(KEY_LAST_NAME, profile.getLastName());
        values.put(KEY_BIRTH_DATE, profile.getBirthDate());
        values.put(KEY_NATIONALITY, profile.getNationality());
        values.put(KEY_GENDER, profile.getGender());
        values.put(KEY_IMAGE, profile.getProfilePic());

        database.insert(TABLE_PROFILES, null, values);
        database.close();
    }

    Profile getProfile(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_PROFILES, new String[] {
                        KEY_ID,
                        KEY_FIRST_NAME,
                        KEY_LAST_NAME,
                        KEY_BIRTH_DATE,
                        KEY_NATIONALITY,
                        KEY_GENDER,
                        KEY_IMAGE
                }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Profile profile = new Profile(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getBlob(6)
                );
        return profile;
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profileList = new ArrayList<Profile>();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILES;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setID(Integer.parseInt(cursor.getString(0)));
                profile.setFirstName(cursor.getString(1));
                profile.setLastName(cursor.getString(2));
                profile.setBirthDate(cursor.getString(3));
                profile.setNationality(cursor.getString(4));
                profile.setGender(cursor.getString(5));
                profile.setProfilePic(cursor.getBlob(6));
                profileList.add(profile);
            } while (cursor.moveToNext());
        }
        return profileList;
    }

    public int updateProfile(Profile profile) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, profile.getFirstName());
        values.put(KEY_LAST_NAME, profile.getLastName());
        values.put(KEY_BIRTH_DATE, profile.getBirthDate());
        values.put(KEY_NATIONALITY, profile.getNationality());
        values.put(KEY_GENDER, profile.getGender());
        values.put(KEY_IMAGE, profile.getProfilePic());

        return database.update(TABLE_PROFILES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(profile.getID()) });
    }

    public void deleteProfile(Profile profile) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_PROFILES, KEY_ID + " = ?",
                new String[]{String.valueOf(profile.getID())});
        database.close();
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PROFILES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public Bitmap getProfilePic(int id) {
        id++;
        SQLiteDatabase database = this.getReadableDatabase();
        Bitmap profilePic = null;
        Cursor cursor =  database.rawQuery("SELECT " + KEY_IMAGE + " FROM " + TABLE_PROFILES + " WHERE " + KEY_ID + "='" + id + "'", null);
        if (cursor.moveToNext()) {
            byte[] image = cursor.getBlob(0);
            profilePic = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return profilePic;
    }

}
