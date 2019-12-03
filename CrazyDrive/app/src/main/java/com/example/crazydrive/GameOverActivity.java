package com.example.crazydrive;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        final RelativeLayout rl = new RelativeLayout(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        rl.setLayoutParams(lp);
        rl.setBackgroundColor(Color.parseColor("#000000"));
        setContentView(rl);
        renderGameOverScreen(rl, score);

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

    public void renderGameOverScreen(RelativeLayout rl , int score){
        RelativeLayout.LayoutParams msgLp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams scoreLp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams btnLp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView msg = new TextView(this);
        msg.setText("Your Score:");
        msg.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        msg.setId((int)1);
        msg.setLayoutParams(msgLp);
        msg.setBackgroundColor(Color.TRANSPARENT);
        msg.setTextColor(Color.RED);
        TextView scoreView = new TextView(this);
        scoreView.setText("" + score);
        scoreView.setBackgroundColor(Color.TRANSPARENT);
        scoreView.setId((int)2);
        scoreView.setLayoutParams(scoreLp);
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        scoreView.setTextColor(Color.RED);
        Button btnTag = new Button(this);
        btnTag.setText("Main Menu");
        btnTag.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        btnTag.setBackgroundColor(Color.TRANSPARENT);
        btnTag.setLayoutParams(btnLp);
        scoreLp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        msgLp.addRule(RelativeLayout.ABOVE, scoreView.getId());
        msgLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btnLp.addRule(RelativeLayout.BELOW, scoreView.getId());
        btnLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl.addView(msg);
        rl.addView(scoreView);
        rl.addView(btnTag);

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
