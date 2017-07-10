package uwaterloo.ca.lab4_203_06;

/**
 * Created by Cristiano Chelotti on 7/9/2017.
 */

public class Position {
    float xPos;
    float yPos;
    int blocknum_x;
    int blocknum_y;
    boolean occupied;

    public Position(float x, float y, int blocknum_x, int blocknum_y, boolean occupied) {
        xPos = x;
        yPos = y;
        this.blocknum_x = blocknum_x;
        this.blocknum_y = blocknum_y;
        this.occupied = occupied;
    }

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public int getBlocknum_x(){return blocknum_x;};

    public int getBlocknum_y(){return blocknum_y;};

    public void setBlocknum_x(int x){
        blocknum_x = x;
    }
    public void setBlocknum_y(int y){
        blocknum_y = y;
    }
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}
