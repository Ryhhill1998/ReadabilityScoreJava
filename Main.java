import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String text = getInputText();
        evaluateTextLevel(text);
    }

    private static boolean textIsHard(int averageSentenceLength) {
        return averageSentenceLength > 10;
    }

    private static String getInputText() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void evaluateTextLevel(String text) {
        String[] sentences = getSentences(text);
        int averageLength = getAverageSentenceLength(sentences);

        if (textIsHard(averageLength)) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }
    }

    private static String[] getSentences(String text) {
        String[] sentences = text.split("[.?!]");

        for (int i = 0; i < sentences.length; i++) {
            sentences[i] = sentences[i].trim();
        }

        return sentences;
    }

    private static int getAverageSentenceLength(String[] sentences) {
        double sum = 0;

        for (String s : sentences) {
            sum += s.split("\\s").length;
        }

        return (int) Math.ceil(sum / sentences.length);
    }
}
