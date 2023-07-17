import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	
	public MainFrame() {
		super("Hello World!");
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		setLayout(new BorderLayout());
		
		textPanel = new TextPanel();
		toolbar = new Toolbar();
		toolbar.setStringListener(new StringListener() {
			@Override
			public void textEmitted(String text) {
				//System.out.print(text);
				textPanel.appendText(text);
			}
		});
		
		
		formPanel = new FormPanel();
		formPanel.setFormListener(new FormListener() {
			@Override
			public void formEventOccured(FormEvent e) {
				String name = e.getName();
				String occupation = e.getOccupation();
				textPanel.appendText(name + ": " + occupation + "\n");
			}
		});
		
		add(textPanel, BorderLayout.CENTER);
		add(toolbar, BorderLayout.PAGE_START);
		add(formPanel, BorderLayout.LINE_START);
	}
}
