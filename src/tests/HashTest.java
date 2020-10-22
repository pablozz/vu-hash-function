package tests;

import core.HashGenerator;
import core.InputReader;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HashTest {

    private final HashGenerator hashGenerator = new HashGenerator();
    private final Random random = new Random();
    private final InputReader inputReader = new InputReader();

    public void testHashLength() {
        String stringLen1 = inputReader.readFile("char1.txt");
        String stringLen2 = inputReader.readFile("long_string1.txt");
        String hash1 = hashGenerator.getHash(stringLen1);
        String hash2 = hashGenerator.getHash(stringLen2);

        System.out.println("Test 1, 2:");
        System.out.println("Hash 1: " + hash1);
        System.out.println("Hash 2: " + hash2);
        System.out.println("String length: " + stringLen1.length() + "; Hash length: " + hash1.length());
        System.out.println("String length: " + stringLen2.length() + "; Hash length: " + hash2.length());
    }

    public void testHashDeterminism() {
        String string1 = inputReader.readFile("long_string1.txt");
        String string2 = inputReader.readFile("long_string1.txt");
        String hash1 = hashGenerator.getHash(string1);
        String hash2 = hashGenerator.getHash(string2);

        System.out.println("\nTest 3:");
        System.out.println("Hash 1: " + hash1);
        System.out.println("Hash 2: " + hash2);
        System.out.println(("Hashes from same string are equal: " + hash1.equals(hash2)));
    }

    public void testHashByLine() {
        long start = System.currentTimeMillis();

        String text = inputReader.readFile("konstitucija.txt");

        if(text != null) {
            Scanner scanner = new Scanner(text);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                hashGenerator.getHash(line);
            }

            scanner.close();

            long finish = System.currentTimeMillis();

            System.out.println("\nTest 4:");
            System.out.println("Time it took to hash every line of \"konstitucija.txt\": " + (finish - start) + "ms");
        }
    }

    public void testCollisionResistance() {
        ArrayList<WordPair> allWordPairs = new ArrayList<>();
        allWordPairs.addAll(generateRandomWordsArray(10));
        allWordPairs.addAll(generateRandomWordsArray(100));
        allWordPairs.addAll(generateRandomWordsArray(500));
        allWordPairs.addAll(generateRandomWordsArray(1000));

        int collisionsCount = 0;
        for (WordPair wordPair : allWordPairs) {
            String firstHash = wordPair.getFirstWordHash();
            String secondHash = wordPair.getSecondWordHash();

            if (firstHash.equals(secondHash)) {
                collisionsCount++;
            }
        }

        System.out.println("\nTest 6:");
        System.out.println("Collisions: " + collisionsCount + "/" + allWordPairs.size());
    }

    public void testAvalancheEffect() {
        ArrayList<WordPair> allWordPairs = new ArrayList<>();
        allWordPairs.addAll(generateRandomSimilarWordsArray(10));
        allWordPairs.addAll(generateRandomSimilarWordsArray(100));
        allWordPairs.addAll(generateRandomSimilarWordsArray(500));
        allWordPairs.addAll(generateRandomSimilarWordsArray(1000));

        System.out.println("\nTest 5, 7:");

        double equalBitPercentageSum = 0;
        double minEqualBitPercentage = 100;
        double maxEqualBitPercentage = 0;

        for (WordPair wordPair: allWordPairs) {
            int hashLength = wordPair.getHashLength();
            int equalBitsCount = 0;
            String firstWordHash = wordPair.getFirstWordHash();
            String secondWordHash = wordPair.getSecondWordHash();

            for (int i = 0; i < hashLength; i++) {
                String firstHashBinary = Integer.toBinaryString(firstWordHash.charAt(i));
                String secondHashBinary = Integer.toBinaryString(secondWordHash.charAt(i));
                String firstHashBinaryWithLeadingZeros = String.format("%8s", firstHashBinary).replace(' ', '0');
                String secondHashBinaryWithLeadingZeros = String.format("%8s", secondHashBinary).replace(' ', '0');

                for (int j = 0; j < 8; j++) {
                    if(firstHashBinaryWithLeadingZeros.charAt(j) == secondHashBinaryWithLeadingZeros.charAt(j)) {
                        equalBitsCount++;
                    }
                }
            }

            double equalBitsPercentage = ((double) equalBitsCount * 100) / (hashLength * 8);

            if (equalBitsPercentage < minEqualBitPercentage) {
                minEqualBitPercentage = equalBitsPercentage;
            }

            if (equalBitsPercentage > maxEqualBitPercentage) {
                maxEqualBitPercentage = equalBitsPercentage;
            }

            equalBitPercentageSum += equalBitsPercentage;
        }

        double equalBitPercentage = equalBitPercentageSum / allWordPairs.size();

        System.out.printf("Minimal percentage of equal bits: %.2f\n", minEqualBitPercentage);
        System.out.printf("Maximal percentage of equal bits: %.2f\n", maxEqualBitPercentage);
        System.out.printf("Percentage of equal bits: %.2f", equalBitPercentage);
        System.out.print("%\n");

        double equalHashPercentageSum = 0;
        double minEqualHashPercentage = 100;
        double maxEqualHashPercentage = 0;

        for (WordPair wordPair: allWordPairs) {
            int hashLength = wordPair.getHashLength();
            int equalHashSymbolsCount = 0;
            for (int i = 0; i < hashLength; i++) {
                if (wordPair.getFirstWordHash().charAt(i) == wordPair.getSecondWordHash().charAt(i)) {
                    equalHashSymbolsCount++;
                }
            }

            double equalHashPercentage = (double) equalHashSymbolsCount * 100 / hashLength;

            if (equalHashPercentage < minEqualHashPercentage) {
                minEqualHashPercentage = equalHashPercentage;
            }

            if (equalHashPercentage > maxEqualHashPercentage) {
                maxEqualHashPercentage = equalHashPercentage;
            }

            equalHashPercentageSum += equalHashPercentage;
        }

        System.out.printf("Minimal percentage of equal hash characters: %.2f\n", minEqualHashPercentage);
        System.out.printf("Maximal percentage of equal hash characters: %.2f\n", maxEqualHashPercentage);
        System.out.printf("Percentage of equal hash symbols: %.2f", equalHashPercentageSum / allWordPairs.size());
        System.out.print('%');
    }

    private ArrayList<WordPair> generateRandomWordsArray(int wordLength) {
        final int ARRAY_LENGTH = 25000;
        ArrayList<WordPair> randomWords = new ArrayList<>();

        for (int i = 0; i < ARRAY_LENGTH; i++) {
            WordPair wordPair = new WordPair(generateRandomWord(wordLength), generateRandomWord(wordLength));

            randomWords.add(wordPair);
        }

        return randomWords;
    }

    private ArrayList<WordPair> generateRandomSimilarWordsArray(int wordLength) {
        final int ARRAY_LENGTH = 25000;
        ArrayList<WordPair> randomWords = new ArrayList<>();

        String firstWord;
        String secondWord;
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            firstWord = generateRandomWord(wordLength);
            secondWord = changeRandomWordLetter(firstWord);
            WordPair wordPair = new WordPair(firstWord, secondWord);

            randomWords.add(wordPair);
        }

        return randomWords;
    }

    private String changeRandomWordLetter(String word) {
        int randomIndex = random.nextInt(word.length());

        StringBuilder modifiedWord = new StringBuilder(word);
        char randomChar;

        while (word.equals(modifiedWord.toString())) {
            randomChar = generateRandomWord(1).charAt(0);
            modifiedWord.setCharAt(randomIndex, randomChar);
        }

        return modifiedWord.toString();
    }

    private String generateRandomWord(int wordLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .limit(wordLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static class WordPair {
        private final String firstWord;
        private final String secondWord;
        private final HashGenerator hashGenerator = new HashGenerator();


        public WordPair(String firstWord, String secondWord) {
            this.firstWord = firstWord;
            this.secondWord = secondWord;
        }

        public String getFirstWordHash() {
            return hashGenerator.getHash(firstWord);
        }

        public String getSecondWordHash() {
            return hashGenerator.getHash(secondWord);
        }

        public int getHashLength() {
            return getFirstWordHash().length();
        }
    }
}
