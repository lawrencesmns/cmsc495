package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * Created by lawre on 6/29/2016.
 */
public class Utility {

    static final int REQUEST_INGREDIENT = 1;

    public static void composeEmail(String subject, String body, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void debugAlert(Context context, String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage(message);
        alertBuilder.create().show();
    }


}
