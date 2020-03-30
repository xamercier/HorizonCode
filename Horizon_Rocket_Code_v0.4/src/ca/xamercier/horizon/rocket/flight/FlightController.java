package ca.xamercier.horizon.rocket.flight;

import ca.xamercier.horizon.rocket.MainHorizon;
import ca.xamercier.horizon.rocket.threads.MPU6050;
import ca.xamercier.horizon.rocket.utils.RocketState;

public class FlightController extends Thread {

	
	
	@SuppressWarnings("deprecation")
	public void run() {
		RocketState.setState(RocketState.InFlight);
		while(RocketState.isState(RocketState.InFlight)) {
			double[] filteredAngles = MainHorizon.getMpu6050().getFilteredAngles();
			MainHorizon.getCommandCenter().send(MPU6050.xyzValuesToString(MPU6050.angleToString(filteredAngles[0] - 180),
					MPU6050.angleToString(filteredAngles[1] - 180), MPU6050.angleToString(filteredAngles[2])));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("Accelerometer:"); double[]
					  accelAngles = MainHorizon.getMpu6050().getAccelAngles(); System.out.println("\t" +
					  MPU6050.xyzValuesToString(MPU6050.angleToString(accelAngles[0]),
					  MPU6050.angleToString(accelAngles[1]),
					  MPU6050.angleToString(accelAngles[2])));
					  
					  double[] accelAccelerations = MainHorizon.getMpu6050().getAccelAccelerations();
					  System.out.println("\tAccelerations: " +
					  MPU6050.xyzValuesToString(MPU6050.accelToString(
					  accelAccelerations[0]),
					  MPU6050.accelToString(accelAccelerations[1]),
					  MPU6050.accelToString(accelAccelerations[2])));
			
			
			/*
			if(filteredAngles[0] -180 > 35 || filteredAngles[1] -180 > 35 || filteredAngles[0] -180 < -35 || filteredAngles[1] -180 < -35) {
				RocketState.setState(RocketState.Aborded);
			}
			*/
			
			
		}
		this.stop();
	}
	
	
	
}