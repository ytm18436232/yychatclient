package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer {
	ServerSocket ss;
	String userName;
	String passWord;
	public StartServer() {
		try {//�����쳣
			ss=new ServerSocket(3456);
			System.out.println("�������Ѿ�����������3456�˿�");
			while(true) {//?Thread���߳�
			Socket s=ss.accept();//���ܿͻ�����������
			System.out.println("���ӳɹ�:"+s);
			
			//����User����
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			User user=(User)ois.readObject();
			userName=user.getUserName();
			passWord=user.getPassWord();
			System.out.println(userName);
			System.out.println(passWord);
	
			//ʵ��������֤����
			Message mess=new Message();
			mess.setSender("Server");
			mess.setReceiver(userName);
			if(passWord.equals("123456")) {//����Ƚ�
				//���߿ͻ�������֤ͨ������Ϣ�����Դ���Message��
				mess.setMessageType(Message.message_LoginSuccess);//"1"Ϊ��֤ͨ��
			}else {
				mess.setMessageType(Message.message_LoginFailure);//"0"Ϊ��֤��ͨ��
			}
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(mess);
			
			//�����������������Ϣ���ɲ����ԣ������ԣ�Ӧ�ô���һ�������߳�
			new ServerReceiverThread(s).start();//����
			}
			
		} catch (IOException e) {
			e.printStackTrace();//�����쳣
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}