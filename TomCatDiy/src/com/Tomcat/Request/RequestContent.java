package com.Tomcat.Request;



public class RequestContent {
	
	
	RequestHandler requestHandler=new RequestHandler();
	
	 
    //��������Ϣ�н�ȡ�����URL����װ��content��
	public String requestContent(byte[] bytes) {
		String content=new String(bytes);
		
		int begin=content.indexOf(" ");
		int end=content.indexOf(" ", begin+1);
		content=content.substring(begin+2, end);
		return content;
	}
	//�Ƚ�URL���ж��Ǿ�̬��Դ���Ƕ�̬��Դ
	public String requestResourceJudge(String resource) {
		
		if(resource.contains(".")||resource.equals("")){
			 return requestHandler.StaticResourceHandler(resource);
		}else {
			 return requestHandler.DynamicResourceHandler(resource);
		}
		
	}

}
