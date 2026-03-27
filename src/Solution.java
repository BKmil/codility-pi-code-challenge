public class Solution {
    public int solution(String P, String Q) {

        boolean[][] relations = new boolean[26][26];

        fillRelations(P, Q, relations);

        int duplicates = countDuplitates(relations);

        boolean[] remainingLetters = calculateRemaining(relations);

        int[] min = new int[]{20};
        findFinal(relations, 0, remainingLetters, 0, min);

        return duplicates + min[0];
    }

    private void findFinal(boolean[][] relations, int currentChar, boolean[] remainingLetters, int count, int[] minOp) {
        if (count >= minOp[0]) { // if count is already higher than the min result, skip
            return;
        }

        if (currentChar > 25) { // if finished loop, new min value is set
            minOp[0] = count;
            return;
        }

        if (remainingLetters[currentChar]) { // check if char is present in remainingLetters
            boolean[] remaining = remainingLetters.clone(); // creating a copy of remainingLetters to pass further

            remaining[currentChar] = false; // delete just the current char
            findFinal(relations, currentChar + 1, remaining, count + 1, minOp); // findFinal with the currentChar deleted
            // (other chars related to it will still be considered, however if it was their only relation, the next if condition will not add anything to countRemovals,
            // resulting in executing the recurrence as in else case)

            int countRemovals = 0;

            for (int i = 0; i < 26; i++) {
                if (relations[i][currentChar] && remainingLetters[i]) {
                    remaining[i] = false; // if char is in relation with currently checked char, remove it from remaining
                    countRemovals++; // count the removals of chars
                }
            }
            findFinal(relations, currentChar + 1, remaining, count + countRemovals, minOp); // findFinal with currenChar removed by removing every char related to it


        } else {
            findFinal(relations, currentChar + 1, remainingLetters, count, minOp); // skipping the char not marked as true in remainingLetters
        }
    }

    private void fillRelations(String P, String Q, boolean[][] relations) { // filling relations array
        for (int i = 0; i < P.length(); i++) {
            int p = P.charAt(i) - 'a';
            int q = Q.charAt(i) - 'a';

            relations[p][q] = true;
            relations[q][p] = true;
        }
    }

    private int countDuplitates(boolean[][] relations) {
        int duplicates = 0;
        for (int i = 0; i < 26; i++) { // counting and removing chars occuring as duplicates to minimize the number of further loops
            if (relations[i][i]) {
                duplicates++;
                for (int j = 0; j < 26; j++) {
                    relations[i][j] = false;
                    relations[j][i] = false;
                }
            }
        }
        return duplicates;
    }

    private boolean[] calculateRemaining(boolean[][] relations) { // boolean array containing
        // chars present in relations array after removing duplicates
        boolean[] remaining = new boolean[26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (relations[i][j]) {
                    remaining[i] = true;
                    break;
                }
            }
        }
        return remaining;
    }
}
