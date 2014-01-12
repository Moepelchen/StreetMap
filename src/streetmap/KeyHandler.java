package streetmap;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import org.lwjgl.input.Keyboard;
import streetmap.gui.ILayerNames;
import streetmap.gui.IScreenNames;

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
	private boolean fDebugShown;

	public void handleInput()
	{
		Player player = fGlobals.getGame().getPlayer();
		while (Keyboard.next())
		{

			if (Keyboard.getEventKeyState())
			{
				handleMovementKeyPressed();
				if(Keyboard.getEventKey() == Keyboard.KEY_F3)
				{
					Nifty nifty = fGlobals.getGame().getNifty();
					if(!fDebugShown)
					{
						fDebugShown = true;
						setLayerVisibility(nifty,fDebugShown);
					}
					else
					{
                        fDebugShown = false;
                        setLayerVisibility(nifty,fDebugShown);
					}
				}

			}
			else
			{
				handleMovementKeyReleased();
			}
		}

		double tileWidth = fGlobals.getMap().getTileWidth() / 4;
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

    private void setLayerVisibility(Nifty nifty, boolean debugShown)
    {
        Screen current = nifty.getCurrentScreen();
        if(current.getScreenId().equals(IScreenNames.SCREEN_GAME))
        {
            current.findElementByName(ILayerNames.LAYER_DEBUG).setVisible(debugShown);
            nifty.update();
            nifty.render(false);

        }
    }

    protected void handleMovementKeyReleased()
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

	protected void handleMovementKeyPressed()
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
	}

	public KeyHandler(SSGlobals fGlobals)
	{
		this.fGlobals = fGlobals;
	}
}
