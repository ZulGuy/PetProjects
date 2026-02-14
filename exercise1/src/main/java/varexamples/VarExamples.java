package varexamples;

import java.util.function.Function;

public class VarExamples {

  class Var{}

  public static void var(){
    //The return type and parameter type cannot be var
  }

//  var age;//Compilaition error. We can use var only in methods or blocks

  public static void main(String[] args) {

    var num = 10;

//    num = "10";Compilation error. Cannot pass different data type in var after its initialization

    var name = "Java";
    var animals = new String[5];
    var person = new Person();

//    var age;//Compilation error.var must be declared and initialized in one statement;
    int age;

//    var text = null;//Compilation error. var cannot be null

    var numbers = new int[5];

    //var with lambda
//    var function = anyString -> anyString.length();//Compilation error. Cannot use var with lambda


  }

}
