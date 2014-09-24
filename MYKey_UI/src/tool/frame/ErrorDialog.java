package tool.frame;

import javax.swing.JOptionPane;

public class ErrorDialog extends JOptionPane{
	public static void warning(String message){
		showMessageDialog(null, message,
				"warning", JOptionPane.WARNING_MESSAGE);
	}
	public static void error(String message){
		showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
