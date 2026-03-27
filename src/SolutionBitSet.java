import java.util.BitSet;

public class SolutionBitSet {
    public int solution(String P, String Q) {
        boolean[][] relations = new boolean[26][26];

        int duplicates = fillRelations(P, Q, relations);

        BitSet remainingLetters = calculateRemaining(relations);

        int[] min = new int[]{20};
        findFinal(relations, 0, remainingLetters, 0, min);

        return duplicates + min[0];
    }

    private void findFinal(boolean[][] relations, int currentChar, BitSet remainingLetters, int count, int[] minOp)
    {
        if(count >= minOp[0]) { // if count is already higher than the min result, skip
            return;
        }
        if(currentChar > 25) { // if finished loop, new min value is set
            minOp[0] = count;
            return;
        }

        if(remainingLetters.get(currentChar)) { // check if char is present in remainingLetters
            BitSet remaining = (BitSet) remainingLetters.clone(); // creating copies of remainingLetters to pass further

            remaining.clear(currentChar); // delete just the current char
            findFinal(relations, currentChar + 1, remaining, count + 1, minOp); // findFinal with the char deleted

            int countRemovals = 0;

            for(int i = 0; i<26; i++) {
                if(relations[i][currentChar] && remainingLetters.get(i)) {
                    remaining.clear(i); // if char is in relation with currently checked char, remove it from remaining
                    countRemovals++; // count the removals of chars
                }
            }
            findFinal(relations, currentChar + 1, remaining, count + countRemovals, minOp);
        }
        else
            findFinal(relations, currentChar + 1, remainingLetters, count, minOp); // skipping the char not marked as true in remainingLetters
    }

    private int fillRelations(String P, String Q, boolean[][] relations) { // filling relations array
        for(int i = 0; i < P.length(); i++) {
            int p = P.charAt(i) - 'a';
            int q = Q.charAt(i) - 'a';

            relations[p][q] = true;
            relations[q][p] = true;
        }

        int duplicates = 0;
        for(int i = 0; i < 26; i++) { // counting and removing chars occuring as duplicates to minimize the number of further loops
            if(relations[i][i]) {
                duplicates++;
                for(int j = 0; j<26; j++) {
                    relations[i][j] = false;
                    relations[j][i] = false;
                }
            }
        }
        return duplicates;
    }

    private BitSet calculateRemaining(boolean[][] relations) { // BitSet containing chars present in relations array after removing duplicates
        BitSet remaining = new BitSet(26);
        for(int i = 0; i < 26; i++) {
            for(int j = 0; j<26; j++) {
                if(relations[i][j]) {
                    remaining.set(i);
                    break;
                }
            }
        }
        return remaining;
    }
}
