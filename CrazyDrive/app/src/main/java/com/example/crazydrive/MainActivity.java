package com.example.crazydrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import android.app.usage.UsageEvents;
import android.graphics.Color;
import android.graphics.Point;
import android.icu.text.RelativeDateTimeFormatter;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RelativeLayout rl = new RelativeLayout(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        rl.setLayoutParams(lp);
        rl.setBackgroundColor(Color.parseColor("#000000"));
        setContentView(rl);
        runGame(rl);
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

    public void runGame(final RelativeLayout rl){

        ConfigManager conf = ConfigManager.getInstance();
        int lanesCount = 3;
        conf.setLanesCount(lanesCount);
        conf.setLaneWidth(getLaneWidth(lanesCount));
        conf.setRoadLength(getRoadLength());
        MoveController mc = new MoveController(conf.getLanesCount(), conf.getLanesCount()/2);
        conf.setMoveController(mc);

        rl.setOnTouchListener(mc);
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            public void run() {
                // need to do tasks on the UI thread
                rl.removeAllViewsInLayout();
                renderGame(rl);
                GameManager gm = GameManager.getInstance();
                gm.doStep();
                renderLifeBar(rl, gm.getLives());
                if(gm.getLives() > 0){
                    handler.postDelayed(this, 10);
                } else{
                    renderGameOver(rl);
                }
            }
        };

        handler.post(runnable);

    }

    public int getLaneWidth(int lanes){
        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getSize(size);
        int sizeX = size.x;
        return sizeX / lanes;
    }

    public int getRoadLength(){
        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public void renderGameOver(RelativeLayout rl){
        rl.removeAllViewsInLayout();
        ImageView imageView_gameOver = new ImageView(this);
        imageView_gameOver.setImageResource(R.drawable.wasted);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView_gameOver.setLayoutParams(lp);
        rl.addView(imageView_gameOver);
    }

    public void renderGame(RelativeLayout rl){
        GameManager gm = GameManager.getInstance();
        ConfigManager config = ConfigManager.getInstance();
        renderPlayerCar(rl, gm.getPlayerCar(), config.getLaneWidth(), config.getRoadLength());
        renderRoad(rl, gm.getRoad());
    }

    public void renderPlayerCar(RelativeLayout rl, PlayerCar playerCar, int laneWidth, int roadLength){
        ImageView imageView_playerCar = new ImageView(this);
        imageView_playerCar.setImageResource(R.drawable.player_car);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(playerCar.getWidth(), playerCar.getHeight() );
        imageView_playerCar.setLayoutParams(lp);
        lp.width = laneWidth;
        imageView_playerCar.setX((laneWidth) * playerCar.getLane());
        imageView_playerCar.setY(roadLength - playerCar.getHeight() + 70);
        rl.addView(imageView_playerCar);
    }

    public void renderLifeBar(RelativeLayout rl, int livesCount){
        LinearLayout parent = new LinearLayout(this);
        parent.setX(0);
        parent.setY(0);
        for( int i = 0; i < livesCount; i++) {
            ImageView imageView_heart = new ImageView(this);
            imageView_heart.setImageResource(R.drawable.heart);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(70, 70);
            imageView_heart.setLayoutParams(lp);
            parent.addView(imageView_heart);
        }
        parent.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,0);
        parent.setLayoutParams(params);
        rl.addView(parent);
    }

    public void renderRoad(RelativeLayout rl, Road road){
        for(int i = 0; i < ConfigManager.getInstance().getLanesCount(); i++){
            renderLane(rl, road.getQueue(i), ConfigManager.getInstance().getLaneWidth(), i);
        }
    }

    public void renderLane(RelativeLayout rl, ArrayList<RoadItem> itemsInLane, int laneWidth, int laneNumber){
        for( RoadItem item :itemsInLane){
            renderPoliceCar(rl, item, laneWidth, laneNumber);
        }
    }

    public void renderPoliceCar(RelativeLayout rl, RoadItem policeCar,int laneWidth, int lane){
        ImageView imageView_policeCar = new ImageView(this);
        imageView_policeCar.setImageResource(R.drawable.police_car);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(policeCar.getWidth(), policeCar.getHeight() );
        imageView_policeCar.setLayoutParams(lp);
        lp.width = laneWidth;
        imageView_policeCar.setX((laneWidth) * lane);
        imageView_policeCar.setY(policeCar.getYPos());
        rl.addView(imageView_policeCar);
    }



}
