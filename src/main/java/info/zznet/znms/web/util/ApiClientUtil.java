package info.zznet.znms.web.util;

import info.zznet.znms.base.common.ZNMSLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class ApiClientUtil {
	// 接口地址
		private String apiURL = "";
		private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
		private HttpClient httpClient = null;
		private HttpPost method = null;
		private long startTime = 0L;
		private long endTime = 0L;
		private int status = 0;
				
		/**
		 * 接口地址
		 * 
		 * @param url
		 */
		public ApiClientUtil(String url) {
			int timeout = 20000;

			if (url != null) {
				this.apiURL = url;
			}
			if (apiURL != null) {
				httpClient = new DefaultHttpClient();
				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
				method = new HttpPost(apiURL);
			}
		}

		/**
		 * 调用 API
		 * 
		 * @param parameters
		 * @return
		 */
		public String post(Map<String,String> paramMap) throws IOException{
			String body = null;		
			if (method != null) {
				try {
					List params = new ArrayList();
					if(paramMap != null){
						for(String key : paramMap.keySet()){
							String value = paramMap.get(key);
							params.add(new BasicNameValuePair(key , value));					
						}
					}
					// 添加参数
					method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
					
					startTime = System.currentTimeMillis();
					// 设置编码
					HttpResponse response = httpClient.execute(method);
					endTime = System.currentTimeMillis();
					int statusCode = response.getStatusLine().getStatusCode();
					ZNMSLogger.debug("statusCode:" + statusCode);
					ZNMSLogger.debug("call api take time：" + (endTime - startTime));
					if (statusCode != HttpStatus.OK.value()) {
						ZNMSLogger.debug("Method failed:" + response.getStatusLine());
						status = 1;
					}
					// Read the response body
					body = EntityUtils.toString(response.getEntity());
				} catch (IOException e) {
					// 发生网络异常
					ZNMSLogger.debug("exception occurred!\n"
							+ ExceptionUtils.getFullStackTrace(e));
					// 网络错误
					status = 3;
					throw e;
				} finally {
					ZNMSLogger.debug("api status：" + status);
					method.releaseConnection();
				}

			}
			if(StringUtils.isEmpty(body)){
				throw new IOException("server response none data");
			}

			try {
				return body;
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		
		
		/**
		 * 调用 API,设置头部信息
		 * @param parameters
		 * @param headers
		 * @return
		 */
		public void doPost(Map<String,String> paramMap, Header[] headers) throws IOException{
			String body = null;		
			if (method != null) {
				try {
					List params = new ArrayList();
					if(paramMap != null){
						for(String key : paramMap.keySet()){
							String value = paramMap.get(key);
							params.add(new BasicNameValuePair(key , value));					
						}
					}
					// 添加参数
					method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
					method.setHeaders(headers);
					startTime = System.currentTimeMillis();
					// 设置编码
					HttpResponse response = httpClient.execute(method);
					endTime = System.currentTimeMillis();
					int statusCode = response.getStatusLine().getStatusCode();
					ZNMSLogger.debug("statusCode:" + statusCode);
					ZNMSLogger.debug("call api take time：" + (endTime - startTime));
					if (statusCode != HttpStatus.OK.value()) {
						ZNMSLogger.debug("Method failed:" + response.getStatusLine());
						status = 1;
					}
					// Read the response body
//					body = EntityUtils.toString(response.getEntity());
				} catch (IOException e) {
					// 发生网络异常
					ZNMSLogger.debug("exception occurred!\n"
							+ ExceptionUtils.getFullStackTrace(e));
					// 网络错误
					status = 3;
					throw e;
				} finally {
					ZNMSLogger.debug("api status：" + status);
					method.releaseConnection();
				}

			}
//			if(StringUtils.isEmpty(body)){
//				throw new IOException("server response none data");
//			}
//
//			try {
//				return body;
//			} catch (Exception e) {
//				throw new IOException(e);
//			}
		}
		

		/**
		 * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
		 * 
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}

		/**
		 * @param status
		 *            the status to set
		 */
		public void setStatus(int status) {
			this.status = status;
		}

		/**
		 * @return the startTime
		 */
		public long getStartTime() {
			return startTime;
		}

		/**
		 * @return the endTime
		 */
		public long getEndTime() {
			return endTime;
		}
}
