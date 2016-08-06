package kr.heek.goline.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import kr.heek.counter.LoginSelectActivity;

/**
 * Created by junha on 2016. 7. 15..
 */

public class AlertUtility {

    public static void alert(Context context, String title, String message) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("확인", null).show();
    }
}
