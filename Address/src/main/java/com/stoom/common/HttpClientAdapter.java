package com.stoom.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Class that implements the Adapter pattern for Commons HttpClient
 * 
 * @author Rodrigo
 *
 */
public class HttpClientAdapter {

    private Log logger = LogFactory.getLog(getClass());
    private Log traceLogger = LogFactory.getLog("RequestResponseTrace");

    private final String url;
    private final Map<String, String> headers = new HashMap<>();
    
    private int status;

    public HttpClientAdapter(String url) {
        super();
        this.url = url;
    }

    public HttpClientAdapter withHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public String post(String body) {
        return post(body, null);
    }

    public String post(String body, String contentType) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }

            StringEntity entity = new StringEntity(body);
            if (StringUtils.isNotBlank(contentType)) entity.setContentType(contentType);
            httpPost.setEntity(entity);
            
            Timer timer = new Timer();
            timer.start();
            CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
            timer.stop();

            
            this.status = httpResponse.getStatusLine().getStatusCode();
            String response =  readStream(httpResponse);

            CallTrace callResults = new CallTrace().forUrl(this.url);
            callResults.setSentPayload(body);
            callResults.setSentHeaders(headers);
            callResults.setReceived(this.status, response);
            callResults.setTimeAndDuration(timer.getStart(), timer.getInterval());
            callResults.setReceivedHeaders(httpResponse.getAllHeaders());
            traceLogger.info(callResults);

            return response;

        } catch (IOException e) {
            logger.error(e);
            this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
            return e.getMessage();
        }
    }

    
    public String put(String body, String contentType) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.addHeader(entry.getKey(), entry.getValue());
            }

            StringEntity entity = new StringEntity(body);
            if (StringUtils.isNotBlank(contentType)) entity.setContentType(contentType);
            httpPut.setEntity(entity);
            
            Timer timer = new Timer();
            timer.start();
            CloseableHttpResponse httpResponse = httpclient.execute(httpPut);
            timer.stop();

            
            this.status = httpResponse.getStatusLine().getStatusCode();
            String response =  readStream(httpResponse);

            CallTrace callResults = new CallTrace().forUrl(this.url);
            callResults.setSentPayload(body);
            callResults.setSentHeaders(headers);
            callResults.setReceived(this.status, response);
            callResults.setTimeAndDuration(timer.getStart(), timer.getInterval());
            callResults.setReceivedHeaders(httpResponse.getAllHeaders());
            traceLogger.info(callResults);

            return response;

        } catch (IOException e) {
            logger.error(e);
            this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
            return e.getMessage();
        }
    }

    
    public String get() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpget.addHeader(entry.getKey(), entry.getValue());
            }

            CloseableHttpResponse response = httpclient.execute(httpget);
            this.status = response.getStatusLine().getStatusCode();

            return readStream(response);

        } catch (IOException e) {
            logger.error(e);
            this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
            return e.getMessage();
        }
    }

    public String delete() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpDelete httpdelete = new HttpDelete(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpdelete.addHeader(entry.getKey(), entry.getValue());
            }

            CloseableHttpResponse response = httpclient.execute(httpdelete);
            this.status = response.getStatusLine().getStatusCode();

            return readStream(response);
            
        } catch (IOException e) {
            logger.error(e);
            this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
            return e.getMessage();
        }
    }

	private String readStream(CloseableHttpResponse response) throws IOException {
		String result = "";
		try (Scanner scanner = new Scanner(response.getEntity().getContent())) {
		    result = scanner.useDelimiter("\\A").next();
		} catch (NoSuchElementException e) {
			// return empty response
		}
		return result;
	}

    public int getHttpStatus() {
    	return status;
    	
    }
}
