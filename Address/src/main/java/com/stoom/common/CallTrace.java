package com.stoom.common;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.springframework.http.HttpHeaders;

/**
 * Helper class to generate logging in {@link HttpClientAdapter}.
 * @author Rodrigo
 *
 */
public class CallTrace {

    private String url;
    private String sentPayload;
    private Map<String, String> sentHeaders = new HashMap<>();

    private String receivedPayload;
    private Map<String, String> receivedHeaders = new HashMap<>();
    private int receivedStatus;

    private Instant requestTime;
    private Duration fetchDuration;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getSentPayload() {
        return sentPayload;
    }
    public void setSentPayload(String sentPayload) {
        this.sentPayload = sentPayload;
    }
    public Map<String, String> getSentHeaders() {
        return sentHeaders;
    }
    public void setSentHeaders(Map<String, String> sentHeaders) {
        this.sentHeaders = sentHeaders;
    }
    public String getReceivedPayload() {
        return receivedPayload;
    }
    public void setReceivedPayload(String receivedPayload) {
        this.receivedPayload = receivedPayload;
    }
    public Map<String, String> getReceivedHeaders() {
        return receivedHeaders;
    }
    public void setReceivedHeaders(Map<String, String> receivedHeaders) {
        this.receivedHeaders = receivedHeaders;
    }
    public int getReceivedStatus() {
        return receivedStatus;
    }
    public void setReceivedStatus(int receivedStatus) {
        this.receivedStatus = receivedStatus;
    }
    public Instant getRequestTime() {
        return requestTime;
    }
    public void setRequestTime(Instant requestTime) {
        this.requestTime = requestTime;
    }
    public Duration getFetchDuration() {
        return fetchDuration;
    }
    public void setFetchDuration(Duration fetchDuration) {
        this.fetchDuration = fetchDuration;
    }

    @Override
    public String toString() {
            String string = "{\n"
                    + "  \"url\":" + url + ",\n"
                    + "  \"sentPayload\":" + sentPayload + ",\n"
                    + "  \"sentHeaders\":" + sentHeaders + ",\n"
                    + "  \"receivedPayload\":" + receivedPayload + ",\n"
                    + "  \"receivedHeaders\":" + receivedHeaders + ",\n"
                    + "  \"receivedStatus\":" + receivedStatus + ",\n"
                    + "  \"requestTime\":" + requestTime + ",\n"
                    + "  \"fetchDuration\":" + fetchDuration + "\n}";//.replace("{", "{\n").replace(",", ",\n").replace("}", "\n}");
            return string;
    }
    public CallTrace forUrl(String url) {
        this.url = url;
        return this;
    }
    public void setReceived(int statusCodeValue, String receivedPayload) {
        this.receivedStatus = statusCodeValue;
        this.receivedPayload = receivedPayload;
    }
    public void setTimeAndDuration(Instant start, Duration duration) {
        this.requestTime = start;
        this.fetchDuration = duration;
        
    }
    public void setReceivedHeaders(HttpHeaders responseHeaders) {
        for (String key: responseHeaders.keySet()) {
            for (String value : responseHeaders.get(key)) {
                receivedHeaders.put(key, value);
            }
        }
    }
    public void setReceivedHeaders(Header[] allHeaders) {
        for (Header header : allHeaders) {
            sentHeaders.put(header.getName(), header.getValue());
        }
    }
}
