package homeWork10.Chat.Client;

import java.awt.Component;
import java.net.Socket;

public class ClientMG {

	
	private static final ClientMG clientmg = new ClientMG();
	private ClientMG() {}
	public static ClientMG getClientMG() {
		return clientmg;
	}
	
	private ClientForm clientForm;
	ClientSocketThread sThd;
	
	public void setClientForm(ClientForm clientForm) {
		this.clientForm = clientForm;
	}
	public void setLogTxt(String str) {
		clientForm.textLog.append(str+"\r\n");
	}
	public void addItems(String [] users) {
		//添加到客户端的List控件中
		for(int i=0;i<users.length;i++) {
			addItems(users[i]);
		}
	}
	public void addItems(String user) {
		// TODO Auto-generated method stub
		clientForm.itemUsers.addElement(user);
	}
	public void clearItems() {
		clientForm.itemUsers.clear();
	}
	public void removeItem(String username) {
		clientForm.itemUsers.removeElement(username);
	}
	
	public boolean Connect(String IP,int port,String user) {
		Socket socket = null;
		try {
			socket = new Socket(IP,port);
			sThd = new ClientSocketThread(socket,user);
			sThd.start();
			return true;
			} catch (Exception e) {
			// TODO: handle exception
				e.printStackTrace();
				return false;
		}
	}
	public ClientSocketThread getClientSocketThd() {
		// TODO Auto-generated method stub
		return sThd;
	}
	
	
}
