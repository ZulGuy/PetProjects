package streamexamples;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import streamexamples.Employee.Sex;

public class ReductionOperationsWithCollect {

  public static void main(String[] args) {

//    List<String> names = Arrays.asList("David","Clark","Marry","Sara","Andy", "Marry");
//    List<String> collectedNames = names.stream()
//        .filter(n -> n.length() > 4)
//        .collect(Collectors.toList());
//    System.out.println("Collected names: " + collectedNames);
//
//    Set<String> collectedNamesSet = names.stream()
//        .filter(n -> n.length() > 4)
//        .collect(Collectors.toSet());
//    System.out.println("Collected set names: " + collectedNamesSet);
//
//    String joinedNames = names.stream()
//        .collect(Collectors.joining(", ","[","]"));
//    System.out.println("Joined names: " + joinedNames);
//
//    Map<Integer, List<String>> groupedByLength = names.stream()
//        .collect(Collectors.groupingBy(String::length));
//    System.out.println("Grouped by length: " + groupedByLength);
//
//    Map<Boolean, List<String>> partitionedByLength = names.stream()
//        .collect(Collectors.partitioningBy(n -> n.length() > 4));
//    System.out.println("Partitioned by length: " + partitionedByLength);
//
//    LinkedList<String> linkedListNames = names.stream()
//        .filter(n -> n.length() > 4)
//        .collect(Collectors.toCollection(LinkedList::new));
//    System.out.println("Linked list names: " + linkedListNames);

    List<Employee> employees = Arrays.asList(
        new Employee("David", 3000, Sex.MALE),
        new Employee("Marry", 2500, Sex.FEMALE),
        new Employee("Clark", 3500, Sex.MALE),
        new Employee("Andy", 4500, Sex.MALE),
        new Employee("Sara", 2000, Sex.FEMALE)
    );

//    Map<Employee.Sex, Integer> totalSalaryByGender = employees.stream()
//        .collect(
//            Collectors.groupingBy(
//                Employee::getGender,
//                Collectors.summingInt(Employee::getSalary))
//        );
//    System.out.println("Total salary by gender: " + totalSalaryByGender);
//
//    SalaryCollector salaryCollector = employees.stream()
//        .map(Employee::getSalary)
//        .collect(SalaryCollector::new, SalaryCollector::accept, SalaryCollector::combine);
//
//    System.out.println("Total salary: " + salaryCollector.getTotal());

    Map<Employee.Sex, SalaryCollector> totalSalaryByGenderWithCustomCollector = employees.stream()
        .collect(
            Collectors.groupingBy(
                Employee::getGender,
                Collectors.mapping(
                    Employee::getSalary,
                    Collector.of(
                        SalaryCollector::new,
                        SalaryCollector::accept,
                        (salary1, salary2) -> {
                          salary1.combine(salary2);
                          return salary1;
                        }
                    )
                )
            )
        );

    totalSalaryByGenderWithCustomCollector.forEach(
        (g, s) -> System.out.println("Gender: " + g + " Total Salary: " + s.getTotal()));

  }

}
