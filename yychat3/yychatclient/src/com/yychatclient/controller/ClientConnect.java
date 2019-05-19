package com.yychatclient.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;

public class ClientConnect {
	
	//public static Socket s;//静态成员，类变量
	public Socket s;
	
	public static HashMap hmSocket=new HashMap<String,Socket>();
	
	public ClientConnect(){
		try {
			s= new Socket("127.0.0.1",3456);//本机地址，回测地址
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Message loginValidateFromDB(User user){//从数据库登录验证的函数		
		//输入输出流对象，发送对象
		//字节输出流对象 包装 对象输出流对象
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);//发送到服务器端			
			
			//接收验证通过的mess
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();		
			
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				System.out.println(user.getUserName()+" 登录成功！");
				hmSocket.put(user.getUserName(), s);
				new ClientReceiverThread(s).start();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return mess;		
	}
	
	/*public boolean loginValidate(User user){//登录验证
		boolean loginSuccess=false;
		//输入输出流对象，发送对象
		//字节输出流对象 包装 对象输出流对象
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);//发送到服务器端			
			
			//接收验证通过的mess
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();		
			
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				loginSuccess=true;
				System.out.println(user.getUserName()+" 登录成功！");
				hmSocket.put(user.getUserName(), s);
				new ClientReceiverThread(s).start();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loginSuccess;		
	}*/
}
