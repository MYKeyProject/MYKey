package tool.panel.display;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class ScreenPanel extends JPanel{
	Image img = new ImageIcon(Paths.get("").toAbsolutePath().toString() + File.separator + "res" + File.separator + "img" + File.separator + "screen.png").getImage();
	public ScreenPanel(){
		this.setBackground(Color.black);
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(img,0,this.getHeight()-this.getParent().getHeight(),CompositionPanel.getOneCellWidth()*CompositionPanel.getColumn(),this.getParent().getHeight(),this);
	}
}
