package packagetwo;

import packageone.Cat;

public class Bee extends Cat {

  public static void main(String[] args) {

    Cat myCat = new Cat();
    myCat.publicMethod();
    Bee myBee = new Bee();

    //Без protected modifier не спрацює.
    // Працює лише коли викликається від this або super або об'єкта дочірнього класа.
    // Не спрацює з об'єктом батьківського класа, тобто в нашому випадку Cat myCat
    myBee.protectedMethod();

//    myCat.defaultMethod();
//    myCat.privateMethod();

  }

}
