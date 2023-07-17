import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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
	
	public FormPanel() {
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameTxtField = new JTextField(10);
		occupationTxtField = new JTextField(10);
		okBtn = new JButton("OK");
		ageList = new JList();
		empCombo = new JComboBox();
		
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
				//System.out.println(ageCat.getId());
				String empCat = (String)empCombo.getSelectedItem();
				System.out.println(empCat);
				//System.out.println(name);
				//System.out.println(occupation);
				FormEvent ev = new FormEvent(this, name, occupation, 
						ageCat.getId(), empCat);
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
