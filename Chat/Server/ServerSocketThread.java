package homeWork10.Chat.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import homeWork10.Chat.Client.ClientMG;

public class ServerSocketThread extends Thread {

	//初始化
	BufferedReader bReader = null;
	PrintWriter pWriter = null;
	Socket socket;
	public ServerSocketThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			bReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));
			
			
			String string = "";
			while ((string=bReader.readLine()) != null) {
				String[] commands=string.split("\\|");
				if (commands[0].equals("LOGIN")) {
					String user=commands[1];
					this.setName(user);
					//1.得到所有用户上线信息
					ServerMG.getServerMG().getOnlineNames(this);
					//2.将当前登录用户的信息发送给已经在线的其他用户
					ServerMG.getServerMG().sendNewUsertoAll(this);
					//3.将当前登录的Socket信息放入ArrayList
					ServerMG.getServerMG().addList(this);
					
					
				}else if (commands[0].equals("MSG")) {
					//"MSG|"+SendName+"|"+RecName+"|"+MSGinfo
					String SendName=commands[1];
					String RecName=commands[2];
					String MSGinfo=commands[3];
					if (RecName.equals("ALL")) {
						String AMsg="MSG|"+SendName+"|"+MSGinfo;
						ServerMG.getServerMG().sendMsgtoAll(AMsg, this);
						
						ServerMG.getServerMG().setTextLog(SendName+"发送消息["+MSGinfo+"]到所有人.");
					}else {
					//私聊：通过RecName用户名查找，找到目标ServerSocketThread对象
					ServerSocketThread st=ServerMG.getServerMG().getServerSocketThreadByName(RecName);
					if (st!=null) {
						String SMsg="MSG|"+SendName+"|"+MSGinfo;
						st.sendMSG(SMsg);
						
						ServerMG.getServerMG().setTextLog(SendName+"发送消息["+MSGinfo+"]到"+RecName);
						}
					}
				}else if (commands[0].equals("OFFLINE")) {
					//处理OFFLINE
					String username=commands[1];
					//向所有其他用户发送该用户下线消息：DEL|username
					ServerMG.getServerMG().sendOfflineUsertoAll(this);
					//清除ArrayList中的当前用户
					ServerMG.getServerMG().removeList(this);
					//socket退出
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
