package streetmap.handler.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ulrichtewes on 23.11.13.
 */
public abstract class IntegerFieldListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JTextField field = (JTextField) e.getSource();
        int toSet = Integer.parseInt(field.getText());
        setInteger(toSet);
    }

    protected abstract void setInteger(Integer toSet);
}
