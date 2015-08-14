package game;

import org.newdawn.slick.Animation;

/**
 * Created by bob on 6/08/15.
 */
public class anim {
    Animation animation;
    float x = 0;
    float y = 0;

    public anim(Animation animation)
    {
        this.animation = animation;
    }
    public void setX(float x)
    {
        this.x = x;
    }
    public void setY(float y)
    {
        this.y = y;
    }
}
