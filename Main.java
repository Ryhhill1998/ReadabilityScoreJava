import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
//            String filePath = args[0];
            String text = getFileText("in.txt");
            System.out.println(getWordCount(text));
            System.out.println(getSentenceCount(text));
            System.out.println(getCharacterCount(text));
            System.out.println(Arrays.toString(getSentences(text)));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getFileText(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder text = new StringBuilder();

        while (scanner.hasNextLine()) {
            if (!text.isEmpty()) {
                text.append(" ");
            }

            text.append(scanner.nextLine());
        }

        return text.toString();
    }

    private static String[] getSentences(String text) {
        String[] sentences = text.split("[.?!]");

        for (int i = 0; i < sentences.length; i++) {
            sentences[i] = sentences[i].trim();
        }

        return sentences;
    }

    private static String[] getWords(String text) {
        return text.split(" ");
    }

    private static String[] getCharacters(String text) {
        text = text.replace(" ", "")
                .replace("\t", "")
                .replace("\n", "");

        return text.split("");
    }

    private static int getSentenceCount(String text) {
        return getSentences(text).length;
    }

    private static int getWordCount(String text) {
        return getWords(text).length;
    }

    private static int getCharacterCount(String text) {
        return getCharacters(text).length;
    }

//    private static int calculateARI() {
//
//    }
}
