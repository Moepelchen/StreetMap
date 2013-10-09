package streetmap.handler.gui;

import streetmap.SSGlobals;
import streetmap.saveandload.Saver;

import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class SaveClickHandler extends ClickHandler
{

	public SaveClickHandler(SSGlobals globals)
	{
		super(globals);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		new Saver().save(fGlobals);
	}


}
