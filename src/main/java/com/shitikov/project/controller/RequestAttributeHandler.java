package com.shitikov.project.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Request attribute handler.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class RequestAttributeHandler {
    private final Map<String, Object> requestAttributes = new HashMap<>();

    /**
     * Gets request attributes.
     *
     * @return the request attributes
     */
    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    /**
     * Sets request attributes.
     *
     * @param request the request
     */
    public void setRequestAttributes(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();

        while(attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attribute = request.getAttribute(attributeName);
            requestAttributes.put(attributeName, attribute);
        }
    }
}
