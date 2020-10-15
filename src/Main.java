import core.HashGenerator;
import core.InputReader;
import tests.HashTest;

public class Main {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();

        String programType = inputReader.readUserInput("1 - hash input; 2 - test:");

        if (programType.equals("1")) {
            String input = inputReader.readStringFromArgs(args);

            HashGenerator hashGenerator = new HashGenerator();
            String hash = hashGenerator.getHash(input);

            System.out.println(hash);
        } else if (programType.equals("2")) {
            HashTest test = new HashTest();
            test.testHashLength();
            test.testHashDeterminism();
            test.testHashByLine();
            test.testCollisionResistance();
            test.testAvalancheEffect();
        } else {
            System.out.println("Invalid input.");
        }
    }
}
