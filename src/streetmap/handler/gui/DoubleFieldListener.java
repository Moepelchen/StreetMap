package streetmap.handler.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ulrichtewes on 23.11.13.
 */
public abstract class DoubleFieldListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JTextField field = (JTextField) e.getSource();
        double toSet = Double.parseDouble(field.getText());
        setDouble(toSet);
    }

    protected abstract void setDouble(double toSet);
}
