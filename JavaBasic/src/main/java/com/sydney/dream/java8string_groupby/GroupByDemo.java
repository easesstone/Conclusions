package com.sydney.dream.java8string_groupby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class GroupByDemo {
    public static void main(String[] args) {
        Employee employeeV1 = new Employee("name01", "shanghai", 500);
        Employee employeeV2 = new Employee("name01", "shanghai", 500);
        Employee employeeV3 = new Employee("name01", "beijing", 500);
        Employee employeeV4 = new Employee("name01", "hangzhou", 500);
        Employee employeeV5 = new Employee("name01", "hangzhou", 500);
        Employee employeeV6 = new Employee("name01", "hangzhou", 500);
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employeeV1);
        employees.add(employeeV2);
        employees.add(employeeV3);
        employees.add(employeeV4);
        employees.add(employeeV5);
        employees.add(employeeV6);

        Map<String, List<Employee>> groupByCity = employees.stream().collect(groupingBy(Employee::getCity));
        for (Map.Entry<String, List<Employee>> entry : groupByCity.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }


    }
}

class Employee {
    private String name;
    private String city;
    private int numOfSale;

    public Employee(String name, String city, int numOfSale) {
        this.name = name;
        this.city = city;
        this.numOfSale = numOfSale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumOfSale() {
        return numOfSale;
    }

    public void setNumOfSale(int numOfSale) {
        this.numOfSale = numOfSale;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", numOfSale=" + numOfSale +
                '}';
    }
}
