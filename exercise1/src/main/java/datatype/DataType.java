package datatype;

public class DataType {

  public static void main(String[] args) {
    boolean a = true;
    char b = 'R';
    byte c = 12;
    short d = -356;
    int e = 43543;
    long f = 98765432198L;
    float g = 987.1234F;
    double h = 98765.12345D;

    System.out.println("a = " + a);
    System.out.println("b = " + b);
    System.out.println("c = " + c);
    System.out.println("d = " + d);
    System.out.println("e = " + e);
    System.out.println("f = " + f);
    System.out.println("g = " + g);
    System.out.println("h = " + h);

    //String class
    String str1 = "This is a string";
    System.out.println("str1 = " + str1);

    //Some tips
    long money = 98_765_432_198L;

    float float1 = 4_5.987_654F;
  }

}
