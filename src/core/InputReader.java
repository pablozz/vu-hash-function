package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class InputReader {

    public String readFile(String fileName) {
        try {
            return Files.readString(Path.of("./inputs/" + fileName));
        } catch (IOException e) {
            System.out.println("Couldn't read this file");
            return null;
        }
    }

    public String readStringFromArgs(String[] args) {
        if (args.length == 1) {
            try {
                return Files.readString(Path.of("./inputs/" + args[0]));
            } catch (IOException e) {
                System.out.println("Couldn't read this file");
            }
        } else {
            return readUserInput("Enter an input to hash:");
        }

        return "";
    }

    public String readUserInput(String output) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(output);

        return scanner.nextLine();
    }
}