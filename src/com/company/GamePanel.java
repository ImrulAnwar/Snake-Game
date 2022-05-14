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
	static final int DELAY = 75;
	final int[] x = new int[GAME_UNITS];
	final int[] y = new int[GAME_UNITS];
	int bodyParts = 6;
	int foodEaten;
	int foodX;
	int foodY;
	char direction = 'R';
	boolean running = false;
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
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
			graphics.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			graphics.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}
		graphics.setColor(Color.WHITE);
		graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
	}

	public void newFood() {
		foodX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
		foodY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
	}

	public void move() {

	}

	public void checkFood() {

	}

	public void checkCollisions() {

	}

	public void gameOver(Graphics graphics) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public static class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

		}
	}

}
