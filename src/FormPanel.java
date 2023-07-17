import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	
	public FormPanel() {
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameTxtField = new JTextField(10);
		occupationTxtField = new JTextField(10);
		
		okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTxtField.getText();
				String occupation = occupationTxtField.getText();
				//System.out.println(name);
				//System.out.println(occupation);
				FormEvent ev = new FormEvent(this, name, occupation);
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
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		add(nameLabel, gc);
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		add(nameTxtField, gc);
		
		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.gridy = 1;
		add(occupationLabel, gc);
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 1;
		add(occupationTxtField, gc);
		
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.gridx = 0;
		gc.weighty = 1.0;   //request any extra vertical space
		gc.gridx = 1;
		gc.gridy = 2;
		add(okBtn, gc);
	}
	
	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}
