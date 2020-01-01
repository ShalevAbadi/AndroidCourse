package com.example.crazydrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private int centerViewId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        final RelativeLayout rl = new RelativeLayout(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        rl.setLayoutParams(lp);
        setContentView(rl);
        renderMainScreen(rl);
        renderEasyButton(rl);
        renderHardButton(rl);
        renderTopTenButton(rl);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void renderMainScreen(RelativeLayout rl){
        rl.setBackgroundResource(R.drawable.main_screen);
    }

    public void renderEasyButton(RelativeLayout rl){
        Button btnTag = new Button(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        btnTag.setLayoutParams(lp);
        btnTag.setText("Easy");
        btnTag.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        btnTag.setAlpha((float)0.65);
        btnTag.setBackgroundColor(Color.TRANSPARENT);
        lp.addRule(RelativeLayout.ABOVE, centerViewId);

        //add button to the layout
        rl.addView(btnTag);

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra(GameActivity.DIFFICULTY_KEY, GameActivity.Difficulty.EASY.getDifficulty());
                MainActivity.this.startActivity(myIntent);
            }
            });
        }


    public void renderHardButton(RelativeLayout rl){
        Button btnTag = new Button(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        lp.setMargins(10,10,10,10);
        btnTag.setLayoutParams(lp);
        btnTag.setText("Hard");
        btnTag.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        btnTag.setAlpha((float)0.65);
        btnTag.setBackgroundColor(Color.TRANSPARENT);
        btnTag.setId(centerViewId);
        //add button to the layout
        rl.addView(btnTag);

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra(GameActivity.DIFFICULTY_KEY, GameActivity.Difficulty.HARD.getDifficulty());
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    public void renderTopTenButton(RelativeLayout rl){
        Button hardBtnTag = new Button(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        lp.setMargins(10,10,10,10);
        hardBtnTag.setLayoutParams(lp);
        hardBtnTag.setText("Top 10");
        hardBtnTag.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        hardBtnTag.setAlpha((float)0.65);
        hardBtnTag.setBackgroundColor(Color.TRANSPARENT);
        lp.addRule(RelativeLayout.BELOW, centerViewId);
        rl.addView(hardBtnTag);

        hardBtnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, TopTenActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
