package game;
import org.newdawn.slick.Image;

/**
 * Created by bob on 7/08/15.
 */
public class MapCell {
    public final int EMPTY = 0;
    public final int CASTLE = 1;
    public final int NOT_TRAVERSABLE = 2;
    public final int HALL = 3;
    int type = EMPTY;
    Piece pieceOnCell;
    Image cellBackground;

    public MapCell(int type, Piece piece, String cellBackground)
    {
        this.type = type;
        this.pieceOnCell = piece;
        try {
            this.cellBackground = new Image(cellBackground);
        }
        catch (Exception e)
        {
            System.out.print("Error: MapCell background could not be loaded.\n");
        }
    }
    @Override
    public String toString()
    {
        return String.valueOf(type) + ":" + pieceOnCell.toString() + ":" + cellBackground.getResourceReference();
    }
}