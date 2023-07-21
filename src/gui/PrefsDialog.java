package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PrefsDialog extends JDialog {
	private JButton OKbtn;
	private JButton cancelBtn;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	
	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);
		
		OKbtn = new JButton("OK");
		OKbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer value = (Integer) portSpinner.getValue();
				System.out.println(value);
				setVisible(false);
			}
		});
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.gridy = 0;
		
		add(new JLabel("Port: "), gc);
		
		gc.gridx++;
		add(portSpinner, gc);
		
		////////// Next Row //////////
		gc.gridy++;
		gc.gridx = 0;
		add(OKbtn, gc);
		gc.gridx++;
		add(cancelBtn, gc);
		
		setSize(400, 400);
		setLocationRelativeTo(parent);
	}
}
