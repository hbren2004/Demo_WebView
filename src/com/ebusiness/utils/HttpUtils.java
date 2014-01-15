package com.ebusiness.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import android.util.Log;

public class HttpUtils {

	private static String TAG = "HttpUtils";
	private static final int CONTIMEOUT = 5000;
	private static final int SOTIMEOUT = 10000;
	private static int retryCount = 3;

	public static HttpResponse execute(String url,String data,int connectionTimeOut,int soTimeOut)throws Exception{
		HttpResponse httpResponse = null;
		if(url != null){
			HttpParams httpParameters = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParameters, "UTF-8");
			HttpConnectionParams.setStaleCheckingEnabled(httpParameters, false);
			HttpConnectionParams.setConnectionTimeout(httpParameters,connectionTimeOut);
			HttpConnectionParams.setSoTimeout(httpParameters, soTimeOut);
			HttpConnectionParams.setSocketBufferSize(httpParameters, 1024);
			HttpClientParams.setRedirecting(httpParameters, false);
			DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
			httpclient.setHttpRequestRetryHandler(requestRetryHandler);
			if (data != null && !"".equals(data)) {
				HttpPost httpRequest = new HttpPost(url);
				HttpEntity requestBodies = new StringEntity(data, "UTF-8");
				httpRequest.setEntity(requestBodies);
				Log.i("postMsg", EntityUtils.toString(httpRequest.getEntity()));
				httpResponse = httpclient.execute(httpRequest);
			} else {
				HttpGet httpRequest = new HttpGet(url);
				httpResponse = httpclient.execute(httpRequest);
			}
		}else{
			Log.e(TAG, "The URL is Error ["+url+"]");
		}
		return httpResponse;
	}

	public static HttpResponse execute(String url,List<NameValuePair> params,int connectionTimeOut,int soTimeOut)throws Exception{
		HttpResponse httpResponse = null;
		if (url != null) {
			HttpParams httpParameters = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParameters, "UTF-8");
			HttpConnectionParams.setStaleCheckingEnabled(httpParameters, false);
			HttpConnectionParams.setConnectionTimeout(httpParameters,connectionTimeOut);
			HttpConnectionParams.setSoTimeout(httpParameters, soTimeOut);
			HttpConnectionParams.setSocketBufferSize(httpParameters, 1024);
			HttpClientParams.setRedirecting(httpParameters, false);
			DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
			httpclient.setHttpRequestRetryHandler(requestRetryHandler);
			if (params != null) {
				HttpPost httpRequest = new HttpPost(url);
				httpRequest.setEntity(new UrlEncodedFormEntity(params));
				Log.i("postMsg", EntityUtils.toString(httpRequest.getEntity()));
				httpResponse = httpclient.execute(httpRequest);
			}
		} else {
			Log.e(TAG, "The URL is Error ["+url+"]");
		}
		return httpResponse;
	}
	public static HttpResponse execute(String url, String data)throws Exception {
		return execute(url, data, CONTIMEOUT, SOTIMEOUT);
	}
	public static HttpResponse execute(String url, List<NameValuePair> params)throws Exception {
		return execute(url, params, CONTIMEOUT, SOTIMEOUT);
	}

	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler(){
		public boolean retryRequest(IOException exception, int executionCount,HttpContext context){
			if(executionCount >= retryCount){//Do not retry if over max retry count
				return false;
			}
			if(exception instanceof NoHttpResponseException){//Retry if the server dropped connection on us
				return true;
			}
			if(exception instanceof SSLHandshakeException){//Do not retry on SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest)context.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if(!idempotent){
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}
	};

	/**
	 * 用post的方式发送请求协议，注入回调接口用于处理响应数据
	 * @param url
	 * @param data 请求数据流 支持xml和json类型
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,String data,MsgParams params) throws Exception{
		String eData = URLEncoder.encode(data);
		HttpResponse response = execute(url,eData);
		if(null != response){
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String xml = EntityUtils.toString(response.getEntity());
				Log.i(TAG,"response XMl:"+xml);
				params.dealXML(xml);
				return xml;
			}else{
				Log.i(TAG, "错误号为" + response.getStatusLine().getStatusCode());
			}
		}
		return null;
	}
	/**
	 * 用post的方式发送请求协议，注入回调接口用于处理响应数据
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,MsgParams params) throws Exception  {
		HttpResponse response = execute(url,params.createParams());
		if(null != response){
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String xml = EntityUtils.toString(response.getEntity());
				Log.i(TAG,"response XMl:"+xml);
				params.dealXML(xml);
				return xml;
			}else{
				Log.i(TAG, "错误号为：" + response.getStatusLine().getStatusCode());
			}
		}
		return null;
	}
	/**
	 * 回调接口 确定请求参数以及返回XMl文件
	 */
	public interface MsgParams{
		public List<NameValuePair> createParams(); 
		public void dealXML(String responseXml);
	}
}
