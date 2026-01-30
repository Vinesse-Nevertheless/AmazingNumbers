package numbers;

import java.text.NumberFormat;
import java.util.*;

public class Stage4Gapful {
   
    private Map<String, Boolean> properties;
    
    public static void main(String[] args) {
        Stage4Gapful init = new Stage4Gapful();
        System.out.println("Welcome to Amazing Numbers!\n");
        init.printOptions();
        init.requestNumber();
    }


    private void printOptions() {
        String options = "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "\t * the first parameter represents a starting number;\n" +
                "\t * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.";

        System.out.println(options);

    }


    private void requestNumber() {
        Scanner in;
        while (true) {
            in = new Scanner(System.in);
            System.out.print("\nEnter a request: ");
            String request = in.nextLine();
            System.out.println();

            if (request.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }

            if (isValidRequest(request)) {
                processRequest(request);
            }else{
                printOptions();
            }
        }
         in.close();
    }


    private void processRequest(String request) {
        String[] entries = request.split(" ");
        //create arguments for easy processing

        long currNum = Long.parseLong(entries[0]);
        String currStr = String.valueOf(currNum);

        //determine how many numbers we need to process
        long range = entries.length > 1 ? Long.parseLong(entries[1]) : 1;

        for (int i = 0; i < range; i++) {
            properties = new HashMap<>();

            checkOddOrEven(currNum);
            checkIfBuzzNum(currNum, currStr);
            checkIfDuckNum(currStr);
            checkIfPalindrome(currStr);
            checkIfGapful(currNum, currStr);
            printProperties(currNum, range);

            currNum++;
            currStr = String.valueOf(currNum);
        }

    }

    private boolean isValidRequest(String request){
        if (request.isEmpty() || request.equals(" ")) {
            return false;
        }

        String[] entries = request.split(" ");

        //ensure no more than 2 args are passed
        if (entries.length > 2) {
            return false;
        }

        for (int i = 0; i < entries.length; i++) {
            if (!isNaturalNumber(entries[i]) && i == 0) {
                System.out.println("The first parameter should be a natural number or zero.\n");
                return false;
            }
            if (!isNaturalNumber(entries[i]) && i == 1) {
                System.out.println("\"The second parameter should be a natural number.\n");
                return false;
            }

        }
        return true;
    }

    private boolean isNaturalNumber(String request) {

        //ensure no letters or symbols are in args
        //ensure natural number

        for (int i = 0; i < request.length(); i++) {
            if (!Character.isDigit(request.charAt(i)) || request.equals("0")) {
                return false;
            }
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
                break;
            }
        }
    }

    private void checkIfPalindrome(String numStr) {
        int head = 0;
        int tail = numStr.length() - 1;

        properties.put("palindromic", true);
        while (head <= tail) {
            if (numStr.charAt(head) != numStr.charAt(tail)) {
                properties.put("palindromic", false);
                break;
            }
            head++;
            tail--;
        }
    }

    private void checkIfGapful(long currNum, String numStr) {

        properties.put("gapful", false);

        if (numStr.length() < 3) {
            return;
        }

        char head = numStr.charAt(0);
        char tail = numStr.charAt(numStr.length() - 1);

        long divisor = Long.parseLong(head + "" + tail);

        if (currNum % divisor == 0) {
            properties.put("gapful", true);
        }
    }

    private void printProperties(long num, long range) {

        NumberFormat formatter = NumberFormat.getNumberInstance();
        String formattedNum = formatter.format(num);

        if (range > 1) {
            System.out.print("\t" + formattedNum + " is ");
            rangePrint();
        } else {
            System.out.println("Properties of " + formattedNum);
            singleNumPrint();
        }
    }

    private void singleNumPrint() {
        for (String key : properties.keySet()) {
            System.out.println("\t" + key + ": " + properties.get(key));
        }
    }

    private void rangePrint() {
        StringBuilder keys = new StringBuilder();
        for (String key : properties.keySet()) {
            if (properties.get(key) == true) {
                if (!keys.isEmpty()) {
                    keys.append(", ");
                }
                keys.append(key);
            }
        }
        System.out.println(keys);
    }
}





