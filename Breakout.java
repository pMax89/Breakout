/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.concurrent.Delayed;



public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
/** Brick */
	private GRect brick;
/** Paddle */
	private GRect paddle;
	
	private GOval ball;
	
	private double vx, vy;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		
			setup();
			play();
		
		
	}
	
	private void setup(){
		setupBricks();
		setupPaddle();
		setupBall();
	}
	
	private void setupBricks(){
		for (int i = 0; i < NBRICK_ROWS; i++) {			
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				
				double x = getWidth()/2 - (NBRICKS_PER_ROW*BRICK_WIDTH)/2 - ((NBRICKS_PER_ROW-1)*BRICK_SEP)/2 + j*BRICK_WIDTH + j*BRICK_SEP;
				double y = BRICK_Y_OFFSET + i*BRICK_HEIGHT + i*BRICK_SEP;
				brick = new GRect(x,y,BRICK_WIDTH,BRICK_HEIGHT);
				brick.setFilled(true);
				add(brick);
				
				switch (i) {
				case 0: case 1:
					brick.setColor(Color.RED);
					break;
				case 2: case 3:
					brick.setColor(Color.ORANGE);
					break;
				case 4: case 5:
					brick.setColor(Color.YELLOW);
					break;
				case 6: case 7:
					brick.setColor(Color.GREEN);
					break;
				case 8: case 9:
					brick.setColor(Color.CYAN);
					break;
				default:
					break;
				}
			}
		}
	}
	private void setupPaddle(){
		double x = getWidth()/2 - PADDLE_WIDTH/2;
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(x,y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}
	public void mouseMoved(MouseEvent e){
		if (e.getX() < getWidth() - PADDLE_WIDTH) {
			paddle.setLocation(e.getX(), getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
		
	}
	
	private void setupBall(){
		double x = getWidth()/2 - BALL_RADIUS;
		double y = getHeight()/2 - BALL_RADIUS;
		ball = new GOval(x,y,BALL_RADIUS,BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	private void play(){
		waitForClick();
		ballVelocity();
		while (true) {
			moveBall();
			if (ball.getY() >= getHeight()) {
				break;
			}
		}
	}
	//Направление мяча
	private void ballVelocity(){		
		vy = 5.0;
		vx = rgen.nextDouble(1.0,3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
	}
	private void moveBall(){
		ball.move(vx, vy);
		//отскок от боков
		if (ball.getX() > getWidth() - BALL_RADIUS || ball.getX() <= 0 ) {
			vx = -vx;
		}
		//отскок от пола и потолка
		if (ball.getY() <= 0) {
			vy = -vy;
		}
		GObject collider = getCollidiObject();
		if (collider == paddle) {
			vy = -vy;
		}else if (collider != null) {
			remove(collider);
			vy = -vy;
		}
		pause(10);
	}
	
	private GObject getCollidiObject(){
		if (getElementAt(ball.getX(), ball.getY())!= null) {
			return getElementAt(ball.getX(), ball.getY());
		}
		else if (getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY()) != null) {
			return getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY());
		}
		else if (getElementAt(ball.getX(), ball.getY() + 2*BALL_RADIUS) != null) {
			return getElementAt(ball.getX(), ball.getY() + 2*BALL_RADIUS);
		}
		else if (getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS) != null) {
			return getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS);
		}else{
		return null;
		}
	}
	

}
