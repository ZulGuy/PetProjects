package streamexamples;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import streamexamples.Employee.Sex;

public class ReductionOperationsWithReduce {

  public static void main(String[] args) {

    List<Employee> employees = Arrays.asList(
        new Employee("David", 3000, Sex.MALE),
        new Employee("Marry", 2500, Sex.FEMALE),
        new Employee("Clark", 3500, Sex.MALE),
        new Employee("Andy", 4500, Sex.MALE),
        new Employee("Sara", 2000, Sex.FEMALE)
    );

    Optional<Employee> highestSalary =  employees.stream()
        .reduce((e1,e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2);
    highestSalary.ifPresent(e -> System.out.println("Name: " + e.getName() + ", Salary: " + e.getSalary()));

    int totalSalary = employees.stream()
        .map(Employee::getSalary)
        .reduce(0, Integer::sum);
    System.out.println("Total Salary: " + totalSalary);

    String concatenatedNames = employees.stream()
        .map(Employee::getName)
        .reduce("", (n1,n2) -> n1 + " " + n2);
    System.out.println("Concatenated names: " + concatenatedNames.trim());

//    List<Integer> nums = Arrays.asList(1,2,3,4,5);
//    Optional<Integer> sum = nums.stream()
//        .reduce(Integer::sum);
//    sum.ifPresent(n -> System.out.println("Sum: " + n));
//
//    int sumWithInitialValue = nums.stream()
//        .reduce(0, Integer::sum);
//    System.out.println("Sum: " + sumWithInitialValue);
//
//    int elementProduct = nums.stream()
//        .reduce(1, (a, b) -> a * b);
//    System.out.println("Product: " + elementProduct);
//
//    Optional<Integer> max = nums.stream().reduce((a,b) -> a > b ? a : b);
//    max.ifPresent(n -> System.out.println("Max: " + n));
//
//    Optional<Integer> min = nums.stream().reduce((a,b) -> a < b ? a : b);
//    min.ifPresent(n -> System.out.println("Min: " + n));


  }

}
