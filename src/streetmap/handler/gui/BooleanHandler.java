package streetmap.handler.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ulrichtewes on 23.11.13.
 */
public abstract class BooleanHandler implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JCheckBox box = (JCheckBox) e.getSource();
        boolean toSet = box.isSelected();
        setBoolean(toSet);
    }

    protected abstract void setBoolean(boolean toSet);
}
