package ca.xamercier.horizon.commandcenter.communicationsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ca.xamercier.horizon.commandcenter.CommandCenter;
import ca.xamercier.horizon.commandcenter.MainCommandCenterFrame;

public class ServerClient extends Thread {

	Socket clientSocket = null;
	public static BufferedReader in = null;
	PrintWriter out = null;
	Server server = null;
	String ip = null;

	ServerClient(Socket clientSocket, Server server) {
		this.clientSocket = clientSocket;
		this.server = server;
		this.ip = clientSocket.getInetAddress().getHostAddress();
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String msg) {
		out.println(msg);
	}

	@Override
	public void run() {
		boolean run = true;
		while (run) {
			try {
				String line = in.readLine();
				if (line.length() != 0) {
					server.getMessage(line);
				}
			} catch (IOException e) {
				run = false;
				Server.getClientList().remove(this);
				CommandCenter.isRocketConnected = false;
			} catch (NullPointerException e) {
				run = false;
				Server.getClientList().remove(this);
				CommandCenter.isRocketConnected = false;
			}
		}
		try {
			clientSocket.close();
			CommandCenter.isRocketConnected = false;
			System.out.println("[CommandCenter] Rocket disconnected from the CommandCenter.");
			MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] Rocket disconnected from the CommandCenter.");
			//MainCommandCenter.getLogger().info("[CommandCenter] Rocket disconnected from the CommandCenter.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopServerClient() {
		try {
			clientSocket.close();
			CommandCenter.isRocketConnected = false;
			System.out.println("[CommandCenter] Rocket disconnected from the CommandCenter.");
			MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] Rocket disconnected from the CommandCenter.");
			//MainCommandCenter.getLogger().info("[CommandCenter] Rocket disconnected from the CommandCenter.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
