package com.comp344.ecommerce.service.representation;

/**
 * Created by Byambatsog on 10/30/16.
 */
public class Link {

    private String href;
    private String rel;
    private String action;

    public Link(String href, String rel, String action) {
        this.href = href;
        this.rel = rel;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
}
