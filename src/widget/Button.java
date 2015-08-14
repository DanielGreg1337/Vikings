package widget;

/**
 * Created by OlymBeast on 12/08/2015.
 */

//Interface
//http://stackoverflow.com/questions/122407/whats-the-nearest-substitute-for-a-function-pointer-in-java

import org.newdawn.slick.Image;
import widget.Box;

//An interface to hold a function that can be changed
//The function 'dynamicFunc' will be called when the button is pressed
interface interButton
{
    void dynamicFunc ( Object argObj );
}

//A Button scheme is passed to a Button constructor to determine how the button will look
class ButtonScheme
{
    //The Text that is shown within the Button
    private String text;

    //The buttons dimensions
    Box rect;

    //The thickness of the buttons border ( in pixels )
    int thickness;

    //Ctor
    public ButtonScheme ( String aText, int x, int y, int w, int h, int thick )
    {
        text = aText;

        rect.x = x;
        rect.y = y;
        rect.w = w;
        rect.h = h;

        thickness = thick;
    }
}

//A button class, simply calls a function when it is clicked
public class Button
{
    //The text that will be displayed inside the button
    //private String text;

    //The Image that will show when the button isnt doing anything
    private Image defaultImage;

    //The Image that will be shown when the button is hovered over
    private Image hoverImage;

    //The Image that will be shown when the button is pressed
    private Image pressImage;

    //The function that will be called when the button is pressed
    interButton callFunc;

    //Ctor -> cF is the function that will be called when the button is pressed
    public Button ( interButton cF, ButtonScheme dI, ButtonScheme hI, ButtonScheme pI )
    {
        //Transfer Vars
        callFunc = cF;

        //Render the images

    }

    //This function will render the actual Button
    public void Render ()
    {

    }
}
