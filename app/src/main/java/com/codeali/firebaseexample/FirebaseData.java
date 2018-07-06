package com.codeali.firebaseexample;

/**
 * Created by Ziggy on 7/6/2018.
 */

// Create a similar class to hold data being saved on firebase

public class FirebaseData {

    private String first_name, last_name, favorite_ice_cream;

    public FirebaseData(){

    }

    public FirebaseData(String fn, String ln, String favic){
        first_name = fn;
        last_name = ln;
        favorite_ice_cream = favic;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFavorite_ice_cream() {
        return favorite_ice_cream;
    }
}
