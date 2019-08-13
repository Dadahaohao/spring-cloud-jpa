package com.bwei.haohao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.buf.StringUtils;
import com.github.pagehelper.util.StringUtil;

public final class DigestUtil {
	
	private static final String METHOD="POST";
	
	/**
	 * response生成规则
	 * 
	 * param1 = MD5((userName):(realm):(password)) userName reslm
	 * password以：拼接，再使用MD5加密
	 * 
	 * param2 = (nonce):(nc):(cnonce):(qop) nonce nc cnonce qop以：拼接
	 * 
	 * param3 = MD5(POST:(url)) POST url以：拼接，再使用MD5加密
	 * 
	 * response = MD5(param1:param2:param3) param1 param2 param3以：拼接，再使用MD5加密
	 * 
	 * @throws NoSuchAlgorithmException
	 * 
	 * 
	 */
	public String generateResponse(String userName, String realm, String nonce, String url, String qop, String nc,
			String cnonce, String password, byte[] body) throws NoSuchAlgorithmException {

		String param1 = "";// param1 = MD5((userName):(realm):(password)) userName
							// reslmpassword以：拼接，再使用MD5加密
		String param2 = "";// param2 = (nonce):(nc):(cnonce):(qop) nonce nc cnonce qop以：拼接
		String param3 = "";// param3 = MD5(POST:(url)) POST url以：拼接，再使用MD5加密

		//去除括号
		String NameRealmPass=userName.replace("\"", "")+":"+realm.replace("\"", "")+":"+password.replace("\"", "");
		param1=this.md5(NameRealmPass);
		
		//去掉所有的  括号
		param2 =  nonce.replace("\"", "")+":"+nc.replace("\"", "")+":"+cnonce.replace("\"", "")+":"+qop.replace("\"", "");
		
		//DigestUtil.md5(firstA2(qop, url, body));
		String firstA2 = "";
		
		if("auth".equals(qop)) {
			firstA2=METHOD+":"+url;
		}else if("auth-int".equals(qop)) {
			String entityBoby="";
			try {
				entityBoby= new String(body,"utf-8");
			} catch (Exception e) {
				// TODO: handle exception
				entityBoby=new String(body);
			}
			firstA2=METHOD+":"+url+":"+this.md5(entityBoby);
		}
		param3=this.md5(firstA2);
		//去括号并且 添加到一起
		
		//DigestUtil.md5(firstA2(qop, url, body));
		
		System.out.println("param1========"+param1);
		System.out.println("param2========"+param1);
		System.out.println("param3========"+param1);

		String lastString = param1+":"+param2+":"+param3;
		return this.md5(lastString);
	}
	
	/**
	 * MD5加密算法
	 * @param string
	 * 
	 * NumberConstants  --------------我没有找到这个包
	 * 
	 * @return
	 */
	public static String md5(String string) {
		
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		 try
	        {
	            byte[] strTemp = string.getBytes("utf-8");
	            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	            mdTemp.update(strTemp);
	            byte[] md = mdTemp.digest();
	            int j = md.length;
	            char str[] = new char[j * NumberConstants.INT_2];
	            int k = 0;
	            for (int i = 0; i < j; i++)
	            {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> NumberConstants.INT_4 & NumberConstants.NUM_0XF];
	                str[k++] = hexDigits[byte0 & NumberConstants.NUM_0XF];
	            }
	            return new String(str);
	        }
	        catch (Exception e)
	        {
	            LOG.error("MD5 error! the input param is ", e);
	            return null;
	        }
		
		
		return "";
	}

}
