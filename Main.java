import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = getFilePath();
            String text = getFileText(filePath);
            evaluateText(text);
            printMenu();
            evaluateSelection(getSelection(), text);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file you wish to read from: ");
        return scanner.nextLine();
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

    private static void evaluateText(String text) {
        double words = getWordCount(text);
        double sentences = getSentenceCount(text);
        double characters = getCharacterCount(text);
        double syllables = getSyllableCount(text);
        double polysyllables = getPolysyllableCount(text);

        System.out.printf("Words: %.0f\n", words);
        System.out.printf("Sentences: %.0f\n", sentences);
        System.out.printf("Characters: %.0f\n", characters);
        System.out.printf("Syllables: %.0f\n", syllables);
        System.out.printf("Polysyllables: %.0f\n", polysyllables);
    }

    private static void printMenu() {
        System.out.print("\nEnter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
    }

    private static String getSelection() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toUpperCase();
    }

    private static void evaluateSelection(String selection, String text) {
        double score, age;

        switch (selection) {
            case "ARI":
                // get text ARI score
                displayARIScore(text);
                break;
            case "FK":
                // get text FK score
                displayFKScore(text);
                break;
            case "SMOG":
                // get text SMOG score
                displaySMOGScore(text);
                break;
            case "CL":
                // get text CL score
                displayCLScore(text);
                break;
            case "ALL":
                // get all 4 scores and average them
                displayARIScore(text);
                displayFKScore(text);
                displaySMOGScore(text);
                displayCLScore(text);
                System.out.println();
                displayAverageTextAge(text);
                break;
            default:
                System.out.println("Invalid entry!");
        }
    }

    private static double getScoreUpperBound(double score) {
        return Math.ceil(score);
    }

    private static String[] getSentences(String text) {
        String[] sentences = text.split("[.?!]");

        for (int i = 0; i < sentences.length; i++) {
            sentences[i] = sentences[i].trim();
        }

        return sentences;
    }

    private static String[] getWords(String text) {
        return text.replace(".", "")
                .replace("?", "")
                .replace("!", "")
                .split(" ");
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

    private static double countSyllables(String word) {
        String vowels = "aeiouy";

        String[] characters = word.split("");
        double syllables = 0;
        boolean previousWasVowel = false;
        int length = characters.length;

        for (int i = 0; i < length; i++) {
            String character = characters[i].toLowerCase();

            if (i == length - 1 && character.equals("e")) {
                break;
            }

            if (vowels.contains(character)) {
                if (!previousWasVowel) {
                    syllables++;
                    previousWasVowel = true;
                }
            } else {
                previousWasVowel = false;
            }
        }

        if (syllables == 0) {
            syllables = 1;
        }

        if (word.endsWith("le")) {
            if (!vowels.contains(characters[length - 3])) {
                syllables++;
            }
        }

        return syllables;
    }

    private static double getSyllableCount(String text) {
        String[] words = getWords(text);
        double syllables = 0;

        for (String word : words) {
            syllables += countSyllables(word);
        }

        return syllables;
    }

    private static boolean isPolysyllable(String word) {
        return countSyllables(word) > 2;
    }

    private static double getPolysyllableCount(String text) {
        String[] words = getWords(text);
        double polysyllables = 0;

        for (String word : words) {
            if (isPolysyllable(word)) {
                polysyllables++;
            }
        }

        return polysyllables;
    }

    private static double calculateARI(double words, double sentences, double characters) {
        return 4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43;
    }

    private static double calculateFK(double words, double sentences, double syllables) {
        return 0.39 * (words / sentences) + 11.8 * (syllables / words) - 15.59;
    }

    private static double calculateSMOG(double polysyllables, double sentences) {
        return 1.043 * Math.sqrt(polysyllables * (30 / sentences)) + 3.1291;
    }

    private static double calculateCL(double L, double S) {
        return 0.0588 * L - 0.296 * S - 15.8;
    }

    private static double getTextARIScore(String text) {
        double words = getWordCount(text);
        double sentences = getSentenceCount(text);
        double characters = getCharacterCount(text);
        return calculateARI(words, sentences, characters);
    }

    private static double getTextFKScore(String text) {
        double words = getWordCount(text);
        double sentences = getSentenceCount(text);
        double syllables = getSyllableCount(text);
        return calculateFK(words, sentences, syllables);
    }

    private static double getTextSMOGScore(String text) {
        double polysyllables = getPolysyllableCount(text);
        double sentences = getSentenceCount(text);
        return calculateSMOG(polysyllables, sentences);
    }

    private static double getTextCLScore(String text) {
        double words = getWordCount(text);
        double characters = getCharacterCount(text);
        double averageCharactersPer100Words = characters / (words / 100.0);
        double sentences = getSentenceCount(text);
        double averageSentencesPer100Words = sentences / (words / 100.0);
        return calculateCL(averageCharactersPer100Words, averageSentencesPer100Words);
    }

    private static double getTextAge(double score) {
        double age;

        if (score == 14) {
            age = 22;
        } else {
            age = score + 5;
        }

        return age;
    }

    private static double getAverageTextAge(String text) {
        double ageSum = 0;

        double textARI = getScoreUpperBound(getTextARIScore(text));
        double textFK = getScoreUpperBound(getTextFKScore(text));
        double textSMOG = getScoreUpperBound(getTextSMOGScore(text));
        double textCL = getScoreUpperBound(getTextCLScore(text));

        ageSum += (getTextAge(textARI) + getTextAge(textFK) + getTextAge(textSMOG) + getTextAge(textCL + 1));

        return ageSum / 4;
    }

    private static void displayARIScore(String text) {
        double score = getTextARIScore(text);
        double age = getTextAge(getScoreUpperBound(score));
        System.out.printf("\nAutomated Readability Index: %.2f (about %.0f-year-olds).",
                score, age);
    }

    private static void displayFKScore(String text) {
        double score = getTextFKScore(text);
        double age = getTextAge(getScoreUpperBound(score));
        System.out.printf("\nFlesch–Kincaid readability tests: %.2f (about %.0f-year-olds).",
                score, age);
    }

    private static void displaySMOGScore(String text) {
        double score = getTextSMOGScore(text);
        double age = getTextAge(getScoreUpperBound(score));
        System.out.printf("\nSimple Measure of Gobbledygook: %.2f (about %.0f-year-olds).",
                score, age);
    }

    private static void displayCLScore(String text) {
        double score = getTextCLScore(text);
        double age = getTextAge(getScoreUpperBound(score) + 1);
        System.out.printf("\nColeman–Liau index: %.2f (about %.0f-year-olds).",
                score, age);
    }

    private static void displayAverageTextAge(String text) {
        double averageTextAge = getAverageTextAge(text);
        System.out.printf("\nThis text should be understood in average by %.2f-year-olds.",
                averageTextAge);
    }
}

