package streetmap.handler.gui;

import streetmap.SSGlobals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Abstract class for all Buttons and other clickable stuff
 */
public abstract class ClickHandler implements ActionListener
{
	protected SSGlobals fGlobals;

	public ClickHandler(SSGlobals globals)
	{
		this.fGlobals = globals;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
