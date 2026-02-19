package bankaccount;

public class BankAccount {

  private double balance;

  public BankAccount() {
    balance = 0.0;
  }

  //deposit method
  public void deposit(double amount) {
    balance += amount;//balance = balance + amount
  }

  //withdraw method
  public void withdraw(double amount) throws InsufficientFundException {
    if (amount > balance)
      throw new InsufficientFundException("Insufficient Balance. Withdraw process couldn't be completed");
    else
      balance -= amount;
  }

  //getter method of balance
  public double getBalance() {
    return balance;
  }

}
