package boxing;

public class AutoBoxingUnboxing {

  public static void main(String[] args) {

    Double db = 12.8;//Auto-boxing of double
    double d = db;//Auto-unboxing of Double
    System.out.println(db + " " + d);

    Integer in = 5;//Auto-boxing of int
    int i = in;//Auto-unboxing of Integer
    System.out.println(in + " " + i);

    Character ch = 'b';//Auto-boxing of char
    char c = ch;//Auto-unboxing of Character
    System.out.println(ch + " " + c);

    Integer integer1 = null;
//    int int1 = (Integer) null;//Runtime exception NullPointerException
//    int int2 = integer1;//Runtime exception NullPointerException

  }

}
