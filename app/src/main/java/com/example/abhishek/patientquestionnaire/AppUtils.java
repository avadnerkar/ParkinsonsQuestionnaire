package com.example.abhishek.patientquestionnaire;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

/**
 * Created by Tao-Nhan Nguyen on 2014-09-29.
 * Copyright (c) 2014 Mighty Cast, Inc. All rights reserved.
 */

public class AppUtils {
    private final static String CLASSTAG = AppUtils.class.getSimpleName();

    public static ProgressDialog createProgressDialog(Context mContext) {

        final ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            Activity a = (Activity) mContext;
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        return dialog;
    }


    // SIMPLE WARNING ALERT
    @SuppressWarnings("deprecation")
    public static void showAlert(final String title, final String msg, final Context context) {

        Activity a = (Activity) context;
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle(title);
                alertDialog.setMessage(msg);
                alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    /*
    Displays custom one button dialog
     */
    public static void showSimpleDialog(String message, Context context){
        final DialogSimple dialog = new DialogSimple(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_title);
        textView.setText(message);
    }

    public static void showTwoButtonDialog(String message, String positiveButton, String negativeButton, Context context, DialogTwoButton.ClickHandler clickHandler){
        final DialogTwoButton dialog = new DialogTwoButton(context, clickHandler);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_title);
        textView.setText(message);

        textView = (TextView) dialog.findViewById(R.id.dialog_ok);
        textView.setText(positiveButton);

        textView = (TextView) dialog.findViewById(R.id.dialog_cancel);
        textView.setText(negativeButton);
    }

    public static void showThreeButtonDialog(String message, String positiveButton, String negativeButton, String thirdButton, Context context, DialogThreeButton.ClickHandler clickHandler){
        final DialogThreeButton dialog = new DialogThreeButton(context, clickHandler);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_title);
        textView.setText(message);

        textView = (TextView) dialog.findViewById(R.id.dialog_ok);
        textView.setText(positiveButton);

        textView = (TextView) dialog.findViewById(R.id.dialog_cancel);
        textView.setText(negativeButton);

        textView = (TextView) dialog.findViewById(R.id.dialog_third);
        textView.setText(thirdButton);
    }

    /*
    Displays custom list dialog
     */
    public static void showListDialog(String title, String[] items, Context context, final ListHandler h){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                h.onClick(i);
            }
        });
        builder.show();
    }

    public interface ListHandler{
        void onClick(int i);
    }




    public static void showAlertWithImage(String title, String msg, Context context, ImageView image) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context).
                        setTitle(title).
                        setMessage(msg).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).
                        setView(image);
        builder.create().show();
    }

    public static void showPopup(final String msg, final Context context) {
        Activity a = (Activity) context;
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }




    public static Bitmap decodeUriToCompressedBitmap(Uri selectedImage, Context context) throws FileNotFoundException {
        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o);

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o2);
    }


}
