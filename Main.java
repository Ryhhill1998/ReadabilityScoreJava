import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String text = getInputText();
        printTextLevel(text);
    }

    private static boolean textIsHard(String text) {
        return text.length() > 100;
    }

    private static String getInputText() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void printTextLevel(String text) {
        if (textIsHard(text)) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }
    }
}