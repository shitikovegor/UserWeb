package com.shitikov.project.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class FormatDateTag extends TagSupport {
    private long date;
    private boolean input = false;
    public void setDate(long date) {
        this.date = date;
    }
    public void setInput(boolean input) {
        this.input = input;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            String dateFormatted;
            LocalDate localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("yyyy-MM-dd");


            dateFormatted = input ? localDate.format(formatterIn) : localDate.format(formatterOut);
            pageContext.getOut().write(dateFormatted);

        } catch (IOException e) {
            throw new JspException(e);
        }

        return super.doStartTag();
    }
}
