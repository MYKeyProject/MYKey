package tool.panel.keysetting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import tool.key.KeyButton;
public class KeySettingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JPanel textInputPanel;
	JPanel imageInputPanel;
	JPanel optionPanel;

	ButtonGroup radioButtonGroup;
	JRadioButton textRadioButton;
	JRadioButton imageRadioButton;
	JCheckBox repeatCheckBox;

	JButton textInputButton;
	JButton imagePathButton;
	JButton deleteButton;

	JTextField imagePathTextField;
	JTextField textShowTextField;

	boolean isTextKey = true;
	boolean isRepeatableKey;
	KeyButton keyButton;
	KeySettingMainPanel parent;

	public KeySettingPanel(KeyButton keyButton, KeySettingMainPanel parent) {
		this.isTextKey = keyButton.isTextButton();
		this.setLayout(new GridLayout(3, 1));
		this.keyButton = keyButton;
		this.parent = parent;
		radioButtonGroup = new ButtonGroup();
		textInputPanelSetting();
		imageInputPanelSetting();
		optionPanelSetting(keyButton.isRepeatable());
		if (isTextKey) {
			textShowTextField.setText(keyButton.getLabelName());
			textRadioButton.setSelected(true);
		} else {
			imagePathTextField.setText(keyButton.getImagePath());
			imageRadioButton.setSelected(true);
		}
		selectRadioButton();
	}

	public void textInputPanelSetting() {
		textInputPanel = new JPanel();
		textInputPanel.setLayout(new BorderLayout());
		textInputPanel.setBorder(new TitledBorder("선택 1"));
		textInputPanel.setOpaque(false);
		this.add(textInputPanel);

		textRadioButton = new JRadioButton("문자가 보이는 키", true);
		textRadioButton.setOpaque(false);
		textRadioButton.addItemListener(new RadioButtonListener());

		textInputPanel.add(textRadioButton, "West");
		radioButtonGroup.add(textRadioButton);

		textShowTextField = new JTextField();
		textShowTextField.setEnabled(false);
		textInputPanel.add(textShowTextField);

		textInputButton = new JButton("문자 입력");
		textInputButton.setBackground(Color.LIGHT_GRAY);
		textInputButton.addActionListener(new InputTextButtonListener());
		textInputPanel.add(textInputButton, "East");
	}

	public void imageInputPanelSetting() {
		imageInputPanel = new JPanel();
		imageInputPanel.setLayout(new BorderLayout());
		imageInputPanel.setBorder(new TitledBorder("선택 2"));
		imageInputPanel.setOpaque(false);
		this.add(imageInputPanel);

		imageRadioButton = new JRadioButton("그림이 보이는 키");
		imageRadioButton.setOpaque(false);
		imageRadioButton.addItemListener(new RadioButtonListener());
		imageInputPanel.add(imageRadioButton, "West");
		radioButtonGroup.add(imageRadioButton);

		imagePathTextField = new JTextField();
		imagePathTextField.setEnabled(false);
		imageInputPanel.add(imagePathTextField, "Center");

		imagePathButton = new JButton("파일 찾기");
		imagePathButton.setEnabled(false);
		imagePathButton.setBackground(Color.LIGHT_GRAY);
		imagePathButton.addActionListener(new FilePathButtonListener());
		imageInputPanel.add(imagePathButton, "East");
	}

	public void optionPanelSetting(boolean isRepeatable) {
		setRepeatable(isRepeatable);
		optionPanel = new JPanel();
		optionPanel.setLayout(new BorderLayout());
		optionPanel.setBorder(new TitledBorder("선택 3"));
		optionPanel.setOpaque(false);
		this.add(optionPanel);

		repeatCheckBox = new JCheckBox("꾸욱 누르면 연속 입력",isRepeatable);
		repeatCheckBox.setOpaque(false);
		repeatCheckBox.addItemListener(new RepeatButtonListener());
		optionPanel.add(repeatCheckBox);

		deleteButton = new JButton("삭제 하기");
		deleteButton.setBackground(Color.LIGHT_GRAY);
		deleteButton.addActionListener(new KeyDeleteButtonListener());
		optionPanel.add(deleteButton, "East");
	}

	public void setImagePath(String path) {
		imagePathTextField.setText(path);
		isTextKey = false;
	}

	public void setTextLabel(String label) {
		textShowTextField.setText(label);
		isTextKey = true;
	}

	public void setRepeatable(boolean bool) {
		this.isRepeatableKey = bool;
	}

	public boolean isRepeatable() {
		return isRepeatableKey;
	}

	public boolean isTextKey() {
		return isTextKey;
	}

	public String getLabel() {
		return textShowTextField.getText();
	}

	public String getImagePath() {
		return imagePathTextField.getText();
	}

	public void selectRadioButton() {
		if (textRadioButton.isSelected()) {
			imagePathButton.setEnabled(false);
			textInputButton.setEnabled(true);
			isTextKey = true;
		} else if (imageRadioButton.isSelected()) {
			textInputButton.setEnabled(false);
			imagePathButton.setEnabled(true);
			isTextKey = false;
		}
	}

	public void removeKey() {
		keyButton.remove();
		parent.exit();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	class RepeatButtonListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				setRepeatable(false);
			} else if (e.getStateChange() == ItemEvent.SELECTED) {
				setRepeatable(true);
			}
		}
	}

	class RadioButtonListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.DESELECTED)
				return;
			selectRadioButton();
		}
	}

	class KeyDeleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null,
					"Can't Undo....Really Remove?", "Remove",
					JOptionPane.YES_NO_OPTION);

			switch (result) {
			case JOptionPane.CLOSED_OPTION: // �׳� ����
				break;

			case JOptionPane.YES_OPTION: // Ȯ�� ����
				removeKey();
				break;

			case JOptionPane.NO_OPTION: // ��� ����
				break;
			}
		}
	}

	class InputTextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String label = JOptionPane
					.showInputDialog("Input Text You want to Show");
			if (label != null) {
				setTextLabel(label);
			} else {
			} // no input
		}
	}

	class FilePathButtonListener implements ActionListener {

		JFileChooser fileExplorer = new JFileChooser();

		@Override
		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Only Image Files..", "png", "bmp", "gif", "jpg");
			fileExplorer.setFileFilter(filter);

			int ret = fileExplorer.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) { // cancel
				JOptionPane.showMessageDialog(null, "canceled",
						"Cancel", JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, ret + "Image added",
						"Add", JOptionPane.PLAIN_MESSAGE);
				setImagePath((fileExplorer.getSelectedFile())
						.getAbsolutePath());
				return;
			}

		}
	}

}
