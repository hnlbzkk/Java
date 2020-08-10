package homeWork10.Chat.Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.Component;

public class ClientForm extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblNewLabel;
	public JTextField textIP;
	public JLabel lblNewLabel_1;
	public JTextField textPort;
	public JButton btnLogin;
	public JButton btnClose;
	public JLabel lblNewLabel_2;
	public JTextField textUser;
	public JScrollPane scrollPaneLog;
	public JTextArea textLog;
	public JScrollPane scrollPaneUser;
	public JPanel panel_1;
	public JTextField textSend;
	public JButton btnSendAll;
	public JButton btnSend1;

	/**
	 * Launch the application.
	 */
	DefaultListModel<String> itemUsers;
	public JList listUser;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientForm frame = new ClientForm();
					frame.setVisible(true);
					ClientMG.getClientMG().setClientForm(frame);//注册窗体
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientForm() {
		
		setTitle("\u591A\u4EBA\u804A\u5929\u5BA2\u6237\u7AEF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 559);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);
		
		this.panel = new JPanel();
		this.panel.setBounds(10, 10, 591, 60);
		this.panel.setLayout(null);
		this.panel.setBorder(new TitledBorder(null, "\u767B\u5F55\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.contentPane.add(this.panel);
		
		this.lblNewLabel = new JLabel("\u670D\u52A1\u5668IP\uFF1A");
		this.lblNewLabel.setBounds(10, 24, 80, 26);
		this.panel.add(this.lblNewLabel);
		
		this.textIP = new JTextField();
		this.textIP.setText("192.168.6.14");
		this.textIP.setColumns(10);
		this.textIP.setBounds(70, 27, 114, 21);
		this.panel.add(this.textIP);
		
		this.lblNewLabel_1 = new JLabel("\u7AEF\u53E3");
		this.lblNewLabel_1.setBounds(194, 24, 58, 26);
		this.panel.add(this.lblNewLabel_1);
		
		this.textPort = new JTextField();
		this.textPort.setText("1998");
		this.textPort.setColumns(10);
		this.textPort.setBounds(225, 27, 47, 21);
		this.panel.add(this.textPort);
		
		this.btnLogin = new JButton("\u767B\u5F55");
		this.btnLogin.addActionListener(new BtnLoginActionListener());
		this.btnLogin.setBounds(411, 24, 80, 23);
		this.panel.add(this.btnLogin);
		
		this.btnClose = new JButton("\u9000\u51FA");
		this.btnClose.addActionListener(new BtnCloseActionListener());
		this.btnClose.setBounds(501, 24, 80, 23);
		this.panel.add(this.btnClose);
		
		this.lblNewLabel_2 = new JLabel("\u7528\u6237\u540D");
		this.lblNewLabel_2.setBounds(274, 24, 58, 26);
		this.panel.add(this.lblNewLabel_2);
		
		this.textUser = new JTextField();
		this.textUser.setColumns(10);
		this.textUser.setBounds(322, 27, 80, 21);
		this.panel.add(this.textUser);
		
		this.scrollPaneLog = new JScrollPane();
		this.scrollPaneLog.setViewportBorder(new TitledBorder(null, "\u804A\u5929\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.scrollPaneLog.setBounds(10, 80, 421, 368);
		this.contentPane.add(this.scrollPaneLog);
		
		this.textLog = new JTextArea();
		this.scrollPaneLog.setViewportView(this.textLog);
		
		this.scrollPaneUser = new JScrollPane();
		this.scrollPaneUser.setViewportBorder(new TitledBorder(null, "\u5728\u7EBF\u7528\u6237", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.scrollPaneUser.setBounds(441, 80, 153, 368);
		this.contentPane.add(this.scrollPaneUser);
		
		itemUsers = new DefaultListModel<String>();
		this.listUser = new JList(itemUsers);
		this.listUser.setToolTipText("");
		this.scrollPaneUser.setViewportView(this.listUser);
		
		this.panel_1 = new JPanel();
		this.panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.panel_1.setBounds(10, 459, 584, 60);
		this.contentPane.add(this.panel_1);
		this.panel_1.setLayout(null);
		
		this.textSend = new JTextField();
		this.textSend.setColumns(10);
		this.textSend.setBounds(10, 28, 385, 21);
		this.panel_1.add(this.textSend);
		
		this.btnSendAll = new JButton("\u7FA4\u53D1");
		this.btnSendAll.addActionListener(new BtnSendAllActionListener());
		this.btnSendAll.setBounds(404, 27, 80, 23);
		this.panel_1.add(this.btnSendAll);
		
		this.btnSend1 = new JButton("\u53D1\u9001");
		this.btnSend1.addActionListener(new BtnSend1ActionListener());
		this.btnSend1.setBounds(494, 27, 80, 23);
		this.panel_1.add(this.btnSend1);
	}
	ClientSocketThread sthd = null;
	Socket socket = null;
	private class BtnLoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String IP = textIP.getText().trim();
			int Port = Integer.parseInt(textPort.getText().trim());
			String user=textUser.getText().trim();
			
			if (ClientMG.getClientMG().Connect(IP, Port, user)) {
				ClientMG.getClientMG().setLogTxt("已经连接到服务器...");
			}else {
				ClientMG.getClientMG().setLogTxt("连接服务器失败！");
			}
		}
	}
	private class BtnCloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//推出连接
			//向服务器发送下线消息
			String SendName=ClientMG.getClientMG().getClientSocketThd().getName();
			String strSend="OFFLINE|"+SendName;
			ClientMG.getClientMG().getClientSocketThd().sendMSG(strSend);
			//关闭Sth，清空用户列表
			ClientMG.getClientMG().clearItems();
			ClientMG.getClientMG().setLogTxt("已经断开连接");
		}
	}
	private class BtnSend1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(listUser.getSelectedIndex()>=0 && !textSend.getText().trim().equals("")) {
				String SendName=ClientMG.getClientMG().getClientSocketThd().getName();
				String RecName=listUser.getSelectedValue().toString();
				String MSGinfo=textSend.getText().trim();
				//String sendMsg="MSG|"+SendName+"|"+RecName+"|"+MSGinfo;
				String sendMsg="MSG|{0}|{1}|{2}";
				Object [] params= {
						SendName,
						RecName,
						MSGinfo
				};
				sendMsg=MessageFormat.format(sendMsg, params);
				ClientMG.getClientMG().getClientSocketThd().sendMSG(sendMsg);
				
				ClientMG.getClientMG().setLogTxt("[我]");
				ClientMG.getClientMG().setLogTxt(MSGinfo);
				textSend.setText("");
			}
		}
	}
	private class BtnSendAllActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String SendName=ClientMG.getClientMG().getClientSocketThd().getName();
			String RecName="ALL";
			String MSGinfo=textSend.getText().trim();
			
			String sendMsg="MSG|"+SendName+"|"+RecName+"|"+MSGinfo;
			ClientMG.getClientMG().getClientSocketThd().sendMSG(sendMsg);
			
			ClientMG.getClientMG().setLogTxt("[我]");
			ClientMG.getClientMG().setLogTxt(MSGinfo);
			textSend.setText("");
		}
	}
}
