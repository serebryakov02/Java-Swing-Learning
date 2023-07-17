import javax.swing.SwingUtilities;

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// We have to put all our code inside
				// of this method to provide thread-safe.
				new MainFrame();
			}
		});
	}
}
