package calculatorwithlambda;

@FunctionalInterface
public interface Operation {

  void performOperation(double a, double b);

}
