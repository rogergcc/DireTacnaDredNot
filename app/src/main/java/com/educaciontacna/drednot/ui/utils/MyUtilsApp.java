/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.ui.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.educaciontacna.drednot.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public final class MyUtilsApp {
    public static final String EXTRA_DOCUMENTO = "documento_extra";

    private MyUtilsApp() {
    }

    public static HashMap<Integer, String> getEstadoDocumento() {
        HashMap<Integer, String> genreMap = new HashMap<>();
        genreMap.put(1, "Pendiente");
        genreMap.put(2, "Notificado");
        genreMap.put(3, "Animation");

        return genreMap;
    }


    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String TAG, String message) {
        Log.d(TAG, "showLog: " + message);
    }

    public static Snackbar showErrorSnackBar(Context mContext, View rootView) {
//        String message = mContext.getString(R.string.dialog_general_error_message);
        String message = "Good! Connected to Internet";

        Snackbar snack_error = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        View view = snack_error.getView();
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.color2));
        return snack_error;
    }

    public static void showLogInfo(String TAG, String message) {
        Log.i(TAG, "showLog Mssage: " + message);
    }

    public static void showLogError(String TAG, String message) {
        Log.e(TAG,  message);
    }

    public static AlertDialog showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }

    public static void showDialogTitleMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        alertDialog.show();
    }

    public static void ShowDialog(Context context, String title, String message) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.location_on_24);
        alertDialog
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(MyConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static String formateToThreeDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.000");
        return df.format(value);
    }
    public String getScanTime() {
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return timeFormat.format(new Date());
    }

    public static String getDateDMY() {
        DateFormat timeFormat = new SimpleDateFormat(MyConstants.TIMESTAMP_FORMAT_DATE, Locale.getDefault());
        return timeFormat.format(new Date());
    }
    public static Date getDateFromString(String datetoSaved){
         final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            Date date = format.parse(datetoSaved);
            return date ;
        } catch (ParseException e){
            return null ;
        }

    }

    private void showSnack(View view, boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }


}
