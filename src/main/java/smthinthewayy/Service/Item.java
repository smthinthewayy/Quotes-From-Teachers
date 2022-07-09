package smthinthewayy.Service;

import java.sql.Date;

public class Item {
  private final String quote;
  private final String teacher;
  private final String subject;
  private final Date date;

  public Item(String quote, String teacher, String subject, Date date) {
    this.quote = quote;
    this.teacher = teacher;
    this.subject = subject;
    this.date = date;
  }

  public String getQuote() {
    return quote;
  }

  public String getTeacher() {
    return teacher;
  }

  public String getSubject() {
    return subject;
  }

  public Date getDate() {
    return date;
  }
}
