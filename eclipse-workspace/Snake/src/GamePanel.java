import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	//declare all variables we will need
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS]; //since snake cant be bigger than the game itself
	final int y[] = new int[GAME_UNITS]; //two arrays hold x and y coords for snakes body, including head
	int bodyParts = 6; //start with a size of 6
	int applesEaten;
	int appleX; //x and y coords for where apples will appear
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	//GameFrame game;
	GamePanel game;
	JButton resetButton;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		//resetButton = new JButton();
		//resetButton.setText("Play Again");
		//resetButton.setSize(100, 50); //btn size
		//resetButton.setLocation(0, 200);
		//resetButton.addActionListener(this);
		//resetButton.setVisible(false);
		//resetButton.addActionListener(this);
		
		//game = new GamePanel();
		
		//this.add(resetButton);
		
		//this.add(game);
		
		startGame(); //now that panels constructed we call the start game method
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this); //pass "this" because were using the action listener interface
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw (Graphics g) {
		//to draw lines on our game panel and make it look like a grid
		if (running) { //if game is running do this
			for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //draw y axis
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); //draw x axis
			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) { //head of snake
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45, 180, 0));
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); //to generate random rgb values and apply them to the snakes body. Flashing colors
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			//same as game over method but to display the score
			g.setColor(Color.red);
			g.setFont(new Font ("Ink Free", Font.BOLD, 40));
			//font metrics allow us to display text in the center of the screen
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		}
		
		else {
			gameOver(g); //call game over method and pass our graphics parameter g
		}
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //we cast the rand num as an int so we dont run into errors
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move () {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1]; //shift all coords in this array over by 1
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case'U':
			y[0] = y[0] - UNIT_SIZE;//so it will move to the next position
			break;
		case'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		//we will look at apples coords and the snakes coords
		if ((x[0] == appleX) && y[0] == appleY) {
			bodyParts++; //there are on the same coord so the apple has been eaten
			applesEaten++;
			newApple(); //call this method to generate a new apple
		}
	}
	
	public void checkCollisions() {
		//to check if head collides with body
		for (int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//to check if head touches left boarder
		if (x[0] < 0) {
			running = false;
		}
		//check to see if head touches right boarder
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top boarder
		if (y[0] < 0) {
			running = false;
		}
		//check if head touches bottom boarder
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		//if there is a collision stop timer
		if (!running) {
			timer.stop();
		}
	}
	
	public void gameOver (Graphics g) {
		//display score after game ends
		g.setColor(Color.red);
		g.setFont(new Font ("Ink Free", Font.BOLD, 40));
		//font metrics allow us to display text in the center of the screen
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize()); // to make score font appear higher than game over font
		
		//game over text
		g.setColor(Color.red);
		g.setFont(new Font ("Ink Free", Font.BOLD, 75));
		//font metrics allow us to display text in the center of the screen
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
		//resetButton.setVisible(true);
		
		/*g.setColor(Color.red);
		g.setFont(new Font ("Ink Free", Font.BOLD, 40));
		JButton b = new JButton("Play Again");
		b.setBounds(400, 450, 100, 450);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//startGame();
				new GameFrame();
			}
		}); */
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
		//if (e.getSource() == resetButton) {
			//running = true;
			//this.remove(game);
			//game = new GamePanel();
			//this.add(game);
			//GamePanel();
		//}
		
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed (KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				//if direction does not equal right we go left
				//VK_LEFT means if a key stroke is detected from the arrow keys do something
				//since the snake cant make 180 turn or it would kill itself we have to make sure that command cant be performed
				if (direction != 'R') {
					direction = 'L';
				}
				break;
				//if dir does not equal left we go right
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
