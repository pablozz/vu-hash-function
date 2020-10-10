import core.HashGenerator;
import core.InputReader;
import tests.HashTest;

public class Main {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        String input = inputReader.readString(args);

//        HashGenerator hashGenerator = new HashGenerator();
//        String hash = hashGenerator.getHash(content);

//        System.out.println(hash);


        HashTest test = new HashTest();

//        test.hashByLine(input);
        test.testCollisionResistance();
    }
}
