package com.merseyside.admin.seeworld;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SurfaceActivity extends AppCompatActivity  {

    private MySurfaceView MSV;
    public Button restart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        MSV = new MySurfaceView(this);

        FrameLayout game = new FrameLayout(this);
        LinearLayout buttonsWidget = new LinearLayout (this);

        int white_color = getResources().getColor(R.color.white);
        int grey_color = getResources().getColor(R.color.grey);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        linearParams.setMargins(15, 0, 15, 0);

        restart = new Button(this);
        restart.setText(getResources().getString(R.string.restart));
        restart.setVisibility(View.VISIBLE);
        restart.setTextColor(white_color);
        restart.setBackgroundColor(grey_color);
        restart.setWidth(300);
        restart.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_selector));
        restart.setOnClickListener(view -> {
            MSV.restartGame();
        });

        buttonsWidget.addView(restart, linearParams);
        buttonsWidget.setGravity(Gravity.BOTTOM|Gravity.CENTER);

        game.addView(MSV);
        game.addView(buttonsWidget);

        setContentView(game);
        MSV.setOnTouchListener(MSV);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
