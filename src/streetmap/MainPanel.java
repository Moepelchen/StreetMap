package streetmap;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/20/11
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainPanel extends JFrame
{
	public MainPanel(SSGlobals globals){
		this.setLayout(new BorderLayout(5, 5));
		this.getContentPane().add(new Map(globals));

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().add(new StreetPanel(globals),BorderLayout.PAGE_END);
		this.pack();
		this.setVisible(true);
	}


	public static void main(String[] args) {
        SSGlobals globals = null;
        try {
            globals = new SSGlobals();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
      MainPanel main = new MainPanel(globals);
    }


}
