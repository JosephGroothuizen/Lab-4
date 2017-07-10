package uwaterloo.ca.lab04_202_02;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;


public class accelSensorEventListener implements SensorEventListener {//Creates the class to handle the accelerometer and all values associated
    TextView currentStateOuput;//outputs the state to show

    //creates a 2 dimensional array to store the smmothed values
    private float[] smoothedValues = new float[3];
    //stores a GameLoop
    private GameLoopTask myGL;

    public float[][] prevOneHundred= new float[100][3];//stores the previous 100 readings to be outputted to csv

    public accelSensorEventListener(TextView inputTextView, GameLoopTask GL_IN){//constructor
        currentStateOuput=inputTextView;
        myGL=GL_IN;
    }
    public void onAccuracyChanged(Sensor s, int i){}// required function for the sensor event listener


    public void onSensorChanged(SensorEvent se){//function for when a reading of the acceleromter values change

        //smoothes values of the accelerometer
        smoothedValues[0] += (se.values[0]-smoothedValues[0])/5;
        smoothedValues[1] += (se.values[1]-smoothedValues[1])/5;
        smoothedValues[2] += (se.values[2]-smoothedValues[2])/5;

        //updates the previous 100 values to include the current values and to exclude the 101st values
        for (int j=0; j<3; j++){
            for (int i = 99; i>=0; i-- ){
                if (i > 0) {
                    prevOneHundred[i][j] = prevOneHundred[i - 1][j];
                } else {
                    prevOneHundred[0][j] = smoothedValues[j];
                }
            }
        }

        //checks current state and creates appropriate text for it and set the game blocks movement direction to that
        switch (stateMachine.getCurrentState()) {

            case UP:
                currentStateOuput.setText("UP");
                myGL.setDirection(GameLoopTask.gameDirection.UP);
                break;

            case DOWN:
                currentStateOuput.setText("DOWN");
                myGL.setDirection(GameLoopTask.gameDirection.DOWN);
                break;

            case LEFT:
                currentStateOuput.setText("LEFT");
                myGL.setDirection(GameLoopTask.gameDirection.LEFT);
                break;

            case RIGHT:
                currentStateOuput.setText("RIGHT");
                myGL.setDirection(GameLoopTask.gameDirection.RIGHT);
                break;
            case WAITING:
                myGL.setDirection(GameLoopTask.gameDirection.NO_MOVEMENT);
            default:
                break;
        }


        stateMachine.checkChange();//checks to see if the state should change

        //updates the counter to check if gesture happens within reason able time
        stateMachine.counter++;

    }
}
