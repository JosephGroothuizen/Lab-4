package uwaterloo.ca.lab04_202_02;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Timer;


public class Lab04 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab04);
        final int GAMEBOARD_DIMENSION= 1080;//sets gameboard dimensions
        Timer mytimer=new Timer();//creates main gameloop timer

        //sets up layout and background as the board
        RelativeLayout r1=(RelativeLayout) findViewById(R.id.layout1);//Creating linear Layout
        r1.getLayoutParams().width = GAMEBOARD_DIMENSION;  //gameboard size
        r1.getLayoutParams().height = GAMEBOARD_DIMENSION;
        r1.setBackgroundResource(R.drawable.gameboard);




        //creates text view to show the gesture and assigns the characteristics such as colour text size and initial text
        TextView currentStateOuput= new TextView(getApplicationContext());
        r1.addView(currentStateOuput);
        currentStateOuput.setTextColor(Color.BLACK);
        currentStateOuput.setTextSize(75);
        currentStateOuput.setText("NO INPUT");





        //creates gameloop and timer to run game loop
        GameLoopTask gameloop= new GameLoopTask(this,r1,getApplicationContext());
        mytimer.schedule(gameloop,16,16);

        //sets up accelerometer handler
        SensorManager sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);//Creating sensor manager
        Sensor accelSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        final accelSensorEventListener accel=new accelSensorEventListener(currentStateOuput, gameloop);
        sensorManager.registerListener(accel,accelSensor, SensorManager.SENSOR_DELAY_GAME);

        //Assigns the accelerometer within the stateMachine class to be the accelerometer handler created above
        stateMachine.accel=accel;
    }
}
