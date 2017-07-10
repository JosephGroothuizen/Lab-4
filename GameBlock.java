package uwaterloo.ca.lab04_202_02;


import android.content.Context;
import android.widget.ImageView;

import static uwaterloo.ca.lab04_202_02.GameLoopTask.gameDirection.NO_MOVEMENT;


public class GameBlock extends android.support.v7.widget.AppCompatImageView {

    private final float IMAGE_SCALE = .77f;
    //stores the blocks location
    private int myCoordX;
    private int myCoordY;
    private GameLoopTask.gameDirection myDir= NO_MOVEMENT;

    //velocity and acceleration for animations
    private int velocity=0;
    private final int ACCELERATION=7;

    //holds the blocks direction and sets it to not moving initially
    //private GameLoopTask.gameDirection myDir= NO_MOVEMENT;


    //The four boundary Coordinates
    private final int LX = -33; //Left X boundary
    private final int RX = 774; //Right X boundary
    private final int UY = -33; //Up Y boundary
    private final int DY = 774; //Down Y boundary


    public GameBlock(Context myContext, int coordX, int coordY) {

        super(myContext);//Used to create the gameblock

        //Store coordX and coordY into the private fields
        myCoordX = coordX;
        myCoordY = coordY;

        //Call the ImageView Constructor
        ImageView myBlock = new ImageView(myContext);

        //Set up the image when the GameBlock constructor is called
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);

        //Set up which point the block will start at
        this.setX(myCoordX);
        this.setY(myCoordY);

    }

    //Method that takes in a game direction input, and save the game direction
    //to the corresponding local private field.


    //Add stuff soon
    public void setDestination(){}

    public int getLocationX(){
        return (myCoordX);
    }
    public int getLocationY(){
        return (myCoordY);
    }



    //a function to be used to check if the velocity is zero to decide if block will move
    public int getVelocity(){

        return velocity;
    }

    public void setBlockDirection(GameLoopTask.gameDirection newDirection){
        myDir=newDirection;
    }


    //function to move the block's location
    public void move() {

            //Uses a switch statement to figure out what direction the block will move in
            switch (myDir) {
                case UP:
                    //UY DY RX LX will be changed in the future!!!!!!!!!!




                    //before the block hits the boundary animates the block accelerating
                    if (myCoordY > UY) {
                        myCoordY += -1 * velocity;
                        this.setY(myCoordY);
                        velocity += ACCELERATION;
                    }

                    //Once the block has hit the edge has it stop moving places it percisely at the edge and resets all changed values
                    else {
                        this.setY(UY);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                case DOWN:
                    if (myCoordY < DY) {
                        myCoordY += 1 * velocity;
                        this.setY(myCoordY);
                        velocity += ACCELERATION;
                    }
                    else {
                        this.setY(DY);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                case RIGHT:
                    if (myCoordX < RX) {
                        myCoordX += 1 * velocity;
                        this.setX(myCoordX);
                        velocity += ACCELERATION;
                    }
                    else {
                        this.setX(RX);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                case LEFT:
                    if (myCoordX > LX) {
                        myCoordX += -1 * velocity;
                        this.setX(myCoordX);
                        velocity += ACCELERATION;
                    }
                    else {
                        this.setX(LX);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                //If not moving do nothing
                default:
                    break;
            }
    }
}