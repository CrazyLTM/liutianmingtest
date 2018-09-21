package com.Tomcat.main;



import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.Tomcat.Request.RequestContent;
import com.Tomcat.Start.Start;

public class TomcatMain {
	
	public static void main(String[] args) throws Exception {
		
		//��ʼ��start��
		Start start=new Start();
		//��ʼ��requestcontent
		RequestContent requestContent=new RequestContent();
		//��socket �˿�
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(15389);
		while(true) {
		Socket client = ss.accept();
		//���̲߳���
		Thread thread=new Thread(()-> {
			InputStream iStream;
			OutputStream oStream;
			try {
				iStream = client.getInputStream();
				oStream = client.getOutputStream();
				
				//����Start������istreamΪ������
				byte[] bytes=start.init(iStream);
				//�õ�URL����
				String url=requestContent.requestContent(bytes);
				//�ж�URL����
				String content=requestContent.requestResourceJudge(url);
				oStream.write(content.getBytes());
				iStream.close();
				oStream.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		thread.start();
		}
	

	}

	

}
