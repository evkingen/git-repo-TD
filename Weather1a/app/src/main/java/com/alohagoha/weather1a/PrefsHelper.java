package com.alohagoha.weather1a;

public interface PrefsHelper {

    String getSharedPreferences(String keyPref, String value);

    void saveSharedPreferences(String keyPref, String value);

    void deleteSharedPreferences(String keyPref);

}
