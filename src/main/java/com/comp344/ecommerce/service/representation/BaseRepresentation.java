package com.comp344.ecommerce.service.representation;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byambatsog on 10/30/16.
 */
public class BaseRepresentation {

    public static final String BASE_URI = "http://localhost:8080";

    private List<Link> _links = new ArrayList<Link>();

    public List<Link> getLinks() {
        return _links;
    }

    public void setLinks(List<Link> links) {
        this._links = links;
    }

    public void addLink(String href, String rel, HttpMethod action, String mediaType) {
        Link link = new Link(href, rel, action, mediaType);
        getLinks().add(link);
    }
}
