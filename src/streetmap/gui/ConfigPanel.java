package streetmap.gui;

import streetmap.SSGlobals;
import streetmap.handler.gui.BooleanHandler;
import streetmap.handler.gui.DoubleFieldListener;
import streetmap.handler.gui.IntegerFieldListener;
import streetmap.interfaces.config.IChangeableConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 02.08.12
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
public class ConfigPanel extends JPanel
{
    private GridLayout fLayout;
    private JTextField fMaxNumberCarsField;
    private JTextField fHeatMapModifierField;
    private JCheckBox fShowHeatMapBox;

    public ConfigPanel(final IChangeableConfig config)
    {
        fLayout = new GridLayout(0, 2);
        this.setLayout(fLayout);
        addMaximumCarsField(config);
        addHeatMapModifierField(config);
        addShowHeatMapSetting(config);
        addShowLanesSetting(config);
        this.setSize(400, 300);
        this.setVisible(true);

    }

    private void addShowHeatMapSetting(final IChangeableConfig config)
    {
        fShowHeatMapBox = this.addCheckBox("Show Heat Map", config.isShowHeatMap(), new BooleanHandler()
        {
            @Override
            protected void setBoolean(boolean toSet)
            {
                config.setShowHeatMap(toSet);
            }
        });

    }

    private void addShowLanesSetting(final IChangeableConfig config)
    {
        fShowHeatMapBox = this.addCheckBox("Show Lanes", config.isDrawLanes(), new BooleanHandler()
        {
            @Override
            protected void setBoolean(boolean toSet)
            {
                config.setDrawLanes(toSet);
            }
        });

    }

    private void addHeatMapModifierField(final IChangeableConfig config)
    {
        fHeatMapModifierField = addTextField("Heatmap Modifier", "" + config.getHeatMapModifier(), new DoubleFieldListener()
        {
            @Override
            protected void setDouble(double toSet)
            {
                config.setHeatMapModifier(toSet);
            }
        });
    }


    private void addMaximumCarsField(final IChangeableConfig config)
    {
        int maximumNumOfCars = config.getMaximumNumOfCars();

        fMaxNumberCarsField = addTextField("Maximum Cars", maximumNumOfCars + "", new IntegerFieldListener()
        {
            @Override
            protected void setInteger(Integer toSet)
            {
                config.setMaximumNumOfCars(toSet);
            }
        });
    }

    public JTextField addTextField(String label, String value, ActionListener listener)
    {

        JTextField numberOfCarsField = new JTextField(value);
        JLabel numberOfCarsLabel = new JLabel(label);
        numberOfCarsField.addActionListener(listener);

        this.add(numberOfCarsLabel);
        this.add(numberOfCarsField);
        return numberOfCarsField;
    }

    private JCheckBox addCheckBox(String label, boolean isChecked, ActionListener listener)
    {
        JCheckBox box = new JCheckBox(label, isChecked);
        box.addActionListener(listener);
        box.setVisible(true);
        this.add(box);
        return box;
    }

    public Component addLabel(JLabel label)
    {
        fLayout.setRows(fLayout.getRows() + 1);
        return super.add(label);
    }

    public void update(SSGlobals globals)
    {
        fHeatMapModifierField.setText(globals.getConfig().getHeatMapModifier()+"");
        fMaxNumberCarsField.setText(globals.getConfig().getMaximumNumOfCars()+"");
        fShowHeatMapBox.setSelected(globals.getConfig().isShowHeatMap());

    }
}
