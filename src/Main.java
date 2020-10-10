import core.HashGenerator;
import core.InputReader;
import tests.HashTest;

public class Main {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        String input = inputReader.readString(args);

        HashGenerator hashGenerator = new HashGenerator();
//        String hash = hashGenerator.getHash(content);

//        System.out.println(hash);


//        HashTest test = new HashTest();

        System.out.println(hashGenerator.getHash("a"));
        System.out.println(hashGenerator.getHash("b"));
        System.out.println("");
        System.out.println(hashGenerator.getHash("asdajias1ndnsaoinas5oinvasoivnasodinvoiAN3OIASNViu5BNACVIUBYUIBuyBVUYvbUOIYBIOYUBOIuybIUBI234Oub"));
        System.out.println(hashGenerator.getHash("asdajias1ndnsaoinas5oinvasoivnasodinvoiAN3OIASNViu5BNACVIUBYUIBuyBVUYvbaOIYBIOYUBOIuybIUBI234Oub"));



//        test.hashByLine(input);
//        test.testCollisionResistance();
    }
}
