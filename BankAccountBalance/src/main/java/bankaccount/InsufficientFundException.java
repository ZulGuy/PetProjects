package bankaccount;

public class InsufficientFundException extends Exception {

  private String message;

  //Constructor
  public InsufficientFundException(String message) {
    this.message = message;
  }

  //getter method of message
  public String getMessage() {
    return message;
  }
}
