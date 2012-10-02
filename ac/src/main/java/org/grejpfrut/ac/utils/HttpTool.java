package org.grejpfrut.ac.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * HttpClient wrapper.
 * 
 * @author ad
 * 
 */
public class HttpTool {

	private final Map<String, String> httpHeader = new HashMap<String, String>();

	private final HttpClient client = new HttpClient();

	private final static Logger logger = Logger.getLogger(HttpTool.class);


	/**
	 * Reads header information from properties file Everything that starts with
	 * "http." will be added to http header.
	 * 
	 * @param conf
	 */
	public HttpTool(Properties conf) {

		for (Object key : conf.keySet()) {
			if (key.toString().startsWith("http")) {
				httpHeader.put(key.toString().substring(5), conf.getProperty(key.toString()));
			}
		}

		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(3000);
	}

	/**
	 * Connects to given url, and reads response using given output encoding.
	 * 
	 * @param link
	 * @param outputEncoding
	 * @return response string
	 */
	public byte[] get(String link, String outputEncoding) {

		final HttpMethod get = addHeader(new GetMethod(link));
		get.setFollowRedirects(true);
		String result = null;
		byte[] bytes;
		try {
			client.executeMethod(get);
			final byte[] responseBody = get.getResponseBody();
			result = new String(responseBody, outputEncoding);
			bytes = result.getBytes(outputEncoding); 
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			get.releaseConnection();
		}
		return bytes;

	}

	private HttpMethod addHeader(HttpMethod method) {

		for (Entry<String, String> entry : httpHeader.entrySet()) {
			method.setRequestHeader(entry.getKey(), entry.getValue());
		}
		return method;
	}

}
