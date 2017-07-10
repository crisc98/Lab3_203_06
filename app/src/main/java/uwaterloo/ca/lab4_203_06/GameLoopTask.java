package uwaterloo.ca.lab4_203_06;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

public class GameLoopTask extends TimerTask {
    String direction;
    GameBlock gb;
    float BLOCKX,BLOCKY, SCALE;
    ImageView gameBoard;
    Activity thisActivity;
    GameLoopTask thisTask=this;
    Position[][] positions;

    public final float ORIGIN_X = -38, ORIGIN_Y = 117;
    public GameLoopTask(String dir, GameBlock gb, ImageView gameBoard, float blockx, float blocky, float SCALE, Activity act, Position[][] positions){
        direction=dir;
        this.gb=gb;
        BLOCKX=blockx;
        BLOCKY=blocky;
        this.gameBoard=gameBoard;
        BLOCKX = gb.getX();
        BLOCKY = gb.getY();
        this.SCALE = SCALE;
        thisActivity= act;
        this.positions=positions;
        //positions[gb.getBlocknum_x()][gb.getBlocknum_y()].setOccupied(false);
        //Log.d("GAMEBOARD", String.format("BLOCK.XY, %f, %f", BLOCKX, BLOCKY));
        //assign();
    }

    public Position getPosition(float x, float y){
        int e=4;
        for(int i=0;i<4;i++) {
            for (int j = 0; j < 4; j++) {
                if ((positions[i][j].getX() < x+e || positions[i][j].getX() >x-e)&& (positions[i][j].getY() < y+e || positions[i][j].getY()> y-e)) {
                    return positions[i][j];
                }
            }
        }
        return null;
    }

    @Override
    public void run(){
        thisActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int xy[] = new int[2];
                gameBoard.getLocationOnScreen(xy);
                //Log.d("GAMEBLOCK", "gb_x: " + gb.getBlocknum_x() + " gb_y: " + gb.getBlocknum_y());
                switch (direction) {
                    case "RIGHT":
                        int right_stop = gb.getStop();
                        if (gb.getX() + gb.getVelocity() >= ORIGIN_X + (gb.getWidth() * SCALE * right_stop)) {
                            gb.setX(ORIGIN_X + (gb.getWidth() * SCALE * right_stop));
                            positions[gb.getBlocknum_y()][gb.getBlocknum_x()].setOccupied(false);
                            gb.setBlocknum_x(right_stop);
                            positions[gb.getBlocknum_y()][right_stop].setOccupied(true);
                            //Log.d("STOP", "stop: " + stop + " OCCUPIED: " +  positions[stop][gb.getBlocknum_y()].isOccupied());
                            Log.d("FINAL POS", "gb.x: " + gb.getBlocknum_x() + " gb.y: " + gb.getBlocknum_y());
                            for (int i = 0; i < 4; i++) {
                                for(int j=0;j<4;j++){
                                    Log.d("LOOP","BLOCK_: " + j + ", " + i +" " + positions[i][j].isOccupied());
                                }
                            }
                            Log.d("LOOP_RIGHT","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            gb.setisDoneMov(true);
                            thisTask.cancel();
                        } else {
                            gb.setX(gb.getX() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() + 10);
                        }
                        break;
                    case "LEFT":
                        int left_stop = gb.getStop();
                        if (gb.getX() + gb.getVelocity() <= (ORIGIN_X + (gb.getWidth() * SCALE * left_stop))) {
                            gb.setX(ORIGIN_X + (gb.getWidth() * SCALE * left_stop));
                            positions[gb.getBlocknum_y()][gb.getBlocknum_x()].setOccupied(false);
                            gb.setBlocknum_x(left_stop);
                            positions[gb.getBlocknum_y()][left_stop].setOccupied(true);
                            Log.d("FINAL POS", "gb.x: " + gb.getBlocknum_x() + " gb.y: " + gb.getBlocknum_y());
                            for (int i = 0; i < 4; i++) {
                                for(int j=0;j<4;j++){
                                    Log.d("LOOP","BLOCK_: " + j + ", " + i +" " + positions[i][j].isOccupied());
                                }
                            }
                            Log.d("LOOP_LEFT","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            gb.setisDoneMov(true);
                            thisTask.cancel();
                        } else {
                            gb.setX(gb.getX() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() - 10);
                        }
                        break;
                    case "UP":
                        int top_stop = gb.getStop();
                        if (gb.getY() + gb.getVelocity() <= (ORIGIN_Y + (gb.getHeight() * SCALE * top_stop))) {
                            gb.setY(ORIGIN_Y + (gb.getWidth() * SCALE * top_stop));
                            positions[gb.getBlocknum_y()][gb.getBlocknum_x()].setOccupied(false);
                            gb.setBlocknum_y(top_stop);
                            positions[top_stop][gb.getBlocknum_x()].setOccupied(true);
                            gb.setisDoneMov(true);
                            thisTask.cancel();
                        } else {
                            gb.setY(gb.getY() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() - 10);
                        }
                        break;
                    case "DOWN":
                        int bot_stop = gb.getStop();
                        if (gb.getY() + gb.getVelocity() >= (ORIGIN_Y + (gb.getHeight() * SCALE * bot_stop))) {
                            gb.setY(ORIGIN_Y + (gb.getHeight() * SCALE * bot_stop));
                            positions[gb.getBlocknum_y()][gb.getBlocknum_x()].setOccupied(false);
                            gb.setBlocknum_y(bot_stop);
                            positions[bot_stop][gb.getBlocknum_x()].setOccupied(true);
                            gb.setisDoneMov(true);
                            thisTask.cancel();
                        } else {
                            gb.setY(gb.getY() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() + 10);
                        }
                        break;
                }
            }
        });

    }

}