package gui;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel{
	private JButton helloBtn;
	private JButton goodbyeBtn;
	private StringListener textListener;
	
	public Toolbar() {
		setBorder(BorderFactory.createEtchedBorder());
		
		helloBtn = new JButton("Hello");
		goodbyeBtn = new JButton("Goodbye");
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(helloBtn);
		add(goodbyeBtn);
	}
	
	public void setStringListener(StringListener listener) {
		if (listener == null) return;
		
		this.textListener = listener;
		
		helloBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textListener.textEmitted("Hello\n");
			}
		});
		
		goodbyeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textListener.textEmitted("Goodbye\n");
			}
		});
	}
}
