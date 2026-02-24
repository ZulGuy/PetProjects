package streamexamples;

public class SalaryCollector {

  private int total = 0;
  private int count = 0;

  //getter method of total
  public int getTotal() {
    return this.total;
  }

  public void accept(int salary) {
    this.total += salary;
    this.count++;
  }

  public void combine(SalaryCollector other) {
    this.total += other.total;
    this.count += other.count;
  }

}
