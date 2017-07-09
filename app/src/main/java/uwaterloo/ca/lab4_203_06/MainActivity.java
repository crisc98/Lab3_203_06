package uwaterloo.ca.lab4_203_06;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.Vector;




public class MainActivity extends AppCompatActivity {

    //Declaring class-wide fields
    SensorEventListener accel;
    Vector<float[]> accelData = new Vector<>();
    Vector<GameBlock>  blocks = new Vector<>();
    public final float BOARDX = 0, BOARDY = -200, BLOCKX = 0, BLOCKY = 0;
    public final float ORIGIN_X = -38, ORIGIN_Y = 117;
    public final float SCALE = .65f;
    ImageView gameBoard;
    Timer time = new Timer();
    RelativeLayout rl;
    Activity activity = this;
    Position[][] positions = new Position[4][4];
    Random rnd = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_203_06);

        //Declares and initializes the linear layout used in the application
        rl = (RelativeLayout) findViewById(R.id.activity_lab3_203_06);

        //Initializes a blank label to display the direction of the gesture
        TextView dirLbl = makeLabel(rl, "Left", 700, 1200);
        dirLbl.setTextSize(32);
        dirLbl.setTextColor(Color.RED);
        TextView l = makeLabel(rl, "Left", 0, 1200);
        l.setTextSize(32);
        TextView r = makeLabel(rl, "Right", 0, 1400);
        r.setTextSize(32);
        TextView u = makeLabel(rl, "Up", 500, 1200);
        u.setTextSize(32);
        TextView d = makeLabel(rl, "Down", 500, 1400);
        d.setTextSize(32);

        //Creates a label that indicates the current accelerometer reading
        gameBoard = makeLabel(rl, R.drawable.gameboard, BOARDX, BOARDY);
        final GameBlock gameBlock = new GameBlock(rl, R.drawable.gameblock, BLOCKX, BLOCKY,
                getApplicationContext(), time, gameBoard, this, positions);
        blocks.add(gameBlock);
        assign();

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position tmpPos =getRandPos();
                moveAll("LEFT");
                blocks.add(new GameBlock(rl, R.drawable.gameblock, tmpPos.getX(), tmpPos.getY(),
                        getApplicationContext(), time, gameBoard, activity, positions));
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position tmpPos =getRandPos();
                moveAll("RIGHT");
                blocks.add(new GameBlock(rl, R.drawable.gameblock, tmpPos.getX(), tmpPos.getY(),
                        getApplicationContext(), time, gameBoard, activity, positions));
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position tmpPos =getRandPos();
                moveAll("UP");
                blocks.add(new GameBlock(rl, R.drawable.gameblock, tmpPos.getX(), tmpPos.getY(),
                        getApplicationContext(), time, gameBoard, activity, positions));
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position tmpPos =getRandPos();
                moveAll("DOWN");
                blocks.add(new GameBlock(rl, R.drawable.gameblock, tmpPos.getX(), tmpPos.getY(),
                        getApplicationContext(), time, gameBoard, activity, positions));
            }
        });


        //Registers and assigns listeners and managers to the linear accelerometer
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        TextView accelSensorLbl = makeLabel(rl, "", 0, 350);
        Sensor accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accel = new MySensorEventListener(accelSensorLbl, accelData, dirLbl, gameBlock);
        sensorManager.registerListener(accel, accelSensor, SensorManager.SENSOR_DELAY_GAME);

    }
    private void assign() {
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                positions[i][j] = new Position((blocks.elementAt(0).getWidth() * SCALE * j),(blocks.elementAt(0).getHeight()*SCALE*i), false);
                Log.d("Position","X: "+positions[i][j].getX()+ "Y: "+positions[i][j].getY());
            }
        }
    }



    //A method that creates a label with specific text and adds it to a specified layout
    private TextView makeLabel(RelativeLayout rl, String text, float x, float y) {
        TextView tv1 = new TextView(getApplicationContext());
        tv1.setText(text);
        tv1.setX(x);
        tv1.setY(y);
        rl.addView(tv1);
        return tv1;
    }

    private ImageView makeLabel(RelativeLayout rl, int img, float x, float y) {
        ImageView iv = new ImageView(getApplicationContext());
        iv.setImageResource(img);
        iv.setY(y);
        iv.setX(x);
        rl.addView(iv);
        return iv;
    }
    private void moveAll(String dir){
        for (GameBlock e : blocks){
            e.move(dir);
        }
    }
    public Position getRandPos(){
        Position tmpPos=positions[rnd.nextInt(4)][rnd.nextInt(4)];
        if (tmpPos.isOccupied()){
            return getRandPos();
        }else {
            return tmpPos;
        }
    }





}







