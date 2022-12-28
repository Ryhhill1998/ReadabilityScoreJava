import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String text = getFileText("in.txt");
            evaluateText(text);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getFileText(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder text = new StringBuilder();

        while (scanner.hasNextLine()) {
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

    private static double getSentenceCount(String text) {
        return getSentences(text).length;
    }

    private static double getWordCount(String text) {
        return getWords(text).length;
    }

    private static double getCharacterCount(String text) {
        return getCharacters(text).length;
    }

    private static double calculateARI(double words, double sentences, double characters) {
        return 4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43;
    }

    private static String getTextAgeRange(double score) {
        String ageRange;

        if (score < 2) {
            ageRange = "5-6";
        } else if (score < 3) {
            ageRange = "6-7";
        } else if (score < 4) {
            ageRange = "7-8";
        } else if (score < 5) {
            ageRange = "8-9";
        } else if (score < 6) {
            ageRange = "9-10";
        } else if (score < 7) {
            ageRange = "10-11";
        } else if (score < 8) {
            ageRange = "11-12";
        } else if (score < 9) {
            ageRange = "12-13";
        } else if (score < 10) {
            ageRange = "13-14";
        } else if (score < 11) {
            ageRange = "14-15";
        } else if (score < 12) {
            ageRange = "15-16";
        } else if (score < 13) {
            ageRange = "16-17";
        } else if (score < 14) {
            ageRange = "17-18";
        } else {
            ageRange = "18-22";
        }

        return ageRange;
    }

    private static void evaluateText(String text) {
        double words = getWordCount(text);
        double sentences = getSentenceCount(text);
        double characters = getCharacterCount(text);
        double textARI = calculateARI(words, sentences, characters);

        System.out.printf("Words: %.0f\n", words);
        System.out.printf("Sentences: %.0f\n", sentences);
        System.out.printf("Characters: %.0f\n", characters);
        System.out.printf("The score is: %.2f\n", textARI);
        System.out.println("This text should be understood by "
                + getTextAgeRange(Math.ceil(textARI))
                + " year-olds.");
    }
}
