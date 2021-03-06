
package uwaterloo.ca.lab04_202_02;

import android.util.Log;

/**
 * Created by Joseph Grooth on 2017-05-31.
 */

//creates state machine
public class stateMachine {
    final static int initialXThresh = 6;
    final static int finalXThresh = 3;
    final static int initialYThresh = 6;
    final static int finalYThresh = 3;
    final static int xDirection = 0;
    final static int yDirection = 1;
    //creates and enumerates the states
    enum states{LR, LL, LU,LD, RIGHT, LEFT, UP, DOWN, WAITING};

    //creates a state variable and intializes it to waiting
    static states currentState= states.WAITING;

    //creates an accelerometer sensor listener that is analyzed for gestures
    static public accelSensorEventListener accel=null;

    //Creates a counter to see how long the state machine has been in the waiting state
    static int waitingTime=0;

    //creates counter that waits for the second part of a gesture
    static int counter=0;

    //returns the current state since it is private and can't be accessed outside the class
    static states getCurrentState(){
        return (currentState);
    }

    static boolean isMax(int direction,int maxThreshHold){ //0 is x, 1 is y
        if(Math.abs(accel.prevOneHundred[1][direction]) > maxThreshHold ){
            Log.d("TAG", "Whe are here in the " + direction + " direction");
            return true; //means the point has passed the threshhold value
        }
        else
            return false;
    }

    static boolean isMin(int direction,int minThreshHold){ //0 is x, 1 is y
        if(Math.abs(accel.prevOneHundred[1][direction]) > minThreshHold ){
            return true; //means the point has passed the threshhold value
        }
        else
            return false;
    }

    static boolean isRising(int direction){ //checks that point 1 is below point 0 but above point 2 to see a negative slope ***in theory but in practice this actually has to be the rising condition?
        if((accel.prevOneHundred[1][direction]<accel.prevOneHundred[0][direction]) &&(accel.prevOneHundred[1][direction]>accel.prevOneHundred[2][direction])){
            return true;
        }
        else
            return false;
    }

    static boolean isFalling(int direction){ //checks that point 1 is above point 0 but below point 2 for a positive slope
        if((accel.prevOneHundred[1][direction]>accel.prevOneHundred[0][direction]) &&(accel.prevOneHundred[1][direction]<accel.prevOneHundred[2][direction])){
            return true;
        }
        else
            return false;
    }

    static boolean atPeak(int direction){
        if((Math.abs(accel.prevOneHundred[1][direction])> (Math.abs(accel.prevOneHundred[0][direction]))) &&((Math.abs(accel.prevOneHundred[1][direction])> Math.abs(accel.prevOneHundred[2][direction])))){
            return true;
        }
        else
            return false;
    }

    //Used to update the state of the state machine
    static states checkChange(){

        if(currentState!=states.WAITING)
            Log.d("TAG", "Current state is " + currentState);
        //checks to see if the state is waiting and if so updates the counter that records how long the apps been in the waiting state
        if(currentState==states.WAITING){
            waitingTime++;
        }
        // if not waiting sets the waiting counter back to zero
        else{
            waitingTime=0;
        }

        //Checks to see if the app has been waiting for the rest of a gesture for too long and if so sets it back to the netral waiting state
        if(counter>15){
            currentState=states.WAITING;
            counter=0;
        }

        //checks for a min values "above" a threshold and changes the state to look for the rest of the gesture. Lets call this part 1
        else if ((currentState==states.WAITING) &&(isFalling(xDirection))&&(isMax(xDirection,initialXThresh))){
            currentState=states.LL;
            counter=0;
        }
        //check to see if looking for a specific gesture and if it is and meets gesture changes state to the gesture. Lets call this part 2
        else if ((currentState == states.LL) && (atPeak(xDirection)) &&(isMin(xDirection,finalXThresh))){
            currentState=states.LEFT;
        }
        //Like part 1
        else if ((currentState==states.WAITING) && (isRising(xDirection) &&(isMax(0,initialXThresh)))){
            currentState=states.LR;
            counter=0;
        }
        //Like part 2
        else if ((currentState==states.LR)&&(atPeak(xDirection))&&(isMin(xDirection,finalXThresh))){
            currentState=states.RIGHT;
        }
        //Like part 1
        else if((currentState==states.WAITING)&&(isMax(1,initialYThresh))&&(isRising(yDirection))){
            currentState=states.LU;
            counter=0;
        }
        //Like part 2
        else if((currentState==states.LU)&&(isMin(1,finalYThresh))&&(atPeak(yDirection))){
            currentState=states.UP;
        }
        //Like part 1
        else if((currentState==states.WAITING)&&(isMax(1,initialYThresh))&& (isFalling(yDirection))){
            currentState=states.LD;
            counter=0;
        }
        //Like part 2
        else if((currentState==states.LD)&&(isMin(1,finalYThresh))&& (atPeak(yDirection))){
            currentState=states.DOWN;
        }
        ////////////
        return currentState;
    }

}