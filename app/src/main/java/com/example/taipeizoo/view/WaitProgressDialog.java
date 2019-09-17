package com.example.taipeizoo.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.taipeizoo.R;

public class WaitProgressDialog {
    private volatile static ProgressDialog mWaitProgressDialog;
    @SuppressLint("StaticFieldLeak")
    static Context ctx;
    static boolean isAlert;

    public static ProgressDialog waitProgressDialog(Context mContext, String msg) {
        ctx = mContext;
        isAlert = false;
        if (mWaitProgressDialog == null) {
            synchronized (WaitProgressDialog.class) {
                if (mWaitProgressDialog == null) {
                    mWaitProgressDialog = ProgressDialog.show(mContext, "", msg, true);
                    mWaitProgressDialog.setCancelable(false);
                    mWaitProgressDialog.setCanceledOnTouchOutside(false);
                }
            }
        }
        return mWaitProgressDialog;
    }

    public static void closeDialog() {
        if (mWaitProgressDialog != null) {
            mWaitProgressDialog.dismiss();
            mWaitProgressDialog = null;
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

