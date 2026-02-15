package stringclassexample;

import java.util.Arrays;

public class UsefulMethodsOfString {

  public static void main(String[] args) {

    String s1 = "Java Developers";
    String s2 = "Hello";
    String s3 = "hello";

    //J a v a   D e v e l o  p  e  r  s
    //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 -> index numbers

    //trim()
    System.out.println(" Hello Java World ");
    System.out.println(" Hello Java World ".trim());

    //replace()
    System.out.println(s1.replace(' ', '-'));

    //contains()
    System.out.println(s1.contains("op"));//true
    System.out.println(s1.contains("J".toLowerCase()));//false

    //endsWith()
    System.out.println(s1.endsWith("ers"));

    //startsWith()
    System.out.println(s2.startsWith("h".toUpperCase()));//Можу викликати методи прямо на String літералах
    System.out.println(s2.startsWith("Hel"));

    //equalsIgnoreCase()
    System.out.println(s2.equalsIgnoreCase(s3));

    //substring()
    System.out.println(s1.substring(5));//Developers
    System.out.println(s1.substring(5, 12));//Develop

    //charAt()
    System.out.println(s1.charAt(0));
    System.out.println(s1.charAt(4));
    System.out.println(s1.charAt(s1.length() - 1));

    //indexOf()
    System.out.println(s1.indexOf('p'));//11
    System.out.println(s1.indexOf('v', 5));//7
    System.out.println(s1.indexOf("lop"));//9
    System.out.println(s1.indexOf("lop", 5));//9
    System.out.println(s1.indexOf("lop", 12));//-1
    System.out.println(s1.indexOf("lop", 25));//-1

//    String str1 = "Hello Java Developers";
//    String str2 = "Java is fun";
//    String[] str3;
//
//    System.out.println("str1: " + str1);
//    System.out.println("Length of str1: " + str1.length());
//    System.out.println("First character of str1: " + str1.charAt(0));
//    System.out.println("Is str1 empty? " + str1.isEmpty());
//    System.out.println("Substring: " + str1.substring(6));
//    System.out.println("str1 is equal to str2? " + str1.equals(str2));
//    System.out.println("str1 + str2: " + str1.concat(" " + str2));
//    System.out.println("str1 in lowercase: " + str1.toLowerCase());
//    System.out.println("str1 in uppercase: " + str1.toUpperCase());
//    System.out.println("Replace l with L: " + str1.replace('l', 'L'));
//    str3 = str1.split(" ");
//    System.out.println("str3: " + Arrays.toString(str3));

  }

}
