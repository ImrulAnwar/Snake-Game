package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 50;
	final int[] x = new int[GAME_UNITS];
	final int[] y = new int[GAME_UNITS];
	int bodyParts = 6;
	int foodEaten;
	int foodX;
	int foodY;
	public char direction = 'D';
	boolean isRunning = false;
	Timer timer;
	Random random;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newFood();
		isRunning = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}

	public void draw(Graphics graphics) {
		if (isRunning) {
			//food
			graphics.setColor(Color.WHITE);
			graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

			//snake body
			for (int i = 0; i < bodyParts; i++) {
					graphics.setColor(Color.LIGHT_GRAY);
					graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			showScore(graphics);
		} else {
			gameOver(graphics);
		}
	}

	private void showScore(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("Poppins", Font.BOLD, 45));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: "+foodEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+foodEaten))/2, graphics.getFont().getSize());
	}

	public void newFood() {
		foodX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
		foodY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
			case 'W' -> y[0] = y[0] - UNIT_SIZE;
			case 'S' -> y[0] = y[0] + UNIT_SIZE;
			case 'A' -> x[0] = x[0] - UNIT_SIZE;
			case 'D' -> x[0] = x[0] + UNIT_SIZE;
		}
	}

	public void checkFood() {
		if ((x[0] == foodX) && (y[0] == foodY)) {
			bodyParts++;
			foodEaten++;
			newFood();
		}
	}

	public void checkCollisions() {
		//checks if head collides with body
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				isRunning = false;
				break;
			}
		}
		//if head touches left border
		if (x[0] < 0) {
			isRunning = false;
		}
		//if head touches right border
		if (x[0] > SCREEN_WIDTH) {
			isRunning = false;
		}
		//if head touches top border
		if (y[0] < 0) {
			isRunning = false;
		}
		//if head touches bottom border
		if (y[0] > SCREEN_HEIGHT) {
			isRunning = false;
		}
		if (!isRunning) {
			timer.stop();
		}

	}

	public void gameOver(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("Poppins", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		showScore(graphics);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isRunning) {
			move();
			checkFood();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'D') {
						direction = 'A';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'A') {
						direction = 'D';
					}
					break;
				case KeyEvent.VK_UP:
					if (direction != 'S') {
						direction = 'W';
					}
					break;
				case KeyEvent.VK_DOWN:
					if (direction != 'W') {
						direction = 'S';
					}
					break;

			}
		}
	}

}
