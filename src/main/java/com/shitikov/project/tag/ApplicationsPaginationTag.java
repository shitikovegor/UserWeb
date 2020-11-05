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

@SuppressWarnings("serial")
public class ApplicationsPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTENT_PATH = "properties/pagecontent";
    private int pageNumber;
    private int applicationsOnPage;

    private static final String BUNDLE_STATUS = "application.status";
    private static final String BUNDLE_TYPE = "application.type";
    private static final String BUNDLE_DATE = "application.date";
    private static final String BUNDLE_CITY = "application.city";
    private static final String BUNDLE_DETAILS = "page.applications.details";
    private static final String BUNDLE_OFFER = "page.applications.offerHelp";
    private static final String BUNDLE_STATUS_PREFIX = "order.status.";
    private static final String BUNDLE_APP_PREFIX = "application.";

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setApplicationsOnPage(int applicationsOnPage) {
        this.applicationsOnPage = applicationsOnPage;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            HttpSession session = pageContext.getSession();
            String attrLocale = (String) session.getAttribute(ParameterName.LOCALE);
            Locale locale = Locale.forLanguageTag(attrLocale.replace("_","-"));
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
                        "<div class=\"card\" style=\"margin-bottom: 15px;\">\n" +
                            "<div class=\"card-body\">\n" +
                                "<div class=\"row\" style=\"border: none;margin-bottom: 5px;\">\n" +
                                    "<div class=\"col\">\n" +
                                        "<h5 class=\"d-inline\">" + application.getTitle() + "</h5>\n" +
                                    "</div>\n" +
                                    "<div class=\"col\" style=\"max-width: 215px;\">\n" +
                                        "<div class=\"form-group\">\n" +
                                            "<h6 class=\"text-info float-right mb-2\" id=\"status\" name=\"status\">\n" +
                                            resourceBundle.getString(BUNDLE_STATUS_PREFIX + status.getName()) + "</h6>\n" +
                                            "<h6 class=\"text-info float-right mb-2\" for=\"status\">\n" +
                                            resourceBundle.getString(BUNDLE_STATUS) +": </h6>\n" +
                                        "</div>\n" +
                                    "</div>\n" +
                                "</div>\n" +
                                "<hr style=\"margin-top: 6px;margin-bottom: 8px;\">\n" +
                                "<div class=\"row\" style=\"border: none;margin-bottom: 5px;\">\n" +
                                    "<div class=\"col\">\n" +
                                        "<p style=\"margin-bottom: 5px;\">\n" +
                                            "<strong class=\"d-inline-block\">" +
                                                resourceBundle.getString(BUNDLE_TYPE) + ": <br></strong>\n" +
                                            resourceBundle.getString(BUNDLE_APP_PREFIX + application.getApplicationType().getName()) +
                                        "</p>\n" +
                                    "</div>\n" +
                                    "<div class=\"col\">\n" +
                                        "<p style=\"margin-bottom: 5px;\"><strong class=\"d-inline-block\">" +
                                                resourceBundle.getString(BUNDLE_DATE) + ": <br></strong>\n" +
                                            DateFormatUtil.formatDate(application.getAddressTimeData().getDepartureDate()) +
                                        "</p>\n" +
                                    "</div>\n" +
                                    "<div class=\"col\">\n" +
                                        "<p style=\"margin-bottom: 5px;\"><strong class=\"d-inline-block\">" +
                                                resourceBundle.getString(BUNDLE_CITY) + ": <br></strong>\n" +
                                            application.getAddressTimeData().getDepartureAddress().getCity() +
                                        "</p>\n" +
                                    "</div>\n" +
                                "</div>\n" +
                                "<div class=\"float-right\" style=\"margin-bottom: 5px;\">\n" +
                                    "<form class=\"form-group d-inline-block app-button\" action=\"controller\" method=\"post\">\n" +
                                        "<input type=\"hidden\" name=\"command\" value=\"application-page\">\n" +
                                        "<input type=\"hidden\" name=\"application_id\" value=\"" + application.getApplicationId() + "\">\n" +
                                        "<input type=\"hidden\" name=\"current_page\" value=\"" +
                                            session.getAttribute(ParameterName.CURRENT_PAGE) + "\">\n" +
                                        "<input type=\"hidden\" name=\"status\" value=\"" + status + "\">\n" +
                                        "<button class=\"btn btn-outline-primary btn-sm\" type=\"submit\" style=\"margin-right: 15px;\">" +
                                            resourceBundle.getString(BUNDLE_DETAILS) +
                                        "</button>\n" +
                                    "</form>\n");

                if (status == OrderStatus.ACTIVE && role == RoleType.DRIVER) {
                    result.append(
                            "<form class=\"form-group d-inline-block app-button\" action=\"controller\" method=\"post\">\n" +
                                    "<input type=\"hidden\" name=\"command\" value=\"offer-help\"><input type=\"hidden\" name=\"application_id\" " +
                                    "value=\"" + application.getApplicationId() + "\">\n" +
                                    "<button class=\"btn btn-outline-primary btn-sm\" type=\"submit\">\n" +
                                    resourceBundle.getString(BUNDLE_OFFER) +
                                    "</button></form>\n"
                    );
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
