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
    static final String LIST_PREF_STATUS = "list_status";

    private ListPreferences() {
        throw new AssertionError();
    }

    /**
     * Category Status
     * @param context activity
     * @return
     */
/*
    public static boolean getListStatusPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(LIST_PREF_STATUS, false);
    }

    static void setListStatusPreferences(Context context, boolean status) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(LIST_PREF_STATUS, status)
                .apply();
    }
*/
}
