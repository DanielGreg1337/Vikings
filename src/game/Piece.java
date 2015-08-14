package game;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import java.io.File;

/**
 * Created by bob on 7/08/15.
 */
public class Piece {
    public final int WHITE = 0;
    public final int BLACK = 1;
    public final int KING = 2;
    public final int VACANT = 3;
    int type = VACANT;
    Image whileMoving;
    Image whileStationary;
    String whileMovingPath;
    String whileStationaryPath;

    public Piece(int type, String movingAnimationPath, String stationaryAnimationPath)
    {
        SpriteSheet movingSheet;
        SpriteSheet stationarySheet;
        try {
            // leave animations as NULL so we get an error throughout the program is a vacant cell is drawn.
            if (type == VACANT)
            {
                this.type = VACANT;
                return;
            }
            File f= new File(movingAnimationPath);
            String paths = f.getCanonicalPath();
            //movingSheet = new SpriteSheet(f.getCanonicalPath(), 10, 10);
            //stationarySheet = new SpriteSheet(stationaryAnimationPath, 10, 10);
            this.type = type;
            //this.whileMoving = new Animation(movingSheet, 0, 0, 1, 1, false, 100, true);
            //this.whileStationary = new Animation(stationarySheet, 0, 0, 1, 1, true, 100, true);
            whileMovingPath = movingAnimationPath;
            whileStationaryPath = stationaryAnimationPath;
            this.whileStationary = new Image(whileStationaryPath);
            this.whileMoving = new Image(whileMovingPath);
        }
        catch (Exception e)
        {
            System.out.print("Error loading piece sheet.");
            return;
        }
    }
    @Override
    public String toString()
    {
        return String.valueOf(type) + ":" + whileMovingPath + ":" + whileStationaryPath;
    }
}
