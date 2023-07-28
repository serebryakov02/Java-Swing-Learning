package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressDialog extends JDialog {
	private JProgressBar progressBar;
	private int progress = 0;
	private JButton btnAdd;
	private JButton btnCancel;
	
	public ProgressDialog(JFrame parent) {
		super(parent, "Progress Dialog Window");
		
		setSize(400, 200);
		this.setLocationRelativeTo(parent);
		
		init_gui();
	}
	
	public void init_gui() {
		progressBar = new JProgressBar();
		progressBar.setMaximum(5);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (progress >= 5) {
					progress = -1;
					progressBar.setValue(++progress);
				}
				else {
					progressBar.setValue(++progress);
				}
				
				System.out.println("Progress: " + progress);
			}
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.setLayout(new GridLayout(3, 1));
		this.add(progressBar);
		this.add(btnAdd);
		this.add(btnCancel);
	}
}
