package numbers;

import java.util.*;
import java.util.stream.LongStream;

enum Properties {
    EVEN {
        @Override
        public boolean testNum(long num) {
            return num % 2 == 0;
        }
    },
    ODD {
        @Override
        public boolean testNum(long num) {
            return num % 2 == 1;
        }
    },
    BUZZ {
        @Override
        public boolean testNum(long num) {
            String numStr = String.valueOf(num);

            return num % 7 == 0 || numStr.charAt(numStr.length() - 1) == '7';
        }
    },
    DUCK {
        @Override
        public boolean testNum(long num) {
            String numStr = String.valueOf(num);

            for (int i = 0; i < numStr.length(); i++) {
                if (numStr.charAt(i) == '0') {
                    return true;
                }
            }
            return false;
        }
    },
    PALINDROMIC {
        @Override
        public boolean testNum(long num) {
            String numStr = String.valueOf(num);
            int head = 0;
            int tail = numStr.length() - 1;

            while (head <= tail) {
                if (numStr.charAt(head) != numStr.charAt(tail)) {
                    return false;
                }
                head++;
                tail--;
            }
            return true;
        }
    },
    GAPFUL {
        @Override
        public boolean testNum(long num) {
            String numStr = String.valueOf(num);

            if (numStr.length() < 3) {
                return false;
            }

            char head = numStr.charAt(0);
            char tail = numStr.charAt(numStr.length() - 1);

            long divisor = Long.parseLong(head + "" + tail);

            return num % divisor == 0;
        }
    },
    SPY {
        @Override
        public boolean testNum(long num) {
            String numStr = String.valueOf(num);

            if (num < 10) {
                return true;
            }

            long sum = 0;
            long prod = 1;

            for (int i = 0; i < numStr.length(); i++) {
                sum += numStr.charAt(i) - '0';
                prod *= numStr.charAt(i) - '0';
            }

            return sum == prod;
        }
    };

    public abstract boolean testNum(long num);

}

public class Stage5Spy {
    
    {
        System.out.println("Welcome to Amazing Numbers!\n\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "\t * the first parameter represents a starting number;\n" +
                "\t * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and a property to search for;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
    }

    public static void main(String[] args) {
        Stage5Spy init = new Stage5Spy();
        init.makeRequest();
    }

    private void makeRequest() {
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
                String[] entries = request.split(" ");
                long start = Long.parseLong(entries[0]);
                long limit = entries.length > 1 ? Long.parseLong(entries[1]) : 1;

                switch (entries.length) {
                    case 1 -> {
                        singleNumPrint(start);
                    }
                    case 2 -> {
                        LongStream.iterate(start, n -> 1 + n)
                                .limit(limit)
                                .forEach(this::rangePrint);
                    }
                    case 3 -> {
                        String filter = entries[2].toUpperCase();
                        LongStream.iterate(start, n -> 1 + n)
                                .filter(x -> Properties.valueOf(filter).testNum(x))
                                .limit(limit)
                                .forEach(this::rangePrint);
                    }
                }
            }
        }
        in.close();
    }


    boolean isValidRequest(String request) {

        if (request.isBlank() || request.equals(" ")) {
            return false;
        }
        String[] entries = request.split(" ");

        //ensure no more than 3 args are passed
        if (entries.length > 3) {
            return false;
        }

        if (!isNaturalNumber(entries[0])) {
            System.out.println("The first parameter should be a natural number or zero.");
            return false;
        }

        if (entries.length > 1 && !isNaturalNumber(entries[1])) {
            System.out.println("The second parameter should be a natural number.");
            return false;
        }

        for (int e = 2; e < entries.length; e++) {
            if (!isValidFilter(entries[e])) {
                StringBuilder invalidFilter = new StringBuilder();
                invalidFilter.append("The property [").append(entries[e].toUpperCase()).append("] is wrong.")
                        .append("\n");

                invalidFilter.append("Available properties: ");
                List<Properties> propList = Arrays.stream(Properties.values()).toList();
                invalidFilter.append(propList);

                System.out.println(invalidFilter);
                return false;
            }
        }

        return true;
    }

    boolean isValidFilter(String filter) {

        for (int i = 0; i < Properties.values().length; i++) {
            if (filter.equalsIgnoreCase(Properties.values()[i].name())) {
                return true;
            }
        }
        return false;
    }

    private boolean isNaturalNumber(String request) {

        for (int i = 0; i < request.length(); i++) {
            if (!Character.isDigit(request.charAt(i)) || request.equals("0")) {
                return false;
            }
        }
        return true;

    }

    private void singleNumPrint(long currNum) {
        System.out.println("Properties of " + currNum);
        for (int i = 0; i < Properties.values().length; i++) {
            System.out.println("\t" + Properties.values()[i].name().toLowerCase()
                    + ": " + Properties.values()[i].testNum(currNum));
        }
    }

    private void rangePrint(long currNum) {

        System.out.print(currNum + " is ");
        StringBuilder keys = new StringBuilder();
        for (int i = 0; i < Properties.values().length; i++) {
            if (Properties.values()[i].testNum(currNum)) {
                if (!keys.isEmpty()) {
                    keys.append(", ");
                }
                keys.append(Properties.values()[i].name().toLowerCase());
            }
        }
        System.out.println(keys);
    }
}

