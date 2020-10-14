package tests;

import core.HashGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HashTest {

    private final HashGenerator hashGenerator = new HashGenerator();
    private final Random random = new Random();


    public void testHashByLine() {
        long start = System.currentTimeMillis();

        String text;
        try {
            text = Files.readString(Path.of("./inputs/konstitucija.txt"));
            Scanner scanner = new Scanner(text);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                hashGenerator.getHash(line);
            }

            scanner.close();

            long finish = System.currentTimeMillis();

            System.out.println("Time it took to hash every line of \"konstitucija.txt\": " + (finish - start) + "ms");
        } catch (IOException e) {
            System.out.println("Couldn't find file named \"konstitucija.txt\"");
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

        System.out.println("Collisions: " + collisionsCount + "/" + allWordPairs.size());
    }

    public void testAvalancheEffect() {

        ArrayList<WordPair> allWordPairs = new ArrayList<>();
        allWordPairs.addAll(generateRandomSimilarWordsArray(10));
        allWordPairs.addAll(generateRandomSimilarWordsArray(100));
        allWordPairs.addAll(generateRandomSimilarWordsArray(500));
        allWordPairs.addAll(generateRandomSimilarWordsArray(1000));

        double equalBitPercentageSum = 0;
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

                for(int j = 0; j < 8; j++) {
                    if(firstHashBinaryWithLeadingZeros.charAt(j) == secondHashBinaryWithLeadingZeros.charAt(j)) {
                        equalBitsCount++;
                    }
                }
            }
            equalBitPercentageSum += ((double) equalBitsCount * 100) / (hashLength * 8);
        }

        double equalBitPercentage = equalBitPercentageSum / allWordPairs.size();

        System.out.printf("Percentage of equal bits: %.2f", equalBitPercentage);
        System.out.print("%\n");

        double equalHashPercentageSum = 0;
        for (WordPair wordPair: allWordPairs) {
            int hashLength = wordPair.getHashLength();
            int equalHashSymbolsCount = 0;
            for (int i = 0; i < hashLength; i++) {
                if (wordPair.getFirstWordHash().charAt(i) == wordPair.getSecondWordHash().charAt(i)) {
                    equalHashSymbolsCount++;
                }
            }
            equalHashPercentageSum += (double) equalHashSymbolsCount * 100 / hashLength;
        }

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
