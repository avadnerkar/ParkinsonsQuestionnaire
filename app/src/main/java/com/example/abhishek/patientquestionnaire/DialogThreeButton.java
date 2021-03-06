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
 * Custom 2 button dialog, with clickhandler
 * *****************************************************************************************
 */
public class DialogThreeButton extends Dialog {

    public Context context;
    public Dialog d;
    public Button positiveButton, negativeButton, thirdButton;
    public ClickHandler handler;

    public DialogThreeButton(Context context, ClickHandler handler) {
        super(context);
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_three_button);
        positiveButton = (Button) findViewById(R.id.dialog_ok);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onPositiveClick();
                dismiss();
            }
        });

        negativeButton = (Button) findViewById(R.id.dialog_cancel);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onNegativeClick();
                dismiss();
            }
        });

        thirdButton = (Button) findViewById(R.id.dialog_third);
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onThirdClick();
                dismiss();
            }
        });
    }

    public interface ClickHandler{
        void onPositiveClick();

        void onNegativeClick();

        void onThirdClick();
    }

}
