package uwaterloo.ca.lab04_202_02;

//package uwaterloo.ca.lab03_202_02;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

import static uwaterloo.ca.lab04_202_02.stateMachine.states.WAITING;


/**
 * Created by steve on 2017-06-25.
 */

public class GameLoopTask extends TimerTask {
    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private GameBlock myGameBlock;
    boolean somethingMoved=false;

    //contains linked list of all gameblocks
    public LinkedList<GameBlock> blockList;
    //private Random gen = new Random();

    //Constants
    public static int leftBoundary = -33; //Left X boundary
    public static int rightBoundary = 774; //Right X boundary
    public static int upBoundary = -33; //Up Y boundary
    public static int downBoundary = 774; //Down Y boundary


    //enumerates the possible directions for the gameblock
    public enum gameDirection {LEFT, RIGHT, UP, DOWN, NO_MOVEMENT}

    //creates a variable to store the gameblocks current direction
    public static gameDirection currentDirection = gameDirection.NO_MOVEMENT;
    String Direction;

    //constructor
    GameLoopTask(Activity Activity, RelativeLayout RL, Context Context) {
        myActivity = Activity;
        myRL = RL;
        myContext = Context;
        blockList = new LinkedList();

        //Call the CreateBlock Method
        createBlock(-33, -33);
        createBlock(-33, 774);
        createBlock(774, 774);
        createBlock(774, -33);

    }

    private void createBlock(int startX, int startY) {

        GameBlock newBlock = new GameBlock(myContext, startX, startY); //Or any (x,y) of your choice

        //adds the game block to the layout
        myRL.addView(newBlock);
        myGameBlock = newBlock;
        blockList.add(myGameBlock); //adds the new gameBlock to the list

    }


    //method to create a game block
    private void createBlock() {
        int startX = (int) (Math.random()*4); //Generatig values from 0 to 3 to select which grid
        Log.d(""+startX, "createBlock: ");

        int startY = (int) (Math.random()*4);
        Log.d(""+startY, "createBlock: ");

        //creates the game block and initializes it to the top left corner
        int gridLength = upBoundary/4;

        GameBlock newBlock = new GameBlock(myContext, startX*gridLength, startY*gridLength); //Or a//ny (x,y) of your choice

        //adds the game block to the layout
        myRL.addView(newBlock);
        //myGameBlock = newBlock;
        blockList.add(newBlock); //adds the new gameBlock to the list

    }

    @Override
    public void run() {

        myActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.d("size","Size is "+ blockList.size());
                        for(int x=0;x<blockList.size();x++){ //iterates through every block to move
                            blockList.get(x).move();
                        }

                    }
                }
        );
    }

    //sets the blocks direction by taking in a direction and assigning it to the game block created
    public void setDirection(gameDirection newDirection) {
        //changes the dierection only if the blocks aren't moving
        Log.d("call", "set direction");
        currentDirection = newDirection;

        //Log.d("SomethingMoved Value", "It is "+somethingMoved);
        int counter=0;
        for(int x=0;x<blockList.size();x++){ //iterates through ever block
            if (blockList.get(x).getVelocity()==0){
                if (somethingMoved && counter<1 && stateMachine.getCurrentState()==WAITING){
                    createBlock();
                    somethingMoved=false;
                    counter++;
                }
                //Pass the direction into the GameBlock class setBlockDirection()
                //Log.d("Is Velocity", "Velocity "+blockList.get(x).getVelocity());
                blockList.get(x).setBlockDirection(newDirection);
                if (currentDirection != gameDirection.NO_MOVEMENT){
                    somethingMoved=true;
                }
            }

        }


    }

}
