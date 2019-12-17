package com.example.crazydrive;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    public static final String DIFFICULTY_KEY  = "DIFFICULTY";
    enum Difficulty {
        EASY(0),
        HARD(1);
        private int difficulty;

        Difficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getDifficulty() {
            return difficulty;
        }
    }
    private int diff;
    private boolean isRunning = true;
    private ConfigManager config;
    private RelativeLayout rl;
    private int lanesCount = 5;
    private int initialLives = 3;
    private int initialSpeed = 5;
    private GameManager gm;
    private Handler handler = new Handler();
    private  Map<RoadItem.Types, Integer> dictionaryRoadItemsTypeToDrawable = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        rl = new RelativeLayout(getApplicationContext());
        Bundle b = getIntent().getExtras();
        diff = b.getInt(DIFFICULTY_KEY);
        initRoadItemsDrawablesMap();
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

    private void setGameConfiguration(){
        IMoveController mc = null;
        if (diff == Difficulty.EASY.getDifficulty()){
            lanesCount = 3;
            mc = new MoveControllerOnTouch(lanesCount, lanesCount/2);
            rl.setOnTouchListener((MoveControllerOnTouch)mc);
            rl.setBackgroundResource(R.drawable.road_background_3_lanes);
        } else if (diff == Difficulty.HARD.getDifficulty()){
            lanesCount = 5;
            mc =new MoveControllerSensors(lanesCount, lanesCount/2, this);
            rl.setBackgroundResource(R.drawable.road_background_5_lanes);
        }
        config = ConfigManager.getInstance();
        config.setLanesCount(lanesCount);
        config.setLaneWidth(getLaneWidth());
        config.setRoadLength(getRoadLength());
        config.setSpeedController(new SpeedController(initialSpeed));
        config.setMoveController(mc);
        config.setInitialLife(initialLives);

    }

    private void runGame(){
        setGameConfiguration();
        gm = GameManager.getInstance();
        gm.resetGame();
        gameLoop();

    }

    private void gameLoop() {
        Runnable runnable = new Runnable() {
            public void run() {
                rl.removeAllViewsInLayout();
                gm.doStep();
                renderGame();
                renderScore();
                renderLifeBar();
                if(gm.getLives()>0)

            {
                if (isRunning) {
                    handler.postDelayed(this, 10);
                }
            } else

            {
                Intent gameOverIntent = new Intent(GameActivity.this, GameOverActivity.class);
                gameOverIntent.putExtra(GameOverActivity.SCORE_KEY, (int) gm.getScore());
                GameActivity.this.startActivity(gameOverIntent);

                finish();
            }
        }
        };
        handler.post(runnable);
    }


    private int getLaneWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int sizeX = size.x;
        return sizeX / config.getLanesCount();
    }

    private int getRoadLength(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int navBarSize = 0;
        if (resourceId > 0) {
            navBarSize  = resources.getDimensionPixelSize(resourceId);
        }


        return size.y + navBarSize;
    }

    private void renderGame(){
        renderPlayerCar();
        renderRoad();
    }

    private void renderPlayerCar(){
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

    private void renderLifeBar(){
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

    private void renderScore(){
        RelativeLayout.LayoutParams scoreLp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView scoreView = new TextView(this);
        scoreView.setText((int)gm.getScore() + "$");
        scoreView.setBackgroundColor(Color.TRANSPARENT);
        scoreView.setLayoutParams(scoreLp);
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        scoreView.setTextColor(Color.WHITE);
        scoreLp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        scoreLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rl.addView(scoreView);
    }

    public void renderRoad(){
        Road road = gm.getRoad();
        for(int i = 0; i < ConfigManager.getInstance().getLanesCount(); i++){
            renderLane(road.getQueue(i), i);
        }
    }

    public void renderLane(ArrayList<RoadItem> itemsInLane, int laneNumber){
        for( RoadItem item :itemsInLane){
            renderRoadItem(item, laneNumber);
        }
    }

    public void initRoadItemsDrawablesMap(){
        dictionaryRoadItemsTypeToDrawable.put(RoadItem.Types.POLICE_CAR, Integer.valueOf(R.drawable.police_car));
        dictionaryRoadItemsTypeToDrawable.put(RoadItem.Types.MONEY, Integer.valueOf(R.drawable.dollar_sign));
    }

    public void renderRoadItem(RoadItem roadItem, int lane){
        ImageView imageView_policeCar = new ImageView(this);
        Integer temp = dictionaryRoadItemsTypeToDrawable.get(roadItem.getType());
        imageView_policeCar.setImageResource(temp.intValue());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(roadItem.getWidth(), roadItem.getHeight() );
        imageView_policeCar.setLayoutParams(lp);
        lp.width = config.getLaneWidth();
        imageView_policeCar.setX((config.getLaneWidth()) * lane);
        imageView_policeCar.setY(roadItem.getYPos());
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
