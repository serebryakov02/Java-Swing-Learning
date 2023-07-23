package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -5491727952132645858L;
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	private PrefsDialog prefsDialog;
	private Preferences preferences;

	public MainFrame() {
		super("Hello World!");

		setMinimumSize(new Dimension(500, 400));
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		setLayout(new BorderLayout());

		setJMenuBar(createMenu());

		controller = new Controller();
		tablePanel = new TablePanel();
		prefsDialog = new PrefsDialog(this);
		
		
		// !!!!!!!!!!
		preferences = Preferences.userRoot().node("db");
		
		prefsDialog.setPrefListener(new PrefsListener() {
			@Override
			public void preferencesSet(String user, String password, int port) {
				System.out.println(user +": " + password);
				
				preferences.put("user", user);
				preferences.put("password", password);
				preferences.putInt("port", port);
			}
		});
		
		String user = preferences.get("user", "");
		String password = preferences.get("password", "");
		Integer port = preferences.getInt("port", 3306);
		prefsDialog.setDefaults(user, password, port);
		
		
		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void rowDeleted(int row) {
				controller.removePerson(row);
			}
		});
		
		tablePanel.setData(controller.getPeople());

		textPanel = new TextPanel();

		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		toolbar = new Toolbar();
		

		formPanel = new FormPanel();
		formPanel.setFormListener(new FormListener() {
			@Override
			public void formEventOccured(FormEvent e) {
				/*
				 * String name = e.getName(); String occupation = e.getOccupation(); int ageCat
				 * = e.getAgeCategory(); String empCat = e.getEmpCat(); String gender =
				 * e.getGender();
				 * 
				 * textPanel.appendText(name + ": " + occupation + ": " + ageCat + ", " + empCat
				 * + "\n");
				 * 
				 * System.out.println(gender);
				 */
				controller.addPerson(e);
				tablePanel.refresh();
			}
		});
		
		toolbar.setToolbarListener(new ToolbarListener() {

			@Override
			public void saveEventOccured() {
				controller.connect();
				
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, 
							"Unable to save to database.", 
							"Database connection problem.", 
							JOptionPane.WARNING_MESSAGE);
				}
			}

			@Override
			public void refreshEventOccured() {
				controller.connect();
				
				try {
					controller.load();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, 
							"Unable to load from database.", 
							"Database connection problem.", 
							JOptionPane.WARNING_MESSAGE);
				}
				tablePanel.refresh();
			}
			
		});

		add(toolbar, BorderLayout.PAGE_START);
		add(formPanel, BorderLayout.LINE_START);
		add(tablePanel, BorderLayout.CENTER);
	}

	public JMenuBar createMenu() {
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportItem = new JMenuItem("Export To...");

		exportItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, 
								"Could not save data to file", 
								"Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JMenuItem importItem = new JMenuItem("Import...");
		importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		importItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, 
								"Could not load data from file", 
								"Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exportItem);
		fileMenu.add(importItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menu.add(fileMenu);

		JMenu windowMenu = new JMenu("Window");
		menu.add(windowMenu);
		
		JMenuItem prefsItem = new JMenuItem("Preferences...");
		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		
		prefsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prefsDialog.setVisible(true);
			}
		});
		
		
		JMenu showMenu = new JMenu("Show");
		JCheckBoxMenuItem personFormItem = new JCheckBoxMenuItem("Person Form");
		showMenu.add(personFormItem);
		windowMenu.add(showMenu);
		
		windowMenu.add(prefsItem);

		personFormItem.setSelected(true);
		personFormItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem chkBox = (JCheckBoxMenuItem) e.getSource();
				formPanel.setVisible(chkBox.isSelected());
			}
		});

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the app?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				System.exit(0);
			}
		});

		return menu;
	}
}
