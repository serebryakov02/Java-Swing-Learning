package gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameTxtField;
	private JTextField occupationTxtField;
	private JButton okBtn;
	private FormListener formListener;
	private JList ageList;
	private JComboBox empCombo;
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel taxLbl;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	
	public FormPanel() {
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameTxtField = new JTextField(10);
		occupationTxtField = new JTextField(10);
		okBtn = new JButton("OK");
		ageList = new JList();
		empCombo = new JComboBox();
		citizenCheck = new JCheckBox("US Citizen");
		taxField = new JTextField(10);
		taxLbl = new JLabel("Tax ID: ");
		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");
		genderGroup = new ButtonGroup();
		
		// Set up mnemonics
		okBtn.setMnemonic(KeyEvent.VK_O);
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameTxtField);	
		
		// Set up radio buttons and group button
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		
		// Set up the "tax section"
		citizenCheck.setSelected(false);
		taxField.setEnabled(false);
		taxLbl.setEnabled(false);
		
		citizenCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isChecked = citizenCheck.isSelected();
				taxField.setEnabled(isChecked);
				taxLbl.setEnabled(isChecked);
			}
		});
		
		// Set up list box
		ageList.setPreferredSize(new Dimension(130, 60));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18-35"));
		ageModel.addElement(new AgeCategory(2, "Over 35"));
		ageList.setModel(ageModel);
		ageList.setSelectedIndex(1);
		
		// Set up combo
		DefaultComboBoxModel empModel = new DefaultComboBoxModel();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(1);
		
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTxtField.getText();
				String occupation = occupationTxtField.getText();
				AgeCategory ageCat = (AgeCategory)ageList.getSelectedValue();
				String empCat = (String)empCombo.getSelectedItem();
				String taxID = taxField.getText();
				boolean usCitizen = citizenCheck.isSelected();
				String gender = genderGroup.getSelection().getActionCommand();

				FormEvent ev = new FormEvent(this, name, occupation, 
						ageCat.getId(), empCat, taxID, usCitizen, gender);
				if (ev != null)
					formListener.formEventOccured(ev);
			}
		});
		
		Dimension dim  = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		
		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		setLayout(new GridBagLayout());
		layoutComponents();
	}
	
	public void layoutComponents() {
		GridBagConstraints gc = new GridBagConstraints();

		// First row
		gc.gridx = 0;
		gc.gridy = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		add(nameLabel, gc);
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		add(nameTxtField, gc);
		
		// Second row
		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.gridy++;
		add(occupationLabel, gc);
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		add(occupationTxtField, gc);
		
		// Third row	
		gc.gridy++;
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Age: "), gc);
		gc.gridx = 1;
		add(ageList, gc);
		
		// Next row
		gc.gridy++;
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Employment: "), gc);
		gc.gridx = 1;
		add(empCombo, gc);
		
		// Next row
		gc.gridy++;
		gc.gridx = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		add(citizenCheck, gc);
		
		// Next row
		gc.gridy++;
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(taxLbl, gc);
		gc.gridx = 1;
		add(taxField, gc);
		
		// Next row
		gc.gridy++;
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Gender: "), gc);
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		add(maleRadio, gc);
		
		// Next row
		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		add(femaleRadio, gc);
		
		// Next row
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.weighty = 1.0;   //request any extra vertical space
		gc.gridx = 1;
		gc.gridy++;
		add(okBtn, gc);
	}
	
	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}

class AgeCategory {
	private int id;
	private String text;
	
	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public int getId() {
		return id;
	}
}
