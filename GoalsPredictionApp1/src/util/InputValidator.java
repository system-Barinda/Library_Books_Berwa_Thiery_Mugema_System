package util;

public class InputValidator {

    public static void validateGoals(int goals) throws Exception {
        if (goals < 0) {
            throw new Exception("Goals must be positive numbers");
        }
    }

    public static boolean isNumber(String value) {
        return value.matches("\\d+");
    }
}
