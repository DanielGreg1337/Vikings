package game;
import javafx.scene.input.MouseButton;
import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import org.newdawn.slick.command.MouseButtonControl;

import java.io.*;

/**
 * Created by bob on 7/08/15.
 */
public class Map {
    globalError error;
    int mapSize = 7;
    MapCell[][] map = new MapCell[mapSize][mapSize];
    //map origin is position independent so the UI can be placed anywhere around it.
    // These coordinates set the map-relative origin.
    int x = 0;
    int y = 0;
    int width = mapSize  * 60;
    int height = mapSize * 60;
    int markedX = -1;
    int markedY = -1;

    public Map(String path)
    {
        loadMap(path);
    }
    //Path may be a template when starting a new map or load an existing saved map.
    //Map dimensions may only be a grid, the first line of the saved map contains "size=<Integer>"
    //to indicate size, allowing players to configure for different variants of the game.
    //Additional metadata holds the game-name, "name=<string>", time of last save "time=<date-output>"
    //
    public void loadMap(String path)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            int x = 0;
            int y = 0;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(":");
                if (parts[0].equals("cell"))
                {
                    MapCell newCell = new MapCell(Integer.parseInt(parts[1]),
                            new Piece(Integer.parseInt(parts[2]), parts[3], parts[4]), parts[5]);
                    map[x][y] = newCell;
                    x++;
                    if (x >= mapSize) {
                        x = 0;
                        y++;
                    }
                    if (y > mapSize)
                        System.out.print("Error: Too many rows in map.\n");
                }
            }
        }
        catch (Exception e)
        {
            System.out.print("Error occurred while attempting to load map.");
        }
    }
    public MapCell get(int x, int y)
    {
        return map[x][y];
    }
    public void saveMap(String path)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path)))
        {
            for (int x = 0; x < mapSize; x++)
            {
                for (int y = 0; y < mapSize; y++)
                    bw.write("cell:" + map[x][y].toString() + "\n");
            }
        }
        catch (Exception e)
        {
            System.out.print("Error: Saving map failed.\n");
        }
    }
    public boolean isPointOnMap(int x, int y)
    {
        if ((x > this.x && x < (this.width + this.x))
                && (y > this.y && y < (this.height + this.y)))
            return true;
        return false;
    }
    public void handleMapMouseClick(int x, int y, int button)
    {
        final int LEFT = 0;
        final int VACANT = 3;
        final int KING = 2;
        final int CASTLE = 1;
        final int EMPTY = 0;
        int cellX = x / 60;
        int cellY = y / 60;
        if (button == LEFT)
        {
            if (markedX == -1)
            {
                if (map[cellX][cellY].pieceOnCell.type != VACANT)
                {
                    map[cellX][cellY].cellBackground.setImageColor((float) 0.5, (float) 0.8, (float) 0.12);
                    markedX = cellX;
                    markedY = cellY;
                }
            }
            else
            {
                //Second left mouse click means make calculations for movement.
                //On movement error we leave the marked cell and display an error message.
                if (cellX == markedX && cellY == markedY)
                {
                    System.out.print("");
                    error = new globalError("Piece should be moved at least one place.", "Error: Cannot move to the same place. In handleMapMouseClick\n");
                    return;
                }
                if ((cellX == markedX || cellY == markedY) == false)
                {
                    error = new globalError("Piece can only move in straight lines.", "Error: Attempted to move diagonal. In handleMapMouseClick\n");
                    return;
                }
                //check that the moving piece does not collide with another piece or move over a cell that it should not.
                // additionally it is convient here to check if the king is over a castle and the game is won.
                if (cellX == markedX)
                {
                    if (cellY > markedY)
                    {
                        for (int i = markedY + 1; i <= cellY; i++)
                        {
                            if (map[markedX][i].pieceOnCell.type != VACANT || map[markedX][i].type != EMPTY)
                            {
                                if (map[markedX][markedY].pieceOnCell.type == KING && map[markedX][i].type == CASTLE)
                                    System.out.print("Game Over. White Wins.\n");
                                System.out.print("Movement Error.\n");
                                error = new globalError("A Piece cannot move through or land on another piece or move through the Hall", "Error: Collision with other piece. In handleMapMouseClick\n");
                                return;
                            }
                        }
                    }
                    else
                    {
                        for (int i = markedY - 1; i >= cellY; i--)
                        {
                            if (map[markedX][i].pieceOnCell.type != VACANT || map[markedX][i].type != EMPTY)
                            {
                                if (map[markedX][markedY].pieceOnCell.type == KING && map[markedX][i].type == CASTLE)
                                    System.out.print("Game Over. White Wins.\n");
                                System.out.print("Error: Cannot move on or across occupied cell.\n");
                                break;
                            }
                        }
                    }
                }
                if (cellY == markedY)
                {
                    if (cellX > markedX)
                    {
                        System.out.print("Going Right.\n");
                        for (int i = markedX + 1; i <= cellX; i++)
                        {
                            if (map[i][markedY].pieceOnCell.type != VACANT || map[markedX][i].type != EMPTY)
                            {
                                if (map[markedX][markedY].pieceOnCell.type == KING && map[i][markedY].type == CASTLE)
                                    System.out.print("Game Over. White Wins.\n");
                                System.out.print("Error: Cannot move on or across occupied cell.\n");
                                break;
                            }
                        }
                    }
                    else
                    {
                        System.out.print("Going Left.\n");
                        for (int i = markedX - 1; i >= cellX; i--)
                        {
                            if (map[i][markedY].pieceOnCell.type != VACANT || map[markedX][i].type != EMPTY)
                            {
                                if (map[markedX][markedY].pieceOnCell.type == KING && map[i][markedY].type == CASTLE)
                                    System.out.print("Game Over. White Wins.\n");
                                System.out.print("Error: Cannot move on or across occupied cell.\n");
                                break;
                            }
                        }
                    }
                }
                //at this point movement from marked cell to final cell is correct.
                // move to destination.
                map[cellX][cellY].pieceOnCell = map[markedX][markedY].pieceOnCell;
            }
        }
        else
            map[cellX][cellY].cellBackground.setImageColor(1,1,1);
    }
    public void handleMapKeyPress(int key)
    {
        if (key == 1 && markedX > -1)
        {
            //clear previously marked cell.
            map[markedX][markedY].cellBackground.setImageColor(1,1,1);
            markedX = -1;
            markedY = -1;
        }
    }
}
