package com.iesebre.dam2.max.todosandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by max on 10/12/15.
 */
public class Utils {

    public static void displayToast(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
