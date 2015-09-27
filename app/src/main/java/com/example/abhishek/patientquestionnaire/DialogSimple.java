package com.example.abhishek.patientquestionnaire;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Abhishek on 9/07/2015.
 */

/**
 * *****************************************************************************************
 * Custom 1 button dialog, dismisses on click
 * *****************************************************************************************
 */
public class DialogSimple extends Dialog {

    public Context context;
    public Dialog d;
    public Button ok;

    public DialogSimple(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_all_simple);
        ok = (Button) findViewById(R.id.dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
