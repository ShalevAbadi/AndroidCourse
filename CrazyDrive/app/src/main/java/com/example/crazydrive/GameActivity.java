package com.example.crazydrive;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private boolean isRunning = true;
    private ConfigManager config;
    private RelativeLayout rl;
    private int lanesCount = 3;
    private int initialLives = 3;
    private int initialSpeed = 10;
    private GameManager gm;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        rl = new RelativeLayout(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        rl.setLayoutParams(lp);
        setContentView(rl);
        runGame();
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

    public void runGame(){
        config = ConfigManager.getInstance();
        config.setLanesCount(lanesCount);
        config.setLaneWidth(getLaneWidth());
        config.setRoadLength(getRoadLength());
        Log.i("Road length", "" + config.getRoadLength());
        config.setInitialLife(initialLives);
        config.setSpeedController(new SpeedController(initialSpeed));
        MoveController mc = new MoveController(config.getLanesCount(), config.getLanesCount()/2);
        config.setMoveController(mc);
        rl.setOnTouchListener(mc);

        gm = GameManager.getInstance();
        gameLoop();

    }

    private void gameLoop() {
        Runnable runnable = new Runnable() {
            public void run() {
                // need to do tasks on the UI thread
                rl.removeAllViewsInLayout();
                gm.doStep();
                renderGame();
                renderLifeBar();
                if (gm.getScore() > ConfigManager.getInstance().getSpeedController().getSpeed()) {
                    ConfigManager.getInstance().getSpeedController().setSpeed((int) gm.getScore());
                }

                if(gm.getLives()>0)

            {
                if (isRunning) {
                    handler.postDelayed(this, 10);
                }
            } else

            {
                Intent myIntent = new Intent(GameActivity.this, GameOverActivity.class);
                myIntent.putExtra("score", (int) gm.getScore());
                GameActivity.this.startActivity(myIntent);
                gm.resetGame();
                finish();
            }
        }
        };
        handler.post(runnable);
    }


    public int getLaneWidth(){
        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getSize(size);
        int sizeX = size.x;
        return sizeX / config.getLanesCount();
    }

    public int getRoadLength(){
        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getRealSize(size);
        return size.y;
    }

    public void renderGame(){
        renderPlayerCar();
        renderRoad();
    }

    public void renderPlayerCar(){
        ImageView imageView_playerCar = new ImageView(this);
        imageView_playerCar.setImageResource(R.drawable.player_car);
        PlayerCar playerCar = gm.getPlayerCar();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(playerCar.getWidth(), playerCar.getHeight());
        imageView_playerCar.setLayoutParams(lp);
        lp.width = config.getLaneWidth();
        imageView_playerCar.setX((config.getLaneWidth()) * playerCar.getLane());
        imageView_playerCar.setY(config.getRoadLength() - playerCar.getHeight());
        rl.addView(imageView_playerCar);
    }

    public void renderLifeBar(){
        int heartMargin = 10;
        int heartWidth = 70;
        int heartHeight = 70;
        LinearLayout parent = new LinearLayout(this);
        parent.setX(0);
        parent.setY(0);
        for( int i = 0; i < gm.getLives(); i++) {
            ImageView imageView_heart = new ImageView(this);
            imageView_heart.setImageResource(R.drawable.heart);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(heartWidth, heartHeight);
            imageView_heart.setLayoutParams(lp);
            parent.addView(imageView_heart);
        }
        parent.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(heartMargin,heartMargin,heartMargin,heartMargin);
        parent.setLayoutParams(params);
        rl.addView(parent);
    }

    public void renderRoad(){
        Road road = gm.getRoad();
        rl.setBackgroundResource(R.drawable.road_background);
        for(int i = 0; i < ConfigManager.getInstance().getLanesCount(); i++){
            renderLane(road.getQueue(i), i);
        }
    }

    public void renderLane(ArrayList<RoadItem> itemsInLane, int laneNumber){
        for( RoadItem item :itemsInLane){
            renderPoliceCar(item, laneNumber);
        }
    }

    public void renderPoliceCar(RoadItem policeCar,int lane){
        ImageView imageView_policeCar = new ImageView(this);
        imageView_policeCar.setImageResource(R.drawable.police_car);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(policeCar.getWidth(), policeCar.getHeight() );
        imageView_policeCar.setLayoutParams(lp);
        lp.width = config.getLaneWidth();
        imageView_policeCar.setX((config.getLaneWidth()) * lane);
        imageView_policeCar.setY(policeCar.getYPos());
        rl.addView(imageView_policeCar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isRunning){
            isRunning = true;
            gameLoop();
        }
    }

}
