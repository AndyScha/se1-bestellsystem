package application;

import datamodel.Customer;

public class Application {
    public static void main(String[] args) {
        System.out.println("SE1 Bestellsystem");
        Customer test = new Customer("Meyer, Eric");
        System.out.println(test.getLastName());
        System.out.println(test.getFirstName());
        System.out.println(test.getName());
//        System.out.println("FIRST NAME: " + test.getFirstName());
//        System.out.println("LAST NAME: " + test.getLastName());
    }
}
