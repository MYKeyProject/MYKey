package tool.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import tool.menubar.MYKeyMenubar;
import tool.panel.display.CompositionPanel;
import tool.panel.display.DevicePanel;
import tool.panel.display.ScreenPanel;
import tool.panel.display.TabablePane;
import tool.panel.keysetting.DesktopPane;
import tool.tempUse.SequenceStart;

public class MFrame extends JFrame {
	Container c;

	public MFrame() {
		final int height = 1000;
		
		this.setJMenuBar(new MYKeyMenubar());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		c = this.getContentPane();
		this.setLayout(new BorderLayout());

		CompositionPanel compositionPanel = new CompositionPanel(true), shiftComposotionPanel = new CompositionPanel(false);
		compositionPanel.setOtherCompositionPanel(shiftComposotionPanel);
		shiftComposotionPanel.setOtherCompositionPanel(compositionPanel);
		
		ScreenPanel showScreenPanel = new ScreenPanel();
		DevicePanel devicePanel = new DevicePanel(compositionPanel,
				showScreenPanel);
		showScreenPanel = new ScreenPanel();
		DevicePanel shiftDevicePanel = new DevicePanel(shiftComposotionPanel,
				showScreenPanel);

		TabablePane tp = new TabablePane();
		tp.setPreferredSize(new Dimension(height / 2, height * 9 / 10));

		tp.add("Normal", devicePanel);
		tp.add("Shift", shiftDevicePanel);
		
		DesktopPane dp = new DesktopPane();
		dp.addPhonemeSettingPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tp,
				dp){
		    private final int location = height/2;
		    {
		        setDividerLocation( location );
		    }
		    @Override
		    public int getDividerLocation() {
		        return location ;
		    }
		    @Override
		    public int getLastDividerLocation() {
		        return location ;
		    }
		};
		

		this.add(splitPane, "Center");
		
		
		
		JLabel lab = new JLabel();
		lab.setPreferredSize(new Dimension(height / 2, height / 50));
		lab.setBackground(Color.lightGray);
		this.add(lab, "South");
		this.setSize(height * 13 / 10, height);
		this.setVisible(true);
new SequenceStart();
		
	}
}
