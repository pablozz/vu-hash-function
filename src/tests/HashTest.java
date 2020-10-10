package tests;

import core.HashGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HashTest {

    private final HashGenerator hashGenerator = new HashGenerator();

    public void hashByLine(String input) {
        long start = System.currentTimeMillis();

        Scanner scanner = new Scanner(input);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            hashGenerator.getHash(line);
        }
        scanner.close();

        long finish = System.currentTimeMillis();

        System.out.println("Time it took to hash every line: " + (finish - start) + "ms");
    }

    public void testCollisionResistance() {
        final int ARRAY_LENGTH = 100000;

        ArrayList<WordPair> allWords = new ArrayList<>();
        allWords.addAll(generateRandomWordsArray(10));
        allWords.addAll(generateRandomWordsArray(100));
        allWords.addAll(generateRandomWordsArray(500));
        allWords.addAll(generateRandomWordsArray(1000));

        int collisionsCount = 0;

        for(int i = 0; i < ARRAY_LENGTH; i++) {
            String firstHash = allWords.get(i).getFirstWordHash();
            String secondHash = allWords.get(i).getSecondWordHash();

            if(firstHash.equals(secondHash)) {
                collisionsCount++;

                System.out.println(firstHash + " | " + secondHash);
            }
        }

        System.out.println(collisionsCount);
    }

    private ArrayList<WordPair> generateRandomWordsArray(int wordLength) {
        ArrayList<WordPair> randomWords = new ArrayList<>();
        final int ARRAY_LENGTH = 25000;

        for(int i = 0; i < ARRAY_LENGTH; i++) {
            WordPair wordPair = new WordPair(generateRandomWord(wordLength), generateRandomWord(wordLength));

            randomWords.add(wordPair);
        }

        return randomWords;
    }

    private String generateRandomWord(int wordLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();

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
    }
}
