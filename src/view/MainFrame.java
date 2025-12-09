package view;

import javax.swing.JFrame;

import controller.MainController;
import controller.TimeController;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	// Instance variables
	private ContentPane contentPane;
	
	// Constants

	public MainFrame(MainController mainController, TimeController timeController) {
		this.setTitle("TagMan by Ferran Hendriks");
		contentPane = new ContentPane(timeController, mainController);
		this.setContentPane(contentPane);
	}

	public void render() {
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public ContentPane getContentPane() {
		return contentPane;
	}
	
	

}
