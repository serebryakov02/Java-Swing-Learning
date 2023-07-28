package gui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ProgressDialogListener {
	private JButton saveBtn;
	private JButton refreshBtn;
	private ToolbarListener textListener;
	private JButton execBtn;
	private ProgressDialog progressDialog;
	
	public Toolbar(JFrame parent) {
		setBorder(BorderFactory.createEtchedBorder());
		setFloatable(false);
		
		progressDialog = new ProgressDialog(parent);
		progressDialog.setListener((gui.ProgressDialogListener) this);
		
		saveBtn = new JButton();
		saveBtn.setIcon(Utils.createIcon("/images/floppy-disk.png"));
		saveBtn.setToolTipText("Save");
		
		refreshBtn = new JButton();
		refreshBtn.setIcon(Utils.createIcon("/images/refresh.png"));
		refreshBtn.setToolTipText("Refresh");
		
		execBtn = new JButton();
		execBtn.setIcon(Utils.createIcon("/images/execute.png"));
		execBtn.setToolTipText("Exec dialog");
		
		add(saveBtn);
		add(refreshBtn);
		add(execBtn);
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
		
		execBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				progressDialog.setVisible(true);
			}
		});
	}

	@Override
	public void progressDialogCancelled() {
		System.out.println("Cancelled");
		progressDialog.setVisible(false);
	}
}
