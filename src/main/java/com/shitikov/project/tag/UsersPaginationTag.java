package com.shitikov.project.tag;

import com.shitikov.project.model.entity.User;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The type Users pagination tag.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
@SuppressWarnings("serial")
public class UsersPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTENT_PATH = "properties/pagecontent";
    private static final String BUNDLE_BLOCK = "page.users.block";
    private static final String BUNDLE_UNBLOCK = "page.users.unblock";
    private static final String BUNDLE_OPEN = "page.users.open";
    private static final String BUNDLE_ROLE_PREFIX = "user.role.";
    private static final String BUNDLE_YES = "page.users.yes";
    private static final String BUNDLE_NO = "page.users.no";
    private int pageNumber;
    private int usersOnPage;

    /**
     * Sets page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Sets users on page.
     *
     * @param usersOnPage the users on page
     */
    public void setUsersOnPage(int usersOnPage) {
        this.usersOnPage = usersOnPage;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            HttpSession session = pageContext.getSession();
            String attrLocale = (String) session.getAttribute(ParameterName.LOCALE);
            Locale locale = Locale.forLanguageTag(attrLocale.replace("_", "-"));
            ServletRequest request = pageContext.getRequest();
            List<User> users =
                    (List<User>) request.getAttribute(ParameterName.USERS);
            ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT_PATH, locale);
            int startIndex = pageNumber * usersOnPage - usersOnPage;
            int finishIndex = Math.min(users.size(), pageNumber * usersOnPage);
            for (int i = startIndex; i < finishIndex; i++) {
                User user = users.get(i);

                StringBuilder result = new StringBuilder("<tr>\n<td>")
                        .append(user.getUserId())
                        .append("</td>\n<td>")
                        .append(user.getLogin())
                        .append("</td>\n<td>")
                        .append(resourceBundle.getString(BUNDLE_ROLE_PREFIX + user.getRoleType().getName()))
                        .append("</td>\n<td>");
                if (user.isActivated()) {
                    result.append(resourceBundle.getString(BUNDLE_YES));
                } else {
                    result.append(resourceBundle.getString(BUNDLE_NO));
                }
                result.append("</td>\n" + "<td>");
                if (user.isBlocked()) {
                    result.append(resourceBundle.getString(BUNDLE_YES));
                } else {
                    result.append(resourceBundle.getString(BUNDLE_NO));
                }
                result.append("</td>\n<td>\n");
                if (!user.isBlocked()) {
                    result.append("<form id=\"block")
                            .append(user.getUserId())
                            .append("\" action=\"controller\" method=\"post\">\n")
                            .append("<input type=\"hidden\" name=\"login\" value=\"")
                            .append(user.getLogin())
                            .append("\">\n<input type=\"hidden\" name=\"command\" value=\"block-user\">\n")
                            .append("<a href=\"javascript:document.getElementById('block")
                            .append(user.getUserId())
                            .append("').submit()\">\n")
                            .append(resourceBundle.getString(BUNDLE_BLOCK))
                            .append("</a>\n</form>\n");
                } else {
                    result.append("<form id=\"unblock")
                            .append(user.getUserId())
                            .append("\" action=\"controller\" method=\"post\">\n")
                            .append("<input type=\"hidden\" name=\"login\" value=\"")
                            .append(user.getLogin())
                            .append("\">\n<input type=\"hidden\" name=\"command\" value=\"unblock-user\">\n")
                            .append("<a href=\"javascript:document.getElementById('unblock")
                            .append(user.getUserId())
                            .append("').submit()\">\n")
                            .append(resourceBundle.getString(BUNDLE_UNBLOCK))
                            .append("</a>\n</form>\n");
                }
                result.append("</td>\n<td>\n")
                        .append("<form id=\"open")
                        .append(user.getUserId())
                        .append("\" action=\"controller\" method=\"post\">\n")
                        .append("<input type=\"hidden\" name=\"login\" value=\"")
                        .append(user.getLogin())
                        .append("\">\n<input type=\"hidden\" name=\"command\" value=\"account-for-admin-page\">\n")
                        .append("<a href=\"javascript:document.getElementById('open")
                        .append(user.getUserId())
                        .append("').submit()\">\n")
                        .append(resourceBundle.getString(BUNDLE_OPEN))
                        .append("</a>\n</form>\n</td>\n</tr>");

                pageContext.getOut().write(result.toString());
            }
        } catch (IOException e) {
            logger.log(Level.WARN, "Error while writing data to jsp.");
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
