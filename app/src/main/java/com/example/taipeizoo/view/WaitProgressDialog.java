package com.example.taipeizoo.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.taipeizoo.R;

import java.lang.ref.WeakReference;

public class WaitProgressDialog {
    private static WeakReference<ProgressDialog> progressDialogRef;

    @SuppressLint("StaticFieldLeak")
    private static Context ctx;
    private static boolean isAlert;

    public static void showProgressDialog(Context mContext, String msg) {
        isAlert = false;
        ctx = mContext;

        if (progressDialogRef == null) {
            progressDialogRef = new WeakReference<>(new ProgressDialog(mContext));
        }

        if (!progressDialogRef.get().isShowing()) {
            progressDialogRef = new WeakReference<>(new ProgressDialog(mContext));
            progressDialogRef.get().setMessage(msg);
            progressDialogRef.get().show();
        }
    }

    public static void closeDialog() {
        try {
            if (progressDialogRef != null) {
                progressDialogRef.get().dismiss();
                progressDialogRef = null;
            }
        } catch (Exception ignored) {
            progressDialogRef = null;
        }
    }

    public static void noNetwork() {
        try {
            if (!isAlert) {
                isAlert = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage(ctx.getString(R.string.reconnect_network_hint))
                        .setCancelable(false)
                        .setPositiveButton(ctx.getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception ignored) {

        }
    }
}

