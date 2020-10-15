package com.shitikov.project.controller.filter;

import com.shitikov.project.util.ParameterName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"/*"})
public class CurrentPageFilter implements Filter {
    private static final String REFERER = "referer";
    private static final String PATH_REGEX = "/controller.+";
    private static final String CHANGE_LANGUAGE = "change-language";

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        String url = request.getHeader(REFERER);
        String path = substringPathWithRegex(url);
        if (path != null && !path.contains(CHANGE_LANGUAGE)) {
            session.setAttribute(ParameterName.CURRENT_PAGE, path);
        }
        chain.doFilter(req, resp);
    }

    private String substringPathWithRegex(String url) {
        Pattern pattern = Pattern.compile(PATH_REGEX);
        String path = null;
        if (url != null) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                path = matcher.group(0);
            } else {
                path = ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.home");
            }
        }
        return path;
    }

    public void destroy() {
    }
}
