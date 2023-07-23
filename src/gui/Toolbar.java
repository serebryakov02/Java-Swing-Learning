package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel {
	private JButton saveBtn;
	private JButton refreshBtn;
	private ToolbarListener textListener;
	
	public Toolbar() {
		setBorder(BorderFactory.createEtchedBorder());
		
		saveBtn = new JButton("Save");
		refreshBtn = new JButton("Refresh");
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(saveBtn);
		add(refreshBtn);
	}
	
	public void setToolbarListener(ToolbarListener listener) {
		
		this.textListener = listener;
		
		saveBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textListener.saveEventOccured();
			}
		});
		
		refreshBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textListener.refreshEventOccured();
			}
		});	
	}
}
