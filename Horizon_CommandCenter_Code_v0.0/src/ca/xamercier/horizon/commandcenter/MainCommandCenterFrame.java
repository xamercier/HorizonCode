package ca.xamercier.horizon.commandcenter;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.TextArea;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import ca.xamercier.horizon.commandcenter.rocketcommands.RocketCommands;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class MainCommandCenterFrame {

	private JFrame frame;
	private JTextField ServerPort;

	/**
	 * Launch the application.
	 */

	static TextArea ConsoleOutput;
	static JLabel RocketState;
	static JLabel RocketXYZGyro;

	public static JLabel getLblXyz() {
		return RocketXYZGyro;
	}

	static public JLabel getlblHorizonState() {
		return RocketState;
	}

	static public TextArea getTextArea() {
		return ConsoleOutput;
	}

	CommandCenter center = new CommandCenter();
	private JPasswordField LaunchPassword;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainCommandCenterFrame window = new MainCommandCenterFrame();
					window.frame.setName("Horizon - CommandCenter");
					window.frame.setTitle("Horizon - CommandCenter");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws UnknownHostException
	 */
	public MainCommandCenterFrame() throws UnknownHostException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws UnknownHostException
	 */
	private void initialize() throws UnknownHostException {
		frame = new JFrame();
		frame.setBounds(100, 100, 1035, 731);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel Title = new JLabel("Horizon - CommandCenter");
		Title.setBounds(10, 11, 166, 26);
		frame.getContentPane().add(Title);

		Label CommandCenterStatus = new Label("CommandCenter - OFF");
		CommandCenterStatus.setBounds(10, 75, 133, 26);
		frame.getContentPane().add(CommandCenterStatus);

		ServerPort = new JTextField();
		ServerPort.setEditable(false);
		ServerPort.setText("Port: 25565");
		ServerPort.setBounds(10, 107, 108, 20);
		frame.getContentPane().add(ServerPort);
		ServerPort.setColumns(10);

		JToggleButton StartCommandCenterButton = new JToggleButton("Start CommandCenter");
		StartCommandCenterButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (StartCommandCenterButton.isSelected()) {
					if (CommandCenter.getServer().isAlive()) {
					} else {
						CommandCenter.getServer().init(25565);
						CommandCenterStatus.setText("CommandCenter - ON");
					}
				} else {
					StartCommandCenterButton.setSelected(true);
				}
			}
		});
		StartCommandCenterButton.setBounds(10, 48, 223, 26);
		frame.getContentPane().add(StartCommandCenterButton);

		JSeparator SeparationLine = new JSeparator();
		SeparationLine.setBounds(0, 138, 239, 2);
		frame.getContentPane().add(SeparationLine);

		ConsoleOutput = new TextArea();
		ConsoleOutput.setBackground(Color.YELLOW);
		ConsoleOutput.setEditable(false);
		ConsoleOutput.setText("Horizon - Console output");
		ConsoleOutput.setBounds(239, 11, 770, 454);
		frame.getContentPane().add(ConsoleOutput);

		LaunchPassword = new JPasswordField();
		LaunchPassword.setText("LAUNCH PASSWORD");
		LaunchPassword.setBounds(10, 231, 133, 20);
		frame.getContentPane().add(LaunchPassword);

		JLabel LabelLaunchPassword = new JLabel("Launch Password");
		LabelLaunchPassword.setBounds(10, 194, 133, 26);
		frame.getContentPane().add(LabelLaunchPassword);

		JButton LaunchButton = new JButton("Launch");
		LaunchButton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (LaunchPassword.getText().equals("LaunchMe") && CommandCenter.isRocketConnected) {
					RocketCommands.LaunchFlight();
				} else {
					System.out.println("[CommandCenter] Error: Cannot launch.");
					MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] Error: Cannot launch.");
					//MainCommandCenter.getLogger().info("[CommandCenter] Error: Cannot launch.");
				}
			}
		});
		LaunchButton.setBounds(10, 293, 89, 23);
		frame.getContentPane().add(LaunchButton);

		RocketState = new JLabel("Horizon State:");
		RocketState.setBounds(10, 151, 288, 32);
		frame.getContentPane().add(RocketState);

		JButton AbortButton = new JButton("Abort");
		AbortButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RocketCommands.AbortFlight();
			}
		});
		AbortButton.setBounds(10, 327, 89, 23);
		frame.getContentPane().add(AbortButton);

		JButton TestTVCButton = new JButton("Test TVC");
		TestTVCButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RocketCommands.TestTVC();
			}
		});
		TestTVCButton.setBounds(130, 293, 89, 23);
		frame.getContentPane().add(TestTVCButton);

		JLabel Title2 = new JLabel("Horizon Project - Xavier Mercier");
		Title2.setBounds(10, 641, 209, 40);
		frame.getContentPane().add(Title2);

		RocketXYZGyro = new JLabel("XYZ");
		RocketXYZGyro.setBounds(239, 537, 750, 58);
		frame.getContentPane().add(RocketXYZGyro);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{CommandCenterStatus, SeparationLine, ConsoleOutput, Title, StartCommandCenterButton, ServerPort, LabelLaunchPassword, LaunchButton, LaunchPassword, RocketState}));
	}
}
