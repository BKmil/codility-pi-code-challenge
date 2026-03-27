import java.util.Arrays;

public class SolutionSilver {
    public int solution(String P, String Q) {
        int[] relationsLeft = new int[26];
        boolean[][] relations = new boolean[26][26];

        int duplicates = fillRelations(P, Q, relations, relationsLeft);

        int remainingLetters = calculateRemaining(relationsLeft);

        return duplicates + findFinal(relations, relationsLeft, remainingLetters, 0, new int[]{20});
    }

    private int findFinal(boolean[][] relations, int[] relationsLeft, int remainingLetters, int count, int[] minOp)
    {
        if(remainingLetters <= 0 || count == minOp[0]) {
            minOp[0] = count;
            return minOp[0];
        }

        int[] groups = new int[26];
        int maxGroups = 0, maxGroupId = 0, maxRel = 0;

        for(int i = 0; i<26; i++) {
            if(relationsLeft[i] > maxRel) {
                maxRel = relationsLeft[i];
            }
            if(relationsLeft[i] == 1) {
                for(int j = 0; j<26; j++) {
                    if(relations[i][j]) {
                        groups[j]++;
                        if(groups[j] > maxGroups) {
                            maxGroups = groups[j];
                            maxGroupId = j;
                        }
                    }
                }
            }
        }

        if(maxGroups > 0) {
            remainingLetters -= removeLetter(relations, relationsLeft, maxGroupId);
            return findFinal(relations, relationsLeft, remainingLetters, count + 1, minOp);
        }

        int min = 20;
        int curr = 0;
        for(int i = 0; i<26; i++) {
            if(relationsLeft[i] > 1 && (relationsLeft[i] == maxRel)) {
                int[] relLeftCopy = Arrays.copyOf(relationsLeft, relationsLeft.length);
                boolean[][] relCopy = new boolean[26][26];
                for(int j = 0; j<26; j++) {
                    relCopy[j] = Arrays.copyOf(relations[j], relations[j].length);
                }
                int rem = remainingLetters - removeLetter(relCopy, relLeftCopy, i);
                curr = findFinal(relCopy, relLeftCopy, rem, count+1, minOp);
                if(min > curr) {
                    min = curr;
                }
            }
        }

        return min;
    }

    private int fillRelations(String P, String Q, boolean[][] relations, int[] relationsLeft) {
        for(int i = 0; i < P.length(); i++) {
            int p = P.charAt(i) - 'a';
            int q = Q.charAt(i) - 'a';

            if(p != q) {
                if(!relations[p][q]) {
                    relationsLeft[p]++;
                    relationsLeft[q]++;
                }
            }
            relations[p][q] = true;
            relations[q][p] = true;
        }
        return countDuplicates(relations, relationsLeft);
    }

    private int calculateRemaining(int[] relationsLeft) {
        int count = 0;
        for(int i = 0; i < 26; i++) {
            if(relationsLeft[i] > 0)
                count++;
        }
        return count;
    }

    private int countDuplicates(boolean[][] rel, int[] relationsLeft) {
        int duplicates = 0;
        for(int i = 0; i < 26; i++) {
            if(rel[i][i]) {
                rel[i][i] = false;
                duplicates++;
                removeLetter(rel, relationsLeft, i);
            }
        }
        return duplicates;
    }

    private int removeLetter(boolean[][] rel, int[] relationsLeft, int index) {
        int lettersRemoved = 1;
        for(int i = 0; i<26; i++) {
            if(i != index && rel[i][index]) {
                relationsLeft[i]--;
                if(relationsLeft[i] == 0) {
                    lettersRemoved++;
                    rel[index][i] = false;
                    rel[i][index] = false;
                }
                rel[index][i] = false;
                rel[i][index] = false;
            }
        }
        relationsLeft[index] = 0;
        return lettersRemoved;
    }
}
