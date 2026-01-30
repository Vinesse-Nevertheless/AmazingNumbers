package numbers;

import java.util.Scanner;

public class Stage1Buzz {
    public static void main(String[] args) {
        Stage1Buzz init = new Stage1Buzz();
        init.requestNumber();
    }


    void requestNumber() {
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Enter a natural number: ");
            int num = in.nextInt();

            if (!isNaturalNum(num)) {
                return;
            }

            checkOddOrEven(num);
            checkIfBuzzNum(num);

        } catch (Exception e) {
            System.out.println("This is not a whole number.");
        }
        in.close();
    }

    boolean isNaturalNum(int num) {
        if (num < 1) {
            System.out.println("This number is not natural!");
            return false;
        }
        return true;
    }

    void checkOddOrEven(int num) {
        if (num % 2 == 0) {
            System.out.println("This number is Even.");
        } else {
            System.out.println("This number is Odd.");
        }
    }

    void checkIfBuzzNum(int num) {
        StringBuilder reason = new StringBuilder();
        String numStr = String.valueOf(num);

        if (num % 7 == 0) {
            reason.append(num).append(" is divisible by 7.");
        }
        if (numStr.charAt(numStr.length() - 1) == '7') {
            if (reason.isEmpty()) {
                reason.append(num).append(" ends with 7.");
            } else {
                reason.deleteCharAt(reason.length() - 1);
                reason.append(" and ends with 7.");
            }
        }


        if (reason.isEmpty()) {
            System.out.println("It is not a Buzz number.");
            System.out.println("Explanation: ");
            System.out.println(num + " is neither divisible by 7 nor does it end with 7.");
        } else {
            System.out.println("It is a Buzz number.");
            System.out.println("Explanation: ");
            System.out.println(reason);
        }

    }
}
