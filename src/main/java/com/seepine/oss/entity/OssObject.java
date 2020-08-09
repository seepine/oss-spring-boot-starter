package com.seepine.oss.entity;

import lombok.Data;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.InputStream;

/**
 * @author Seepine
 */
@Data
public class OssObject {
    private final HttpRequestBase httpRequest;
    private final InputStream in;

    public OssObject(InputStream in, HttpRequestBase httpRequest) {
        this.in = in;
        this.httpRequest = httpRequest;
    }
}
