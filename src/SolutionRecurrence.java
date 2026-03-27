//We are given two strings P and Q, each consisting of N lowercase English letters. For each position in the strings, we have to choose one letter from either P or Q, in order to construct a new string S, such that the number of distinct letters in S is minimal. Our task is to find the number of distinct letters in the resulting string S.
//
//For example, if P = "ca" and Q = "ab", S can be equal to: "ca", "cb", "aa" or "ab". String "aa" has only one distinct letter ('a'), so the answer is 1 (which is minimal among those strings).
//
//Write a function:
//
//class Solution { public int solution(String P, String Q); }
//
//that, given two strings P and Q, both of length N, returns the minimum number of distinct letters of a string S, that can be constructed from P and Q as described above.
//
//Examples:
//
//1. Given P = "abc", Q = "bcd", your function should return 2. All possible strings S that can be constructed are: "abc", "abd", "acc", "acd", "bbc", "bbd", "bcc", "bcd". The minimum number of distinct letters is 2, which be obtained by constructing the following strings: "acc", "bbc", "bbd", "bcc".
//
//2. Given P = "axxz", Q = "yzwy", your function should return 2. String S must consist of at least two distinct letters in this case. We can construct S = "yxxy", where the number of distinct letters is equal to 2, and this is the only optimal solution.
//
//3. Given P = "bacad", Q = "abada", your function should return 1. We can choose the letter 'a' in each position, so S can be equal to "aaaaa".
//
//4. Given P = "amz", Q = "amz", your function should return 3. The input strings are identical, so the only possible S that can be constructed is "amz", and its number of distinct letters is 3.
//
//Write an efficient algorithm for the following assumptions:
//
//N is an integer within the range [1..200,000];
//strings P and Q are both of length N;
//strings P and Q are made only of lowercase letters (a−z);
//strings P and Q contain a total of at most 20 distinct letters.

import java.util.Arrays;
import java.util.HashSet;

public class SolutionRecurrence {

    public int solution(String P, String Q) {
        int[] letters = new int[26];
        int[][] lettersLeft = new int[26][26];
        int[] relations = new int[26];
        if (P.length() == 1) return 1;
        HashSet<Integer> usedChars = new HashSet<>(26);
        int count = initializeLetterCounts(P, Q, letters, lettersLeft, relations, usedChars);
        return count + findFinal(letters, lettersLeft, relations, usedChars, 0);
    }

    private int initializeLetterCounts(String P, String Q, int[] letters, int[][] lettersLeft, int[] relations, HashSet<Integer> usedChars) {
        boolean[] duplicate = new boolean[26];
        int count = 0;
        int len = P.length();
        for (int i = 0; i < len; i++) {
            char charP = P.charAt(i);
            char charQ = Q.charAt(i);
            int pIdx = charP - 'a';
            int qIdx = charQ - 'a';
            letters[pIdx]++;
            letters[qIdx]++;
            if (pIdx != qIdx) {
                if (lettersLeft[pIdx][qIdx]++ == 0) {
                    relations[pIdx]++;
                    relations[qIdx]++;
                }
                lettersLeft[qIdx][pIdx]++;
                usedChars.add(qIdx);
            }
            else {
                duplicate[pIdx] = true;
            }
            usedChars.add(pIdx);
        }
        for(int i = 0; i<26; i++) {
            if (duplicate[i]){
                removeChar(lettersLeft, letters, relations, usedChars, i);
                count++;
            }
        }
        return count;
    }

    private int findFinal(int[] letters, int[][] lettersLeft, int[] relations, HashSet<Integer> usedChars, int count) {
        if (usedChars.size() < 4) {
            return findFinalEnd(letters, lettersLeft, relations, usedChars, count);
        }

        int maxL = 0, index = 0, firstIndex = 0;
        int[] groups = new int[26];
        int maxGroups = 0;
        int maxRelations = 0;
        int minRelations = 20;

        for (int i : usedChars) {
            if (letters[i] > maxL) {
                minRelations = relations[i];
                maxL = letters[i];
                firstIndex = i;
            }
            else if(letters[i] == maxL && minRelations > relations[i]) {
                minRelations = relations[i];
            }
            if(relations[i] == 0) {
                removeChar(lettersLeft, letters, relations, usedChars, i);
                return findFinal(letters, lettersLeft, relations, usedChars, count+1);
            }
            if(relations[i] == 1) {
                for (int j : usedChars) {
                    if (letters[i] == lettersLeft[i][j]) {
                        groups[j]++;
//                        System.out.println("Group found for: " + (char) ('a' + j));
                        if (groups[j] > maxGroups || (groups[j] == maxGroups && relations[j] > maxRelations)) {
                            maxRelations = relations[j];
                            maxGroups = groups[j];
                            index = j;
                        }
                        break;
                    }
                }
            }
        }

        if (maxGroups < 1 || maxL == 1) {
            if(maxL == 1) {
                removeChar(lettersLeft, letters, relations, usedChars, firstIndex);
                return findFinalEnd(letters, lettersLeft, relations, usedChars, count+1);
            }
            int minResult = 20;
            for (int i : usedChars) {
                if (letters[i] == maxL && minRelations == relations[i]) {
                    int[] lettersCopy = Arrays.copyOf(letters, letters.length);
                    int[] relationsCopy = Arrays.copyOf(relations, relations.length);
                    int[][] lettersLeftCopy = new int[26][26];
                    for (int j = 0; j < 26; j++) {
                        lettersLeftCopy[j] = Arrays.copyOf(lettersLeft[j], lettersLeft[j].length);
                    }
                    HashSet<Integer> usedCharsCopy = new HashSet<>(usedChars);
                    removeChar(lettersLeftCopy, lettersCopy, relationsCopy, usedCharsCopy, i);
                    int result = findFinal(lettersCopy, lettersLeftCopy, relationsCopy, usedCharsCopy, count + 1);
                    minResult = Math.min(minResult, result);
                }
            }
            return minResult;
        }
        else {
            removeChar(lettersLeft, letters, relations, usedChars, index);
        }

        return findFinal(letters, lettersLeft, relations, usedChars, count + 1);
    }

    private int findFinalEnd(int[] letters, int[][] lettersLeft, int[] relations, HashSet<Integer> usedChars, int count) {
        if(usedChars.size() < 2) {
            return count + usedChars.size();
        }
        int maxL = 0, firstIndex = 0;
        for (int i : usedChars) {
            if (letters[i] > maxL) {
                maxL = letters[i];
                firstIndex = i;
            }
        }

        removeChar(lettersLeft, letters, relations, usedChars, firstIndex);
        return findFinalEnd(letters, lettersLeft, relations, usedChars, count + 1);
    }

    private void removeChar(int[][] lettersLeft, int[] letters, int[] relations, HashSet<Integer> usedChars, int index) {
        HashSet<Integer> temp = new HashSet<>(usedChars.size());
        usedChars.remove(index);
        letters[index] = 0;
        if(relations[index] == 0) return;
        relations[index] = 0;
        for(int i : usedChars) {
            if(lettersLeft[i][index] > 0) relations[i]--;
            letters[i]-=lettersLeft[i][index];
            lettersLeft[i][index] = 0;
            lettersLeft[index][i] = 0;
            if(letters[i] == 0) {
                temp.add(i);
            }
        }
        usedChars.removeAll(temp);
    }
}