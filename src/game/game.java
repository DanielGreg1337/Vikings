package game;
import org.newdawn.slick.*;

/**
 * Created by bob on 6/08/15.
 */
public class game extends BasicGame {

    Animation sheetRunning;
    anim dude;
    Map map;
    public game()
    {
        super("Game");
    }
    public static void main(String[] args)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new game());
            app.setDisplayMode(500, 500, false);
            app.start();
        }
        catch (Exception e)
        {
            System.out.print("Error in starting.\n");
        }
    }
    @Override
    public void init(GameContainer container) throws SlickException
    {
        try {
            SpriteSheet sheet = new SpriteSheet("fig06.jpg", 162, 220);
            sheetRunning = new Animation();
            for (int i = 0; i < 2; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    sheetRunning.addFrame(sheet.getSprite(j, i), 150);
                }
            }
            dude = new anim(sheetRunning);
            dude.x = 10;
            dude.y = 20;
            map = new Map("data/defaultMap");
        }
        catch (Exception e)
        {
            System.out.print("Error loading sheet.\n");
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
    }

    public final int WHITE = 0;
    public final int BLACK = 1;
    public final int KING = 2;
    public final int VACANT = 3;
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        // use 0,0 since it must exist and all sizes are the same.

        float cellbackgroundSize = map.get(0, 0).cellBackground.getWidth();
        for (int x = 0; x < map.mapSize; x++)
        {
            for (int y = 0; y < map.mapSize; y++)
            {
                map.get(x, y).cellBackground.draw(((float)x * 60), ((float)y * 60));
                if (map.get(x, y).pieceOnCell.type != VACANT)
                {
                    map.get(x, y).pieceOnCell.whileStationary.draw(((float)x * 60) + 1, ((float)y * 60) + 1);
                }
            }
        }
        dude.animation.draw(dude.x, dude.y);
    }
    @Override
    public void mousePressed (int button, int x, int y)
    {
        System.out.print("Mouse Pressed.\n");
        dude.x = (float)x;
        dude.y = (float)y;
        map.saveMap("data/defaultMap");
        if (map.isPointOnMap(x, y))
        {
            map.handleMapMouseClick(x, y, button);
        }
        else
        {
            //On user interface.
        }
    }
    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
        dude.x = (float)newx;
        dude.y = (float)newy;
    }
    @Override
    public void mouseReleased(int button, int x, int y)
    {

    }
    @Override
    public void mouseWheelMoved(int change)
    {

    }
    @Override
    public void keyPressed(int key, char c)
    {
        map.handleMapKeyPress(key);
    }
    @Override
    public void keyReleased(int key, char c)
    {

    }
}
