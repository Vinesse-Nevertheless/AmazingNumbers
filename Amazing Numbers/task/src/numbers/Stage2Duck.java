package numbers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Stage2Duck {

    private Map<String, Boolean> properties = new HashMap<>();

    public static void main(String[] args) {
        Stage2Duck init = new Stage2Duck();
        init.requestNumber();
    }


    private void requestNumber() {
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Enter a natural number: ");
            int num = in.nextInt();
            String numStr = String.valueOf(num);

            if (isNaturalNum(num)) {
                checkOddOrEven(num);
                checkIfBuzzNum(num, numStr);
                checkIfDuckNum(numStr);
                printProperties(num);
            }
        } catch (Exception e) {
            System.out.println("This is not a whole number.");
        }
        in.close();
    }

    private boolean isNaturalNum(int num) {
        if (num < 1) {
            System.out.println("This number is not natural!");
            return false;
        }
        return true;
    }

    private void checkOddOrEven(int num) {
        properties.put("even", false);
        properties.put("odd", false);

        if (num % 2 == 0) {
           properties.put("even", true);
        } else {
            properties.put("odd", true);
        }
    }

    private void checkIfBuzzNum(int num, String numStr) {

        properties.put("buzz", false);

        if (num % 7 == 0 || numStr.charAt(numStr.length() - 1) == '7') {
            properties.put("buzz", true);
        }
    }

    private void checkIfDuckNum(String numStr) {

        properties.put("duck", false);

        for (int i = 0; i < numStr.length(); i++) {
            if (numStr.charAt(i) == '0'){
                properties.put("duck", true);
            }
        }
    }

    private void printProperties(int num){
        System.out.println("Properties of " + num);

        for (String key : properties.keySet()){
            System.out.println("\t" + key + ": " + properties.get(key));
        }
    }
}

