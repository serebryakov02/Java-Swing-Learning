import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

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
		
		setJMenuBar(createMenu());
		
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
				int ageCat = e.getAgeCategory();
				String empCat = e.getEmpCat();
				String gender = e.getGender();
				
				textPanel.appendText(name + ": " + occupation + ": " + 
				ageCat + ", " + empCat + "\n");
				
				System.out.println(gender);
			}
		});
		
		add(textPanel, BorderLayout.CENTER);
		add(toolbar, BorderLayout.PAGE_START);
		add(formPanel, BorderLayout.LINE_START);
	}
	
	public JMenuBar createMenu() {
		JMenuBar menu = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exportItem = new JMenuItem("Export To...");
		JMenuItem importItem = new JMenuItem("Import...");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exportItem);
		fileMenu.add(importItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menu.add(fileMenu);
		
		JMenu windowMenu = new JMenu("Window");
		menu.add(windowMenu);
		JMenu showMenu = new JMenu("Show");
		JCheckBoxMenuItem personFormItem = new JCheckBoxMenuItem("Person Form");
		showMenu.add(personFormItem);
		windowMenu.add(showMenu);
		
		personFormItem.setSelected(true);
		personFormItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem chkBox = (JCheckBoxMenuItem)e.getSource();
				formPanel.setVisible(chkBox.isSelected());
			}
		});
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		return menu;
	}
}
