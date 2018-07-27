package com.sh.study.udacitynano.planner.ui.list;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Collected preferences for List
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-23
 */
public final class ListPreferences {

    public static final String SOURCE_STATUS_FREF = "status";
    public static final String SOURCE_QUERY_FREF = "query";

    private ListPreferences() {
        throw new AssertionError();
    }

    /**
     * Category Status
     * @param context activity
     * @return Active or all categories
     */
    public static boolean getSourceStatusPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SOURCE_STATUS_FREF, false);
    }

    /**
     * Category Status
     * @param context
     * @param status True = only active. False = all.
     */
    public static void setSourceStatusPreferences(Context context, boolean status) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(SOURCE_STATUS_FREF, status)
                .apply();
    }

    /**
     * Category Search Text filter
     * @param context
     * @return String
     */
    public static String getSearchTextPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SOURCE_QUERY_FREF, "");
    }

    public static void setSearchTextPreferences(Context context, String searchText) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(SOURCE_QUERY_FREF, searchText)
                .apply();
    }
}
