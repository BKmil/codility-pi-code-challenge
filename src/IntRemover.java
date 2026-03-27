import java.util.HashSet;

public class IntRemover {

    private void initializeLetterCounts(String P, String Q, int[] letters, int[][] lettersLeft, HashSet<Character> usedChars) {
        int len = P.length();
        for (int i = 0; i < len; i++) {
            char charP = P.charAt(i);
            char charQ = Q.charAt(i);
            int pIdx = charP - 'a';
            int qIdx = charQ - 'a';

            letters[pIdx]++;
            letters[qIdx]++;
            if(pIdx != qIdx) {
                lettersLeft[pIdx][qIdx]++;
                lettersLeft[qIdx][pIdx]++;
            }
            usedChars.add(charQ);
            usedChars.add(charP);
        }
    }

    private void removeChar(int[][] lettersLeft, int[] letters, HashSet<Character> usedChars, char c) {
        int x = c - 'a';
        for(char u : usedChars) {
            int i = u - 'a';
            if(i == x) {
                letters[i] = 0;
            }
            else {
                letters[i]-=lettersLeft[i][x];
                lettersLeft[i][x] = 0;
                lettersLeft[x][i] = 0;
            }
        }
        usedChars.remove(c);
    }

    public void solution(String P, String Q){
        int[] letters = new int[26];
        int[][] lettersLeft = new int[26][26];
        HashSet<Character> usedChars = new HashSet<>();
        initializeLetterCounts(P, Q, letters, lettersLeft, usedChars);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                System.out.print(lettersLeft[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for(int i = 0; i < 5; i++) {
            System.out.print(letters[i] + " ");
        }
        System.out.println();
        System.out.println();
        removeChar(lettersLeft, letters, usedChars, 'c');
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                System.out.print(lettersLeft[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for(int i = 0; i < 5; i++) {
            System.out.print(letters[i] + " ");
        }
    }

    public static void main(String[] args) {
        String P = "bacdec", Q = "bbedad";
        IntRemover rem = new IntRemover();
        rem.solution(P, Q);
    }
}
