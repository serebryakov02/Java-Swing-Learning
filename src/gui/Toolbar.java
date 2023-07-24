package gui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar {
	private JButton saveBtn;
	private JButton refreshBtn;
	private ToolbarListener textListener;
	
	public Toolbar() {
		setBorder(BorderFactory.createEtchedBorder());
		setFloatable(false);
		
		saveBtn = new JButton();
		saveBtn.setIcon(createIcon("/images/floppy-disk.png"));
		saveBtn.setToolTipText("Save");
		
		refreshBtn = new JButton();
		refreshBtn.setIcon(createIcon("/images/refresh.png"));
		refreshBtn.setToolTipText("Refresh");
		
		add(saveBtn);
		add(refreshBtn);
	}
	
	private ImageIcon createIcon(String path) {
		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}
		
		ImageIcon icon = new ImageIcon(url);
		return icon;
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
