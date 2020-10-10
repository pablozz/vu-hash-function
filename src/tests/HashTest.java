package tests;

import core.HashGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HashTest {

    public final String input;

    public HashTest(String input) {
        this.input = input;
    }

    public void hashByLine() {
        long start = System.currentTimeMillis();

        Scanner scanner = new Scanner(input);
        HashGenerator hashGenerator = new HashGenerator();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            hashGenerator.getHash(line);
        }
        scanner.close();

        long finish = System.currentTimeMillis();

        System.out.println("Time it took to hash every line: " + (finish - start) + "ms");
    }
}
