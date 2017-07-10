package uwaterloo.ca.lab4_203_06;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.Vector;

public class GameBlock extends AppCompatImageView {
    float velocity = 0, blockx, blocky;
    public final float OFFSET_X = -68, OFFSET_Y = 87;
    public final float SCALE = .65f;
    Timer time;
    ImageView gameBoard;
    int blocknum_x, blocknum_y;
    RelativeLayout rl;
    int srcImg, stop;
    Context ctx;
    Activity act;
    Position[][] positions;
    int currBlocknum;
    private boolean isDoneMoving;
    public GameBlock(RelativeLayout rl, int img, float x, float y, Context ctx, Timer time, ImageView gameBoard, Activity act,
                     Position[][] positions, int blocknum_x, int blocknum_y, int currBlocknum) {
        super(ctx);
        setImageResource(img);
        setX(OFFSET_X+x);
        setY(OFFSET_Y+y);
        setScaleX(SCALE);
        setScaleY(SCALE);
        rl.addView(this);
        this.ctx=ctx;
        srcImg=img;
        this.rl=rl;
        this.time = time;
        this.blockx=x;
        this.blocky=y;
        this.gameBoard=gameBoard;
        this.act=act;
        this.positions=positions;
        this.blocknum_x = blocknum_x;
        this.blocknum_y = blocknum_y;
        this.currBlocknum = currBlocknum;
        isDoneMoving = true;
        Log.d("NEW SPAWN", "blocknum_x: " + blocknum_x + " blocknum_y: " + blocknum_y);
    }

    public void move(String dir) {//move gameblock with animation
        velocity = 0;
        //positions[blocknum_x][blocknum_y].setOccupied(false);
        isDoneMoving = false;
        time.schedule(new GameLoopTask(dir, this, gameBoard, blockx,blocky, SCALE,act, positions), 25, 25);
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float v) {
        velocity = v;
    }

    public void setisDoneMov(boolean done){
        isDoneMoving = done;
    }
    public boolean isDoneMov(){
        return isDoneMoving;
    }
    public int getBlocknum_x(){return blocknum_x;}

    public int getBlocknum_y(){return blocknum_y;}
    public void setStop(int stop_pos){
        stop = stop_pos;
    }
    public int getStop(){
        return stop;
    }
    public void setBlocknum_x(int x){
        blocknum_x = x;
    }
    public void setBlocknum_y(int y){
        blocknum_y = y;
    }

    public Context getCtx() {
        return ctx;
    }

    public RelativeLayout getRl() {
        return rl;
    }

    public int getSrcImg() {
        return srcImg;
    }

    public Timer getTime() {
        return time;
    }
}

