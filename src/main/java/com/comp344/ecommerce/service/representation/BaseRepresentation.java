package com.comp344.ecommerce.service.representation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byambatsog on 10/30/16.
 */
public class BaseRepresentation {

    private List<Link> links = new ArrayList<Link>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(String href, String rel, String action) {
        Link link = new Link(href, rel, action);
        getLinks().add(link);
    }
}
