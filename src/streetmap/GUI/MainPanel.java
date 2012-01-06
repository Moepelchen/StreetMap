package streetmap.GUI;

import streetmap.Map;
import streetmap.SSGlobals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/20/11
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainPanel extends JFrame implements MouseListener
{
	public MainPanel(SSGlobals globals)
	{
		this.setLayout(new BorderLayout(5, 5));
		this.getContentPane().add(new Map(globals));

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().add(new StreetPanel(globals), BorderLayout.PAGE_END);
		this.getContentPane().add(new MenuPanel(globals), BorderLayout.LINE_END);
		this.pack();
		this.addMouseListener(this);
		this.setVisible(true);

	}


	public static void main(String[] args)
	{
		SSGlobals globals = null;
		try
		{
			globals = new SSGlobals();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		MainPanel main = new MainPanel(globals);
	}


	@Override
	public void mouseClicked(MouseEvent e)
	{
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
