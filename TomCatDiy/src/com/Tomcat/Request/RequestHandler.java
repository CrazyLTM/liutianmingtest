package com.Tomcat.Request;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;




public class RequestHandler {
	
	
	private static final String ENTER = "\r\n";
	private static final String SPACE = " ";
	
	//���request����
	HashMap<String, String> mapParam=new HashMap<>();
	
	//URL������������
    String urlmapping;
    
    
	String content="post request";
	 
	
	//��̬��Դ������
	public String StaticResourceHandler(String resource) {
		
		if (resource.equals("")) {
			resource="index.html";
		}
		if(resource.contains("/")) {
			resource=resource.replaceAll("/", "\\\\");
		}
		//�õ�webcontent·��
		String WebContentUrl=System.getProperty("user.dir")+"\\WebContent\\"+resource;
	
		
	    StringBuilder contextText = new StringBuilder();
	    
		InputStream is=null;
		int length=0;
		try {
			is = new FileInputStream(WebContentUrl);
			try {
				length = is.available();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] b = new byte[length];//�����е����ݶ�ȡ������ֽڵ���
			//����һ��int�洢ÿ�ζ�ȡ��������
			int i = 0;
			//����һ����¼�����ı���
			int index = 0;
			//ѭ����ȡÿ������
			try {
				is.read(b);
				//���ֽ�����ת���ַ���
				String fileData=new String(b);
				//context��Ϊ��̬�ļ�������
				contextText=new StringBuilder(fileData);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
		    //���û�ж�Ӧ�ľ�̬�ļ��򷵻�NO found page
			contextText.append("No Found Page");
			StringBuilder sb = new StringBuilder();
			// ͨ��ͷ��begin
			sb.append("HTTP/1.1").append(SPACE).append("200").append(SPACE).append("OK").append(ENTER);
			sb.append("Server:myServer").append(SPACE).append("0.0.1v").append(ENTER);
			sb.append("Date:Sat," + SPACE).append(new Date()).append(ENTER);
			sb.append("Content-Type:text/html;charset=UTF-8").append(ENTER);
			sb.append("Content-Length:").append(contextText.toString().getBytes().length).append(ENTER);
			// ͨ��ͷ��end
			sb.append(ENTER);// ��һ��
			sb.append(contextText);// ���Ĳ���
			return sb.toString();
		}
		
		
		StringBuilder sb = new StringBuilder();
		// ͨ��ͷ��begin
		sb.append("HTTP/1.1").append(SPACE).append("200").append(SPACE).append("OK").append(ENTER);
		sb.append("Server:myServer").append(SPACE).append("0.0.1v").append(ENTER);
		sb.append("Date:Sat," + SPACE).append(new Date()).append(ENTER);
		sb.append("Content-Type:text/html;charset=UTF-8").append(ENTER);
		sb.append("Content-Length:").append(contextText.toString().getBytes().length).append(ENTER);
		// ͨ��ͷ��end
		sb.append(ENTER);// ��һ��
		sb.append(contextText);// ���Ĳ���
		
		return sb.toString();
	
	
		
		
	}

	//��̬��Դ
	public String  DynamicResourceHandler(String resource) {
		//һ��GET�������
		if(resource.contains("?")) {
			int num=resource.indexOf("?");
			//tempΪget����������ַ���
			String temp=resource.substring(num+1);
			//urlmapping Ϊ�����URL
			resource=resource.substring(0, num);
			urlmapping=resource;
			//�������ַ������ͣ���&�ָ����ַ�������
			String[] param=temp.split("&");
			//ѭ��ȥ��ÿ������
			for(String Map_param:param) { 
				//ÿ�������á�=���ŷָ� �����ΪMap_key���ұ�ΪMap_Value
				String[] paString=Map_param.split("=");
				 mapParam.put(paString[0], paString[1]);
			}
			
			try {
				//����ҵ���߼�������
				content=HandlerMapping(urlmapping,mapParam);
				//�жϷ��ؽ����Ⱦָ������ͼ
				if(content.equals("login")) {
					return  StaticResourceHandler("User.html");
				}
				if(content.equals("redirect")) {
					return StaticResourceHandler("Error.html");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StringBuilder sb = new StringBuilder();
		// ͨ��ͷ��begin
		sb.append("HTTP/1.1").append(SPACE).append("200").append(SPACE).append("OK").append(ENTER);
		sb.append("Server:myServer").append(SPACE).append("0.0.1v").append(ENTER);
		sb.append("Date:Sat," + SPACE).append(new Date()).append(ENTER);
		sb.append("Content-Type:text/html;charset=UTF-8").append(ENTER);
		sb.append("Content-Length:").append(content.toString().getBytes().length).append(ENTER);
		// ͨ��ͷ��end
		sb.append(ENTER);// ��һ��
		sb.append(content);// ���Ĳ���
		return sb.toString();
	}

    //���÷��䣬���������URL���þ���ķ���
	public String HandlerMapping(String mapping,HashMap<String , String> map) throws Exception {
		
		 Class<?> Clazz =  Class.forName("com.Tomcat.MethodMapping.MappingHandler");
		 
		 Object object=Clazz.newInstance();
         //��ȡ����
         Method Method = Clazz.getMethod(mapping,HashMap.class);
         //����
         String HandlerResult=  (String) Method.invoke(object,map);
		
	     return HandlerResult;
		}
	


}
