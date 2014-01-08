package streetmap;

import org.lwjgl.input.Keyboard;

/**
 * Created by ulrichtewes on 19.12.13.
 */
public class KeyHandler
{
    private final SSGlobals fGlobals;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;
    private boolean wPressed;

    public KeyHandler(SSGlobals fGlobals)
    {
        this.fGlobals = fGlobals;
    }


    public void handleInput()
    {
        Player player = fGlobals.getGame().getPlayer();
        while (Keyboard.next())
        {

            if (Keyboard.getEventKeyState())
            {
                if (Keyboard.getEventKey() == Keyboard.KEY_A)
                {
                    aPressed = true;

                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S)
                {
                    sPressed = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D)
                {
                    dPressed = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_W)
                {
                    wPressed = true;
                }
            } else
            {
                if (Keyboard.getEventKey() == Keyboard.KEY_A)
                {
                    aPressed = false;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S)
                {
                    sPressed = false;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D)
                {
                    dPressed = false;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_W)
                {
                    wPressed = false;
                }
            }
        }

        double tileWidth = fGlobals.getMap().getTileWidth()/4;
        if (aPressed)
        {
            player.updateX((float) tileWidth);

        }
        if (wPressed)
        {
            player.updateY((float) tileWidth);

        }
        if (dPressed)
        {
            player.updateX(-(float) tileWidth);

        }
        if (sPressed)
        {
            player.updateY(-(float) tileWidth);

        }
    }
}
