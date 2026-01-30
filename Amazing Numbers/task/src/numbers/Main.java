package numbers;

import java.text.NumberFormat;
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
    },
    SQUARE {
        @Override
        public boolean testNum(long num) {
            double sqrt = Math.sqrt(num);
            return String.valueOf(sqrt).endsWith(".0");
        }
    },
    SUNNY {
        @Override
        public boolean testNum(long num) {
            return SQUARE.testNum(num + 1);
        }
    },
    JUMPING {
        @Override
        public boolean testNum(long num) {
            if (num < 10) {
                return true;
            }

            String numStr = String.valueOf(num);

            for (int i = 1; i < numStr.length(); i++) {
                int currNum = numStr.charAt(i - 1) - '0';
                int nextNum = numStr.charAt(i) - '0';
                if (currNum == 9 && nextNum != 8) {
                    return false;
                } else if (nextNum != currNum + 1 && nextNum != currNum - 1) {
                    return false;
                }
            }

            return true;
        }
    },
    SAD {
        @Override
        public boolean testNum(long num) {
            return !HAPPY.testNum(num);
        }
    },
    HAPPY {
        @Override
        public boolean testNum(long num) {
            String numStr = String.valueOf(num);

            int n = 10;
            while (n != 0){
                long sum = 0;
                for (int i = 0; i < numStr.length(); i++) {
                    long dig = numStr.charAt(i) - '0';
                    sum += (dig * dig);
                }
                if (sum == 1){
                    return true;
                }
                numStr = String.valueOf(sum);
                n--;
            }
            return false;
        }
    };

    public abstract boolean testNum(long num);


}

public class Main {
    String[][] bannedCombos;
    Scanner in;
    {
        System.out.println("Welcome to Amazing Numbers!\n\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "\t * the first parameter represents a starting number;\n" +
                "\t * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");

        bannedCombos = new String[][]{
                {"EVEN", "ODD"},
                {"-EVEN", "-ODD"},
                {"DUCK", "SPY"},
                {"-DUCK", "-SPY"},
                {"SUNNY", "SQUARE"},
                {"HAPPY", "SAD"},
                {"-HAPPY", "-SAD"}
        };
    }

    public static void main(String[] args) {
        Main init = new Main();
        init.makeRequest();
    }

    private void makeRequest() {
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

                if (entries.length == 1) {
                    singleNumPrint(start);
                } else {
                    List<Properties> searchTerms = new ArrayList<>();
                    List<Properties> exclusions = new ArrayList<>();

                    for (int i = 2; i < entries.length; i++) {
                        if (entries[i].startsWith("-")) {
                            exclusions.add(Properties.valueOf(entries[i].substring(1).toUpperCase()));
                        } else {
                            searchTerms.add(Properties.valueOf(entries[i].toUpperCase()));
                        }
                    }

                    LongStream.iterate(start, n -> 1 + n)
                            .filter(x -> excludes(x, exclusions))
                            .filter(x -> matches(x, searchTerms))
                            .limit(limit)
                            .forEach(this::rangePrint);
                }
            }
        }
        in.close();
    }

    boolean excludes(long num, List<Properties> exclusions) {
        for (Properties exclusion : exclusions) {
            if (exclusion.testNum(num)) {
                return false;
            }
        }
        return true;
    }

    boolean matches(long num, List<Properties> searchTerms) {
        for (Properties searchTerm : searchTerms) {
            if (!searchTerm.testNum(num)) {
                return false;
            }
        }
        return true;
    }

    boolean isValidRequest(String request) {

        if (request.isBlank() || request.equals(" ")) {
            return false;
        }
        String[] entries = request.split(" ");

        if (!isNaturalNumber(entries[0])) {
            System.out.println("The first parameter should be a natural number or zero.");
            return false;
        }

        if (entries.length > 1 && !isNaturalNumber(entries[1])) {
            System.out.println("The second parameter should be a natural number.");
            return false;
        }

        Set<String> validFilters = getValidFiltersSet(entries);

        if (entries.length > 2 && (validFilters.isEmpty() || hasBannedCombo(validFilters) )){
            return false;
        }

        return true;
    }

    Set<String> getValidFiltersSet (String[] entries){
        List<String> wrongFilters = new ArrayList<>();
        Set<String> validFilters = new HashSet<>();

        for (int e = 2; e < entries.length; e++) {
            if (!isValidFilter(entries[e])) {
                wrongFilters.add(entries[e].toUpperCase());
            }else{
                validFilters.add((entries[e].toUpperCase()));
            }
        }

        if (!wrongFilters.isEmpty()){
            if(wrongFilters.size() == 1) {
                System.out.println("The property " + wrongFilters + " is wrong.");
            }else{
                System.out.println("The properties " + wrongFilters + " are wrong.");
            }
            System.out.println("Available properties: " + Arrays.stream(Properties.values()).toList());
            return new HashSet<>();
        }
        return validFilters;
    }


   boolean hasBannedCombo(Set<String> validFilters) {

        List<String> bannedList = new ArrayList<>();

        for (int i = 0; i < bannedCombos.length; i++) {
            String oppositeFilter1 = getLogicalOpposite((bannedCombos[i][0]));
            String oppositeFilter2 = getLogicalOpposite((bannedCombos[i][1]));

            if (validFilters.contains(bannedCombos[i][0]) && validFilters.contains(oppositeFilter1)){
                bannedList.add(bannedCombos[i][0]);
            }

            if (validFilters.contains(bannedCombos[i][1]) && validFilters.contains(oppositeFilter2)){
                bannedList.add(bannedCombos[i][1]);
            }

            else if (validFilters.contains(bannedCombos[i][0]) && validFilters.contains(bannedCombos[i][1])){
                bannedList.add(bannedCombos[i][0]);
                bannedList.add(bannedCombos[i][1]);
            }
        }

        if (!bannedList.isEmpty()) {
            System.out.println("The request contains mutually exclusive properties: " + bannedList);
            System.out.println("There are no numbers with these properties.");
            return true;
        }

        return false;
    }

    String getLogicalOpposite(String prop){
        return prop.startsWith("-") ? prop.substring(1) : "-" + prop;
    }

    boolean isValidFilter(String filter) {

        for (int i = 0; i < Properties.values().length; i++) {
            filter = filter.startsWith("-") ? filter.substring(1) : filter;
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
        NumberFormat formatter = NumberFormat.getNumberInstance();
        System.out.println("Properties of " + formatter.format(currNum));
        for (int i = 0; i < Properties.values().length; i++) {
            System.out.println("\t" + Properties.values()[i].name().toLowerCase()
                    + ": " + Properties.values()[i].testNum(currNum));
        }
    }

    private void rangePrint(long currNum) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        System.out.print(formatter.format(currNum) + " is ");
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
