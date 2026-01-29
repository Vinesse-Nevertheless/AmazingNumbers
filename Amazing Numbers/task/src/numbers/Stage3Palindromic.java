package numbers;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Stage3Palindromic {

    private Map<String, Boolean> properties = new HashMap<>();

    public static void main(String[] args) {
        Stage3Palindromic init = new Palindromic();
        init.printGreeting();
        init.requestNumber();
    }


    private void printGreeting() {
        String greeting = "Welcome to Amazing Numbers!\n\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter 0 to exit.\n";

        System.out.println(greeting);

    }


    private void requestNumber() {
        while (true) {
            Scanner in = new Scanner(System.in);
            try {
                System.out.print("Enter a request: ");
                long num = in.nextLong();
                System.out.println();

                if (num == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                String numStr = String.valueOf(num);

                if (isNaturalNum(num)) {
                    checkOddOrEven(num);
                    checkIfBuzzNum(num, numStr);
                    checkIfDuckNum(numStr);
                    checkIfPalindrome(numStr);
                    printProperties(num);
                }
            } catch (Exception e) {
                System.out.println("This is not a whole number.");
            }
        }
    }

    private boolean isNaturalNum(long num) {
        if (num < 0) {
            System.out.println("The first parameter should be a natural number or zero.\n");
            return false;
        }
        return true;
    }

    private void checkOddOrEven(long num) {
        properties.put("even", false);
        properties.put("odd", false);

        if (num % 2 == 0) {
            properties.put("even", true);
        } else {
            properties.put("odd", true);
        }
    }

    private void checkIfBuzzNum(long num, String numStr) {

        properties.put("buzz", false);

        if (num % 7 == 0 || numStr.charAt(numStr.length() - 1) == '7') {
            properties.put("buzz", true);
        }
    }

    private void checkIfDuckNum(String numStr) {

        properties.put("duck", false);

        for (int i = 0; i < numStr.length(); i++) {
            if (numStr.charAt(i) == '0') {
                properties.put("duck", true);
            }
        }
    }

    private void checkIfPalindrome(String numStr){
        int head = 0;
        int tail =  numStr.length() -1;

        properties.put("palindromic", true);
        while (head <= tail){
            if (numStr.charAt(head) != numStr.charAt(tail)){
                properties.put("palindromic", false);
            }
            head++;
            tail--;
        }
    }

    private void printProperties(long num) {

        NumberFormat formatter = NumberFormat.getNumberInstance();

        System.out.println("Properties of " + formatter.format(num));

        for (String key : properties.keySet()) {
            System.out.println("\t" + key + ": " + properties.get(key));
        }
    }
}
