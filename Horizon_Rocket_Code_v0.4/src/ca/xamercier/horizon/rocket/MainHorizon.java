package ca.xamercier.horizon.rocket;

import java.io.IOException;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import ca.xamercier.horizon.rocket.communicationsystem.Client;
import ca.xamercier.horizon.rocket.flight.FlightController;
import ca.xamercier.horizon.rocket.threads.Lcd;
import ca.xamercier.horizon.rocket.threads.MPU6050;
import ca.xamercier.horizon.rocket.utils.RocketState;
import ca.xamercier.horizon.rocket.utils.i2c.lcd.LiquidCrystal_I2C;

public class MainHorizon {

	// UTILS AND COMMUNICATIONS
	static Client CommandCenter;
	static boolean isConnectedToCommandCenter = false;

	// LCD SCREEN
	static LiquidCrystal_I2C screen = new LiquidCrystal_I2C(1, 0x27, 2, 16);

	// IMU
	static MPU6050 mpu6050;

	// THREADS
	static Lcd lcdThread;
	static FlightController FlightControllerThread;

	public static FlightController getFlightControllerThread() {
		return FlightControllerThread;
	}

	public static void main(String[] args) throws IOException, UnsupportedBusNumberException, InterruptedException {
		initALL();
	}

	static void initALL() throws UnsupportedBusNumberException, InterruptedException {
		connectToCommandCenter();
		RocketState.setState(RocketState.Initialising);
		lcdThread = new Lcd();
		lcdThread.start();
		mpu6050 = new MPU6050();
		mpu6050.startUpdatingThread();
		FlightControllerThread = new FlightController();
		System.out.println("[Rocket] Initiation sequence done.");
		RocketState.setState(RocketState.Ready);
	}

	static void connectToCommandCenter() {
		CommandCenter = new Client();
		String ip = "192.168.1.114";
		int port = 25565;
		while (isConnectedToCommandCenter == false) {
			if (CommandCenter.init(ip, port)) {
				System.out.println("[Rocket] Connected to the CommandCenter:  " + ip + ":" + port);
				isConnectedToCommandCenter = true;
			} else {
				System.out.println("[Rocket] Could not connect to the CommandCenter:  " + ip + ":" + port);
				isConnectedToCommandCenter = false;
			}
		}

	}

	public static MPU6050 getMpu6050() {
		return mpu6050;
	}

	public static LiquidCrystal_I2C getScreen() {
		return screen;
	}

	public static Client getCommandCenter() {
		return CommandCenter;
	}

}
