package com.sh.study.udacitynano.planner.ui.list;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.sh.study.udacitynano.planner.widget.PlannerWidget;

/**
 * Collected preferences for List
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-23
 */
public final class ListPreferences {

    private static final String SOURCE_STATUS_FREF = "status";
//    private static final String SOURCE_QUERY_FREF = "query";
    private static final String WIDGET_FREF = "widget";

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
     * Active event category name for widget
     * @param context
     * @return
     */
    public static String getWidgetDataPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(WIDGET_FREF, "No active events");
    }

    /**
     * Active event category name for widget
     * @param application
     * @param context
     * @param text
     */
    public static void setWidgetDataPreferences(Application application, Context context, String text) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(WIDGET_FREF, text)
                .apply();

        Intent intent = new Intent(context, PlannerWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(application, PlannerWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        application.sendBroadcast(intent);
    }
}
