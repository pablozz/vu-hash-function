package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class InputReader {

    private String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an input to hash:");

        return scanner.nextLine();
    }

    public String readString(String[] args) {
        if(args.length == 1) {
            try {
                return Files.readString(Path.of("./inputs/" + args[0]));
            } catch (IOException e) {
                System.out.println("Couldn't read this file");
            }
        } else {
            return readUserInput();
        }

        return "";
    }
}