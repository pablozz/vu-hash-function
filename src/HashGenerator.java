public class HashGenerator {

    public final int HASH_LENGTH = 64;

    public String getHash(String input) {
        int hashInt = 0;

        for (int inputElem : input.toCharArray()) {
            hashInt += Math.pow(inputElem, 3);
            hashInt += ~inputElem % ~hashInt;
            hashInt += hashInt << 5;
        }

        StringBuilder hashBuilder = new StringBuilder(Integer.toHexString(hashInt));

        while (hashBuilder.length() != HASH_LENGTH) {
            for (int hashElem : hashBuilder.toString().toCharArray()){
                hashElem += (hashElem + ~hashBuilder.length()) * hashInt >> 3;
                hashElem += (hashInt ^ hashElem) | (hashInt | hashElem);
                hashElem += hashElem >> 3;

                hashBuilder.append(Integer.toHexString(hashElem));
            }

            int hashLength = hashBuilder.length();
            if (hashLength > HASH_LENGTH) {
                hashBuilder.delete(HASH_LENGTH, hashLength);
            }
        }

        return  hashBuilder.toString();
    }
}