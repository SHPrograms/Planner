package com.sh.study.udacitynano.planner.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.ui.list.ListPreferences;

/**
 * Implementation of App Widget functionality.
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-29
 */
public class PlannerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.planner_widget);

        if (ListPreferences.getWidgetDataPreferences(context).isEmpty()) {
            views.setTextViewText(R.id.widget_title, context.getString(R.string.widget_title_empty));
        } else {
            views.setTextViewText(R.id.widget_title, context.getString(
                    R.string.widget_title,
                    ListPreferences.getWidgetDataPreferences(context))
            );
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

