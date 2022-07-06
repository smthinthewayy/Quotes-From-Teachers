package semiotic;

public class Item {
  private final String quote;
  private final String teacher;
  private final String subject;
  private final String date;

  public Item(String quote, String teacher, String subject, String date) {
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

  public String getDate() {
    return date;
  }
}
