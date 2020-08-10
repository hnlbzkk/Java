# Java
适合小白
一个简单的Java网络编程-多人聊天
使用WindowBuilder


分为两个：Client-Server
可运行文件-
          Client：ClientForm     Server：ServerForm


ClientMG：管理类，管理Socket建立连接等（Server同）
Thread：通过 前缀 ：如“LOGIN|xxx”、“ADD|xxx”、“MSG|xxx|xxx”主要用String.split()作以区分，提取不同数据部分展示
