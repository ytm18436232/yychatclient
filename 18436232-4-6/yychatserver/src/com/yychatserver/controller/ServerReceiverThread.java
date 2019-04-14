package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{
	Socket s;
	public ServerReceiverThread(Socket s){
		this.s=s;
	}
	
	public void run(){
		ObjectInputStream ois;
		ObjectOutputStream oos;
		Message mess;   
		while(true){
		try {
			ois = new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();//����������Ϣ
			System.out.println(mess.getSender()+"��"+mess.getReceiver()+"˵��"+mess.getContent());
			
			if(mess.getMessageType().equals(Message.message_Common)){
				Socket s1=(Socket)StartServer.hsmSocket.get(mess.getReceiver());
				oos=new ObjectOutputStream(s1.getOutputStream());
				oos.writeObject(mess);//ת��������Ϣ
			}
			//�ڶ��������������յ�������������ߺ�����Ϣ(���ͣ�message_OnlineFriend)
			if(mess.getMessageType().equals(Message.message_RequestOnlineFriend)){
				Set friendSet=StartServer.hsmSocket.keySet();
				//Iterator it=friendSet.iterator();
				java.util.Iterator it=friendSet.iterator();
				String friendName;
				String friendString=" ";
				while(it.hasNext()){
					friendName=(String)it.next();
					friendString=friendString+friendName+" ";
				}
				System.out.println("ȫ�����ѵ�����:"+friendString);
				//����ȫ�����ѵ����ֵ��ͻ���
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		}
	}
}