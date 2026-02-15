package collections;

public class Cars {

  String carName;
  int year;
  int price;


  //Constructor
  public Cars(String carName, int year, int price) {
    this.carName = carName;
    this.year = year;
    this.price = price;
  }

  public String getCarName() {
    return carName;
  }

  public int getYear() {
    return year;
  }

  public int getPrice() {
    return price;
  }
}
