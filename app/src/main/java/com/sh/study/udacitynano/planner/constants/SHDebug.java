package com.sh.study.udacitynano.planner.constants;

import android.util.Log;

/**
 * Testing and information purposes
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-22
 */
public final class SHDebug {
    private SHDebug() {
        throw new AssertionError();
    }

    /*
     * LOG.d purposes
     */
    private static final String DEBUG_TAG = "DebugTag";
    /*
     * LOG.e purposes
     */
    private static final String ERROR_TAG = DEBUG_TAG;
    /*
     * LOG.i purposes
     */
    private static final String INFO_TAG = DEBUG_TAG;
    /*
     * LOG.d purposes
     */
    public static void debugTag(String name, String message) {
        Log.d(DEBUG_TAG, name + " - " + message);
    }
    /*
     * LOG.e purposes
     */
    public static void errorTag(String name, String message) {
        Log.d(ERROR_TAG, name + " - " + message);
    }
    /*
     * LOG.i purposes
     */
    public static void infoTag(String name, String message) {
        Log.d(INFO_TAG, name + " - " + message);
    }

}
