package homeWork10.Chat.Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.awt.event.ActionEvent;

public class ServerForm extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JTextField textPort;
	public JLabel lblNewLabel;
	public JButton btnStart;
	public JButton btnClose;
	public JScrollPane scrollPane;
	public JTextArea textLog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
					frame.setVisible(true);
					ServerMG.getServerMG().setServerForm(frame); //注册窗体
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerForm() {
		setTitle("\u591A\u4EBA\u804A\u5929\u670D\u52A1\u5668");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 365);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);
		
		this.panel = new JPanel();
		this.panel.setLayout(null);
		this.panel.setBorder(new TitledBorder(null, "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.panel.setBounds(10, 10, 447, 74);
		this.contentPane.add(this.panel);
		
		this.textPort = new JTextField();
		this.textPort.setText("1998");
		this.textPort.setColumns(10);
		this.textPort.setBounds(72, 26, 83, 21);
		this.panel.add(this.textPort);
		
		this.lblNewLabel = new JLabel("\u7AEF\u53E3\u53F7\uFF1A");
		this.lblNewLabel.setBounds(16, 26, 58, 21);
		this.panel.add(this.lblNewLabel);
		
		this.btnStart = new JButton("\u5F00\u59CB\u670D\u52A1");
		this.btnStart.addActionListener(new BtnStartActionListener());
		this.btnStart.setBounds(199, 25, 97, 23);
		this.panel.add(this.btnStart);
		
		this.btnClose = new JButton("\u505C\u6B62\u670D\u52A1");
		this.btnClose.addActionListener(new BtnCloseActionListener());
		this.btnClose.setBounds(322, 25, 97, 23);
		this.panel.add(this.btnClose);
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportBorder(new TitledBorder(null, "\u6D88\u606F\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setBounds(10, 96, 447, 222);
		this.contentPane.add(this.scrollPane);
		
		this.textLog = new JTextArea();
		this.scrollPane.setViewportView(this.textLog);
	}
	private class BtnStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int port=Integer.parseInt(textPort.getText().trim());
			if(ServerMG.getServerMG().CreateServer(port)) {
				ServerMG.getServerMG().setTextLog("服务器开启...");
			}else {
				ServerMG.getServerMG().setTextLog("服务器开启失败！");
			}
		}
	}
	private class BtnCloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ServerMG.getServerMG().closeServerSocke();
		}
	}
}
