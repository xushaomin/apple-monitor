package com.appleframework.monitor.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * http请求
 */
public class HttpsUtils {

	/**
	 * 忽视证书HostName
	 */
	private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
		public boolean verify(String s, SSLSession sslsession) {
			return true;
		}
	};	

	/**
	 * Ignore Certification
	 */
	private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {
		
		private X509Certificate[] certificates;

		@Override
		public void checkClientTrusted(X509Certificate certificates[],
				String authType) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = certificates;
			}
		}

		@Override
		public void checkServerTrusted(X509Certificate[] ax509certificate,
				String s) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = ax509certificate;
			}
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};
	
	/** 
	 * 发起https请求并获取结果 
	 * @param requestUrl 请求地址 
	 * @param requestMethod 请求方式（GET、POST） 
	 * @param outputStr 提交的数据 
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
	 * @throws IOException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
		//JSONObject jsonObject = null;
		String result = "";
		HttpsURLConnection httpUrlConn = null ;
		BufferedReader bufferedReader = null ;
		InputStream inputStream = null ;
		InputStreamReader inputStreamReader = null ;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化  
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象  
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）  
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时  
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码  
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串  
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			result = buffer.toString();
		}finally{
			if (httpUrlConn != null) {
				httpUrlConn.disconnect();
			}
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
			if(inputStreamReader!=null){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
				}
			}
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}


	public static String getMethod(String urlString) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
		HttpsURLConnection connection = null;
		try {
			URL url = new URL(urlString);
			/*
			 * use ignore host name verifier
			 */
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setUseCaches(false);
			// Prepare SSL Context
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);

			InputStream reader = connection.getInputStream();
			byte[] bytes = new byte[512];
			int length = reader.read(bytes);
			do {
				buffer.write(bytes, 0, length);
				length = reader.read(bytes);
			} while (length > 0);
			
			return new String(buffer.toByteArray());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}