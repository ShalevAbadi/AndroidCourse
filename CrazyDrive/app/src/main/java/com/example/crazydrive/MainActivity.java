package com.example.crazydrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import android.app.usage.UsageEvents;
import android.graphics.Color;
import android.graphics.Point;
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
        setContentView(R.layout.activity_main);
        ConfigManager conf = ConfigManager.getInstance();
        int lanesCount = 3;
        int laneWidth = getLaneWidth(lanesCount);
        conf.setLanesCount(lanesCount);
        conf.setLaneWidth(laneWidth);
        conf.setRoadLength(getRoadLength());
        final MoveController mc = new MoveController(conf.getLanesCount(), conf.getLanesCount()/2);
        conf.setMoveController(mc);
        GameManager gm = GameManager.getInstance();

        RelativeLayout rl = this.render(gm);
        rl.setOnTouchListener(mc);

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            public void run() {
                // need to do tasks on the UI thread
                RelativeLayout rl = render(GameManager.getInstance());
                rl.setOnTouchListener(mc);
                handler.postDelayed(this, 100);
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



    public RelativeLayout render(GameManager gm){

        final RelativeLayout rl = new RelativeLayout(getApplicationContext());
        // Create LayoutParams for RelativeLayout
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        // Set RelativeLayout LayoutParams
        rl.setLayoutParams(lp);
        // Set a background color for RelativeLayout
        rl.setBackgroundColor(Color.parseColor("#000000"));
        ConfigManager config = ConfigManager.getInstance();
        gm.doStep();
        renderPlayerCar(rl, gm.getPlayerCar(), config.getLaneWidth(), config.getRoadLength());
        renderRoad(rl, gm.getRoad());
        setContentView(rl);
        return rl;

    }

    public void renderPlayerCar(RelativeLayout rl, PlayerCar playerCar, int laneWidth, int roadLength){
        ImageView imageView_playerCar = new ImageView(this);
        imageView_playerCar.setImageResource(R.drawable.player_car);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(playerCar.getWidth(), playerCar.getHeight() );
        imageView_playerCar.setLayoutParams(lp);
        lp.width = laneWidth;
        imageView_playerCar.setX((laneWidth) * playerCar.getLane());
        imageView_playerCar.setY(roadLength - playerCar.getHeight() - 70);
        rl.addView(imageView_playerCar);
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

    /*
    public void render(int currentLane, int lanesCount) {
        // Initialize a new RelativeLayout
        final RelativeLayout rl = new RelativeLayout(getApplicationContext());
        // Create LayoutParams for RelativeLayout
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        // Set RelativeLayout LayoutParams
        rl.setLayoutParams(lp);
        // Set a background color for RelativeLayout
        rl.setBackgroundColor(Color.parseColor("#000000"));
        final ConfigManager config = ConfigManager.getInstance();
        config.setLanesCount(3);
        MoveController moveController = new MoveController(config.getLanesCount(), 1);
        final PlayerCar playerCar = new PlayerCar(moveController, new SpeedController(300), lane);
        setContentView(rl);
        View v = null; MotionEvent event = null;
        rl.setOnTouchListener(moveController);
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            public void run() {
                // need to do tasks on the UI thread
                rl.removeAllViewsInLayout();
                ImageView imageView_playerCar = createPlayerCarView(config.getLanesCount());
                renderPlayerCar(rl, playerCar.getLane(), config.getLanesCount(), imageView_playerCar);
                ImageView imageView_policeCar = createPoliceCarView(config.getLanesCount());
                renderPoliceCar(rl, 1, config.getLanesCount(), imageView_policeCar);
                    handler.postDelayed(this, 300);
            }
        };

        handler.post(runnable);
    }


    public void renderPoliceCar(RelativeLayout rl, int currentLane, int lanes, ImageView imageView_policeCar ){
        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getSize(size);
        int sizeX = size.x;
        int sizeY = size.y;
        int laneWidth = sizeX / lanes;
        imageView_policeCar.setX((laneWidth) * currentLane);
        imageView_policeCar.setY(0);
        rl.addView(imageView_policeCar);
    }

    public ImageView createPoliceCarView(int lanes){
        ImageView imageView_policeCar = new ImageView(this);
        imageView_policeCar.setImageResource(R.drawable.police_car);
        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getSize(size);
        int sizeX = size.x;
        int sizeY = size.y;
        int laneWidth = sizeX / lanes;
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(laneWidth/ 2, laneWidth );
        imageView_policeCar.setLayoutParams(lp);
        lp.width = laneWidth;
        return imageView_policeCar;
    }


    public void renderPlayerCar(RelativeLayout rl, int currentLane, int lanes, ImageView imageView_playerCar){
        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getSize(size);
        int sizeX = size.x;
        int sizeY = size.y;
        int laneWidth = sizeX / lanes;
        imageView_playerCar.setX((laneWidth) * currentLane);
        imageView_playerCar.setY(sizeY - laneWidth - 70);
        rl.addView(imageView_playerCar);

    }

    public ImageView createPlayerCarView(int lanes) {
        ImageView imageView_playerCar = new ImageView(this);
        imageView_playerCar.setImageResource(R.drawable.player_car);

        Display display = getWindowManager().getDefaultDisplay();// find screen size X , Y
        Point size = new Point();
        display.getSize(size);
        int sizeX = size.x;
        int sizeY = size.y;
        int laneWidth = sizeX / lanes;
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(laneWidth/ 2, laneWidth );
        imageView_playerCar.setLayoutParams(lp);
        lp.width = laneWidth;
        return imageView_playerCar;
    }

 */
}
