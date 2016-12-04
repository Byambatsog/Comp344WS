package com.comp344.ecommerce.service.representation;

import org.springframework.http.HttpMethod;

/**
 * Created by Byambatsog on 10/30/16.
 */
public class Link {

    private String href;
    private String rel;
    private HttpMethod method;
    private String mediaType;

    public Link(String href, String rel, HttpMethod method, String mediaType) {
        this.href = href;
        this.rel = rel;
        this.method = method;
        this.mediaType = mediaType;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
