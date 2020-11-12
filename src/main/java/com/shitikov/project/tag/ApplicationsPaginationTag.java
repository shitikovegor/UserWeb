package com.shitikov.project.tag;

import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.tag.util.DateFormatUtil;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;

/**
 * The type Applications pagination tag.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ApplicationsPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTENT_PATH = "properties/pagecontent";
    private static final String BUNDLE_STATUS = "application.status";
    private static final String BUNDLE_TYPE = "application.type";
    private static final String BUNDLE_DATE = "application.date";
    private static final String BUNDLE_CITY = "application.city";
    private static final String BUNDLE_DETAILS = "page.applications.details";
    private static final String BUNDLE_OFFER = "page.applications.offerHelp";
    private static final String BUNDLE_STATUS_PREFIX = "order.status.";
    private static final String BUNDLE_APP_PREFIX = "application.";
    private int pageNumber;
    private int applicationsOnPage;

    /**
     * Sets page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Sets applications on page.
     *
     * @param applicationsOnPage the applications on page
     */
    public void setApplicationsOnPage(int applicationsOnPage) {
        this.applicationsOnPage = applicationsOnPage;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            HttpSession session = pageContext.getSession();
            String attrLocale = (String) session.getAttribute(ParameterName.LOCALE);
            Locale locale = Locale.forLanguageTag(attrLocale.replace("_", "-"));
            RoleType role = (RoleType) session.getAttribute(ParameterName.ROLE_TYPE);
            ServletRequest request = pageContext.getRequest();
            Map<Application, OrderStatus> applications =
                    (Map<Application, OrderStatus>) request.getAttribute(ParameterName.APPLICATIONS);
            ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT_PATH, locale);
            List<Application> applicationList = new ArrayList<>(applications.keySet());
            int startIndex = pageNumber * applicationsOnPage - applicationsOnPage;
            int finishIndex = Math.min(applicationList.size(), pageNumber * applicationsOnPage);
            for (int i = startIndex; i < finishIndex; i++) {
                Application application = applicationList.get(i);
                OrderStatus status = applications.get(application);

                StringBuilder result = new StringBuilder(
                        "<div class=\"card\" style=\"margin-bottom: 15px;\">\n<div class=\"card-body\">\n")
                        .append("<div class=\"row\" style=\"border: none;margin-bottom: 5px;\">\n")
                        .append("<div class=\"col\">\n<h5 class=\"d-inline\">")
                        .append(application.getTitle())
                        .append("</h5>\n</div>\n<div class=\"col\" style=\"max-width: 215px;\">\n")
                        .append("<div class=\"form-group\">\n")
                        .append("<h6 class=\"text-info float-right mb-2\" id=\"status\" name=\"status\">\n")
                        .append(resourceBundle.getString(BUNDLE_STATUS_PREFIX + status.getName()))
                        .append("</h6>\n<h6 class=\"text-info float-right mb-2\" for=\"status\">\n")
                        .append(resourceBundle.getString(BUNDLE_STATUS))
                        .append(":&nbsp;</h6>\n</div>\n</div>\n</div>\n<hr style=\"margin-top: 6px;margin-bottom: 8px;\">\n")
                        .append("<div class=\"row\" style=\"border: none;margin-bottom: 5px;\">\n")
                        .append("<div class=\"col\">\n<p style=\"margin-bottom: 5px;\">\n")
                        .append("<strong class=\"d-inline-block\">")
                        .append(resourceBundle.getString(BUNDLE_TYPE))
                        .append(": <br></strong>\n")
                        .append(resourceBundle.getString(BUNDLE_APP_PREFIX + application.getApplicationType().getName()))
                        .append("</p>\n</div>\n<div class=\"col\">\n")
                        .append("<p style=\"margin-bottom: 5px;\"><strong class=\"d-inline-block\">")
                        .append(resourceBundle.getString(BUNDLE_DATE))
                        .append(": <br></strong>\n")
                        .append(DateFormatUtil.formatDate(application.getAddressTimeData().getDepartureDate()))
                        .append("</p>\n</div>\n<div class=\"col\">\n")
                        .append("<p style=\"margin-bottom: 5px;\"><strong class=\"d-inline-block\">")
                        .append(resourceBundle.getString(BUNDLE_CITY))
                        .append(": <br></strong>\n")
                        .append(application.getAddressTimeData().getDepartureAddress().getCity())
                        .append("</p>\n</div>\n</div>\n")
                        .append("<div class=\"float-right\" style=\"margin-bottom: 5px;\">\n")
                        .append("<form class=\"form-group d-inline-block app-button\" action=\"controller\" method=\"post\">\n")
                        .append("<input type=\"hidden\" name=\"command\" value=\"application-page\">\n")
                        .append("<input type=\"hidden\" name=\"application_id\" value=\"")
                        .append(application.getApplicationId())
                        .append("\">\n<input type=\"hidden\" name=\"current_page\" value=\"")
                        .append(session.getAttribute(ParameterName.CURRENT_PAGE))
                        .append("\">\n<input type=\"hidden\" name=\"status\" value=\"")
                        .append(status)
                        .append("\">\n")
                        .append("<button class=\"btn btn-outline-primary btn-sm\" type=\"submit\" style=\"margin-right: 15px;\">")
                        .append(resourceBundle.getString(BUNDLE_DETAILS))
                        .append("</button>\n</form>\n");

                if (status == OrderStatus.ACTIVE && role == RoleType.DRIVER) {
                    result.append("<form class=\"form-group d-inline-block app-button\" action=\"controller\" method=\"post\">\n")
                            .append("<input type=\"hidden\" name=\"command\" value=\"offer-help\"><input ")
                            .append("type=\"hidden\" name=\"application_id\" value=\"")
                            .append(application.getApplicationId())
                            .append("\">\n<button class=\"btn btn-outline-primary btn-sm\" type=\"submit\">\n")
                            .append(resourceBundle.getString(BUNDLE_OFFER))
                            .append("</button></form>\n");
                }
                result.append("</div>\n</div>\n</div>");
                pageContext.getOut().write(result.toString());
            }
        } catch (IOException e) {
            logger.log(Level.WARN, "Error while writing data to jsp.");
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
