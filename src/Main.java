import java.util.Scanner;

public class Main {
    public static final String[] ROMAN_NUMBERS = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    public static final String[] ARABIC_NUMBERS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static void main(String[] args) throws CalculatorException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        System.out.println(calc(input));
    }

    public static String calc(String input) throws CalculatorException {
        String[] operands = new String[2];
        char operator = 0;
        int a;
        int b;
        int result;

        input = input.trim();

        int operatorIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/') {
                operatorIndex = i;
                operator = input.charAt(i);
            }
        }

        if (operatorIndex > 0) {
            operands[0] = input.substring(0, operatorIndex).trim();
            operands[1] = input.substring(operatorIndex + 1).trim();
        } else {
            throw new CalculatorException("Incorrect input.");
        }

        boolean isArabic = isArabic(operands);
        boolean isRoman = false;
        if (!isArabic) {
            isRoman = isRoman(operands);
        }

        if (isArabic) {
            a = Integer.parseInt(operands[0]);
            b = Integer.parseInt(operands[1]);
        } else if (isRoman) {
            a = convertRomanToArabic(operands[0]);
            b = convertRomanToArabic(operands[1]);
        } else {
            throw new CalculatorException("Incorrect input.");
        }

        result = switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new CalculatorException("Unknown operator.");
        };

        if (isArabic) {
            return String.valueOf(result);
        } else if (result > 0){
            return convertArabicToRoman(result);
        } else {
            throw new CalculatorException("Incorrect input.");
        }
    }

    public static boolean isArabic(String[] operands) {
        byte arabicNumbersCounter = 0;
        for (String operand : operands) {
            for (String number : ARABIC_NUMBERS) {
                if (operand.equals(number)){
                    arabicNumbersCounter++;
                    break;
                }
            }
        }
        return arabicNumbersCounter == 2;
    }

    public static boolean isRoman(String[] operands) {
        byte romanNumbersCounter = 0;
        for (String operand : operands) {
            for (String number : ROMAN_NUMBERS) {
                if (operand.equals(number)){
                    romanNumbersCounter++;
                    break;
                }
            }
        }
        return romanNumbersCounter == 2;
    }

    public static int convertRomanToArabic(String operand) throws CalculatorException {
        int arabicNumber = 0;
        for (int i = 0; i < ROMAN_NUMBERS.length; i++) {
            if (operand.equals(ROMAN_NUMBERS[i])) {
                arabicNumber = Integer.parseInt(ARABIC_NUMBERS[i]);
                break;
            }
        }
        if (arabicNumber > 0) {
            return arabicNumber;
        } else {
            throw new CalculatorException("Incorrect input.");
        }
    }

    public static String convertArabicToRoman(int x) {
        String[] romanTens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        String[] romanOnes = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        int tensCount = x / 10;
        int onesCount = x % 10;
        return romanTens[tensCount] + romanOnes[onesCount];
    }

}