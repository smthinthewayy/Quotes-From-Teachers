package smthinthewayy.Service;

import java.sql.Date;

/**
 * Auxiliary class - table row
 */
public class Item {
  /**
   * The string responsible for the text of the quote
   */
  private final String quote;

  /**
   * The string responsible for the teacher's name
   */
  private final String teacher;

  /**
   * The string responsible for the subject name
   */
  private final String subject;

  /**
   * Date type object responsible for the date
   */
  private final Date date;

  /**
   * Constructor - creating a new object with certain values
   *
   * @param quote   text of the quote
   * @param teacher teacher's name
   * @param subject subject name
   * @param date    date
   */
  public Item(String quote, String teacher, String subject, Date date) {
    this.quote = quote;
    this.teacher = teacher;
    this.subject = subject;
    this.date = date;
  }

  /**
   * Getter for the quote text
   *
   * @return text of the quote
   */
  public String getQuote() {
    return quote;
  }

  /**
   * Getter for the teacher's name
   *
   * @return teacher's name
   */
  public String getTeacher() {
    return teacher;
  }

  /**
   * Getter for the subject name
   *
   * @return subject name
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Getter for the date
   *
   * @return Date type object
   */
  public Date getDate() {
    return date;
  }
}
