package com.github.swingomatic.tc.http;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientWrapper {

	/** The Constant ENCONDING. */
	private static final String ENCONDING = HTTP.DEFAULT_CONTENT_CHARSET;

	/** The Constant logger. */
	private static final Logger LOGGER = Logger
			.getLogger(HttpClientWrapper.class.getName());

	/**
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String post(final String url, final String data) throws Exception {

		HttpClient client = null;
		String response = null;

		try {

			LOGGER.log(Level.INFO, "Posting data to: " + url);

			client = new DefaultHttpClient();
			// Setup the request headers
			client.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost post = new HttpPost(url);

			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			// For File parameters
			if (StringUtils.isNotEmpty(data)) {
				ByteArrayBody bab = new ByteArrayBody(data.getBytes(), "");
				entity.addPart("", bab);
				post.setEntity(entity);
			}

			// Here we go!
			response = EntityUtils.toString(client.execute(post).getEntity(),
					ENCONDING);

			return response;

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error occured posting request", e);
			throw e;
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE,
						"Error occured shutting down HTTP client", e);
				/* No Op */
			}
		}

	}

}
