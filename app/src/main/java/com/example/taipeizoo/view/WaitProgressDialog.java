package com.example.taipeizoo.view;

import android.app.ProgressDialog;
import android.content.Context;

public class WaitProgressDialog {
    private volatile static ProgressDialog mWaitProgressDialog;

    public static ProgressDialog waitProgressDialog(Context mContext, String msg) {
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
}

