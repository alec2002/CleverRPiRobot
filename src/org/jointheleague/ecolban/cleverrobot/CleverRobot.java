package org.jointheleague.ecolban.cleverrobot;

import java.io.IOException;
import java.util.Arrays;

import org.jointheleague.ecolban.rpirobot.IRobotAdapter;
import org.jointheleague.ecolban.rpirobot.IRobotInterface;
import org.jointheleague.ecolban.rpirobot.SimpleIRobot;

public class CleverRobot extends IRobotAdapter implements Runnable {

	// The following measurements are taken from the interface specification

	private boolean running;
	private final boolean debug = true; // Set to true to get debug messages.
	private int d = 0;
	private int rightWheelSpeed = 20;
	private int leftWheelSpeed = 50;

	public CleverRobot(IRobotInterface iRobot) {
		super(iRobot);
		if (debug) {
			System.out.println("Hello. I'm CleverRobot");
		}
	}

	public static void main(String[] args) throws IOException {
		try {
			IRobotInterface base = new SimpleIRobot();
			CleverRobot rob = new CleverRobot(base);
			rob.initialize();
			rob.run();
		} catch (InterruptedException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/* This method is executed when the robot first starts up. */
	private void initialize() throws IOException {
		// what would you like me to do, Clever Human?
		if (debug) {
			System.out.println("Initializing...");
		}
	}

	public void run() {

		running = true;
		while (running) {
			try {

				readSensors(SENSORS_GROUP_ID106);
				readSensors(SENSORS_BUMPS_AND_WHEEL_DROPS);
				int[] lightBumps = getLightBumps();
				// boolean l = isBumpLeft();
				System.out.println("lightBumps: " + Arrays.toString(lightBumps));
				System.out.println("isBumpRight = " + isBumpRight());
				driveDirect(leftWheelSpeed, rightWheelSpeed);
				if (lightBumps[5] > 4000) {
					driveDirect(-15, 0);
					Thread.sleep(500);
				} else {
					driveDirect(leftWheelSpeed, rightWheelSpeed);
				}
			} catch (IOException | InterruptedException e) {
				System.out.println("Exception: " + e.getClass());
				running = false;
			}
		}

		try {
			shutDown();
		} catch (IOException e) {
		}

	}

	private void shutDown() throws IOException {
		stop();
		closeConnection();
	}

}
