package datatype;

public class CharExample {

  public static void main(String[] args) {

    //Char Data Type -> 0-65535 'A'
    char c1 = 72;//72 in decimal
    char c2 = 53;//53 in decimal

    System.out.println("c1: " + c1);
    System.out.println("is first variable letter ? " + Character.isLetter(c1));
    System.out.println("is first variable digit ? " + Character.isDigit(c1));

    System.out.println("c2: " + c2);
    System.out.println("is second variable letter ? " + Character.isLetter(c2));
    System.out.println("is second variable digit ? " + Character.isDigit(c2));

    char letter1 = 67;//C in ASCII
    int num1 = letter1 + 3;//67 + 3 = 70 -> F in ASCII
    char letter2 = (char) num1;//F in ASCII

    System.out.println("letter2 is " + letter2);//output if F

    char letter3 = 70;//F in ASCII
    char letter4 = letter3--;//letter4 = 70 after this line letter3 is 69 -> E
    boolean check1 = letter4 == 'E';//F != E -> check1 = false
    boolean check2 = --letter3 > 'D';//D(68) > D(68) -> false
    System.out.println("check1: " + check1);
    System.out.println("check2: " + check2);
  }

}
