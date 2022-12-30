import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        try {
//            String filePath = args[0];
//            String text = getFileText(filePath);
//            evaluateText(text);
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }

        String text = "This is the front page of the Simple English Wikipedia. Wikipedias are places where people work together to write encyclopedias in different languages. We use Simple English words and grammar here. The Simple English Wikipedia is for everyone! That includes children and adults who are learning English. There are 142,262 articles on the Simple English Wikipedia. All of the pages are free to use. They have all been published under both the Creative Commons License and the GNU Free Documentation License. You can help here! You may change these pages and make new pages. Read the help pages and other good pages to learn how to write pages here. If you need help, you may ask questions at Simple talk. Use Basic English vocabulary and shorter sentences. This allows people to understand normally complex terms or phrases.";
        System.out.println(getWordCount(text));
        System.out.println(getSentenceCount(text));
        System.out.println(getCharacterCount(text));
        System.out.println(getSyllableCount(text));
        System.out.println(getPolysyllableCount(text));
    }

    private static void printMenu() {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
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

    private static int countSyllables(String word) {
        String vowels = "aeiouy";

        String[] characters = word.split("");
        int syllables = 0;
        boolean previousWasVowel = false;

        for (int i = 0; i < characters.length; i++) {
            String character = characters[i].toLowerCase();

            if (i == characters.length - 1 && character.equals("e")) {
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

        return syllables;
    }

    private static int getSyllableCount(String text) {
        String[] words = getWords(text);
        int syllables = 0;

        for (String word : words) {
            syllables += countSyllables(word);
        }

        return syllables;
    }

    private static boolean isPolySyllable(String word) {
        return countSyllables(word) > 2;
    }

    private static int getPolysyllableCount(String text) {
        String[] words = getWords(text);
        int polySyllables = 0;

        for (String word : words) {
            if (isPolySyllable(word)) {
                polySyllables++;
            }
        }

        return polySyllables;
    }
}

