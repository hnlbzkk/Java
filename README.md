# Java
适合小白
一个简单的Java网络编程-多人聊天
使用WindowBuilder


分为两个：Client-Server
可运行文件-
          Client：ClientForm     Server：ServerForm


ClientMG：管理类，管理Socket建立连接等（Server同）
Thread：通过 前缀 ：如“LOGIN|xxx”、“ADD|xxx”、“MSG|xxx|xxx”主要用String.split()作以区分，提取不同数据部分展示



# 多人聊天的流程如下：
一、用户登录
    客户端：
         1、发送登录信息：LOGIN|Username    -OK-
	    处理USERLISTS命令：所有在线用户的用户名   -OK-
         2、处理新上线用户信息：ADD|username  -OK-

    服务器端： 
	 1、得到所有在线用户信息名称，发回给客户端：USERLISTS|user1_user2_user3    -OK -
         2、将当前登录的Socket信息放入Arraylist中      -OK-
         3、将当前登录用户的信息（用户名），发送给已经在线的其他用户：ADD|userName  -OK-

二、两两私聊
    客户端：
	  1、选择用户    -OK-
          2、组织一个交互协议，发送到服务器：MSG|SenderName|RecName|MSGInfo   -OK-
          3、接收服务器转发回的消息;MSG|SenderName|MSGInfo   -OK-
    服务器端：
	  1、通过RecName用户名查找，找到目标SocketThread    -OK-
	  2、向目标对象发送消息：MSG|SenderName|MSGInfo    -OK-

三、群聊
	MSG｜Sendername|ALL|MSGInfo   -OK-

四、用户退出连接
    客户端：
	  1、向服务器发送下线消息：OFFLINE|username
	  2、关闭ClientChat，清空在线用户列表

          3、处理下线用户信息：DEL|username
             删除用户列表中的username
    
    服务器端：
          1、处理OFFLINE
	  2、向所有的其他在线用户发送用户下线消息：DEL|username
	  3、清除ArrayList中的当前用户信息（socketChat）

五、服务器关闭
　　客户端：
	　１、处理CLOSE命令：界面显示服务器关闭
	   2、关闭ClientChat
           3、清空在线用户列表


    服务器端：
	  1、向所有在线用户发送关闭服务器的信息：CLOSE
	  2、遍历Arraylist将其中的每一个SocketChat关闭
	  3、ArrayList要清空
          4、关闭SokcetListener
	  5、关闭ServerSocket

六、文件传输
    客户端：
	  1、选择待传输的文件，File类获取文件的相关信息（文件名、文件长度）
	  2、创建临时的文件传输用服务器（serverSocket）,新开一个线程。
	  3、发送文件相关信息和文件服务器相关信息到聊天服务器：
	     FILETRANS|sendername|recName|文件名|文件长度|IP|Port

	  4、处理服务器转发的FILETRANS命令：显示文件信息，询问接收用户是否接收文件？JOptoinPane->confirm
	  5、同意接收时，根据IP和Port连接到文件传输服务器，新开线程进行文件传输操作
          6、拒绝接收时，向聊天服务器发送拒绝接收文件消息：  FILECANCEL|sendername|recname

          7、处理FILECANCEL，关闭文件传输用的serversocket
    服务器端：
	  1、处理FILETRANS命令，根据RecName查找SocketChat对象，
 	  2、转发文件传输的相关信息：
             FILETRANS|sendername|文件名|文件长度|IP|Port
          
          3、处理客户端拒绝接收文件的命令（FILECANCEL|sendername|recname），查找文件传输发起者的Socketchat,发送FILECANCEL

# 功能：五子棋对战流程：

客户端
发送LOGIN|name
 
监听：GAMEREADY|NAME

发送和监听：  SETPOS|x|y|chessType

监听：WIN|name



服务器端

监听LOGIN|name

发送 GAMEREADY|NAME


发送和监听：  SETPOS|x|y|chessType

判断赢： WIN|name


















