package decisionmaking;

public class TernaryOperator {

  public static void main(String[] args) {

    int score = 40;
    String result;
//
//    if(score > 50)
//      result = "You passed the exam";
//    else
//      result = "You failed the exam";
//
//    System.out.println(result);

//    result = score > 50 ? "You passed the exam" : "You failed the exam";
//
//    System.out.println(result);

    int a = 10, b = 9;

    int c = a == b ? a++ : b--;

    System.out.println("a : " + a);//10
    System.out.println("b : " + b);//8
    System.out.println("c : " + c);//9

  }

}
