package com.fragilebytes.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Profile {
    int id;
    String firstName;
    String lastName;
    String birthDate;
    String nationality;
    String gender;
    byte[] profilePic;

    public Profile(){

    }

    public Profile(int id, String firstName, String lastName, String birthDate, String nationality, String gender, byte[] profilePic){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
        this.profilePic = profilePic;
    }

    public Profile(String firstName, String lastName, String birthDate, String nationality, String gender, Bitmap profilePic){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
        this.profilePic = getBytesOfImage(profilePic);

    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }

    public String getNationality(){
        return this.nationality;
    }

    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public String getGender(){
        return this.gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public byte[] getProfilePic(){return this.profilePic;}

    public void setProfilePic(byte[] profilePic){this.profilePic = profilePic;}

    public static byte[] getBytesOfImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImageFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
