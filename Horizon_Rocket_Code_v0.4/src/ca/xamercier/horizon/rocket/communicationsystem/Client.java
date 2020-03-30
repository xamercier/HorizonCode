package ca.xamercier.horizon.rocket.communicationsystem;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ca.xamercier.horizon.rocket.utils.CommandCenterCommands;

public class Client extends Thread {

	String hostName = null;
	int portNumber = 0;
	boolean run = true;
	static Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;

	public boolean init(String ip, int servPort) {
		this.portNumber = servPort;
		this.hostName = ip;
		try {
			socket = new Socket(hostName, portNumber);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			return false;
		}
		this.start();
		return true;
	}

	public void send(String toSend) {
			out.println(toSend);
	}

	public void run() {
		String msg = "";
		while (run) {
			try {
				msg = in.readLine();
			} catch (IOException e) {
				run = false;
			}
			if (msg != null && run == true) {
					System.out.println("[CommandCenter] " + msg);		
					try {
						CommandCenterCommands.CommandCenter(msg);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
		try {
			socket.close();
			System.out.println("[Rocket] Disconnected from the CommandCenter");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopClient() {
		run = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
