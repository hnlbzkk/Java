package homeWork10.Chat.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketThread extends Thread {

	BufferedReader bReader = null;
	PrintWriter pWriter = null;
	Socket socket = null;
	
	public ClientSocketThread(Socket socket,String user) {
		super(user);
		this.socket = socket;
	}
	
	public void run() {
		try {
			bReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"))); 
			//发送客户端的用户名信息，发送登录信息
			String strLogin="LOGIN|"+this.getName();
			sendMSG(strLogin);
		
			String string = "";
			while ((string=bReader.readLine()) != null) {
				String[] commands=string.split("\\|");
				if (commands[0].equals("USERLISTS")) {
					//USERLISTS|user1_user2_user3
					String[] users=commands[1].split("\\_");
					ClientMG.getClientMG().addItems(users);
				}
				else if (commands[0].equals("ADD")) {
					//ADD|username
					String user=commands[1];
					ClientMG.getClientMG().addItems(user);
				}
				else if(commands[0].equals("MSG")){
					String SendName=commands[1];
					String MSGinfo=commands[2];
					
					ClientMG.getClientMG().setLogTxt("["+SendName+"]");
					ClientMG.getClientMG().setLogTxt(MSGinfo);
				}
				else if(commands[0].equals("DEL")){
					String username=commands[1];
					ClientMG.getClientMG().removeItem(username);
					ClientMG.getClientMG().setLogTxt(username+"下线了！");
				}else if(commands[0].equals("CLOSE")){
					//关闭clientchat
					//清空在线用户列表
					ClientMG.getClientMG().clearItems();
					ClientMG.getClientMG().setLogTxt("服务器已关闭。");
					break;
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (pWriter!=null) {
					pWriter.close();
				}
				if (bReader!=null) {				
						bReader.close();
					} 
				if (socket!=null) {
					socket.close();
				}
			}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	}	
		public void sendMSG(String string) {
			pWriter.println(string);
			pWriter.flush();
		}
		public void closeChat() {
			try {
				if (pWriter!=null) {
					pWriter.close();
				}
				if (bReader!=null) {				
						bReader.close();
					} 
				if (socket!=null) {
					socket.close();
				}
			}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
}

