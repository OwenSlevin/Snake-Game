import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame implements ActionListener{
	JButton resetButton;
	GamePanel panel;
	//GameFrame frame;

	GameFrame() {
		GamePanel panel = new GamePanel();
		//GamePanel game = new GamePanel();
		this.add(panel);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); //if we add components to a JFrame the pack function will take the JFrame and make it fit around all the components we add to the frame
		this.setVisible(true);
		this.setLocationRelativeTo(null); // this sets the window to appear in the center of our screen
		
		resetButton = new JButton();
		resetButton.setText("Play Again");
		resetButton.setSize(100, 50); //btn size
		resetButton.setLocation(0, 200);
		resetButton.addActionListener(this);
		//resetButton.setVisible(false);
		
	//	panel = new GameFrame();
		
		this.add(resetButton);
		//this.add(panel);
		
		//this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == resetButton) {
			//this.remove(panel);
			panel = new GamePanel();
			this.add(panel);
			panel.requestFocusInWindow(); //to allow us to move snake after restarting
			SwingUtilities.updateComponentTreeUI(this); //
		}
	} 
}
