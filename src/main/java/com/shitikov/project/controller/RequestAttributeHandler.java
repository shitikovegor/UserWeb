package com.shitikov.project.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestAttributeHandler {
    private Map<String, Object> requestAttributes = new HashMap<>();

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;  // TODO: 22.10.2020 unmodifiable or not?
    }

    public void setRequestAttributes(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();

        while(attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attribute = request.getAttribute(attributeName);
            requestAttributes.put(attributeName, attribute);
        }
    }
}
