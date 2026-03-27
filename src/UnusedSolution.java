import java.util.*;

public class UnusedSolution {
    public int solution(String P, String Q) {
        List<String> S;
        S = permutation(P, Q);
        int n = P.length();
        for (String s : S) {
            if(countDistinctLetters(s) < n)
                n = countDistinctLetters(s);
        }
        return n;
    }

    private int countDistinctLetters(String s) {
        char[] c = s.toCharArray();
        SortedSet<Character> set = new TreeSet<Character>();
        for(char cc : c) {
            set.add(cc);
        }
        return set.size();
    }

    private List<String> permutation(String p, String q) {
        List<String> S = new ArrayList<>();
        generatePermutations(p, q, "", S);
        return S;
    }

    private void generatePermutations(String p, String q, String current, List<String> S) {
        if (current.length() == p.length()) {
            S.add(current);
            return;
        }
        generatePermutations(p, q, current + p.charAt(current.length()), S);
        generatePermutations(p, q, current + q.charAt(current.length()), S);
    }
}

//    private boolean[][] fillPossibilities(String P, String Q) {
//        boolean[][] result = new boolean[26][26];
//        for (int i = 0; i < P.length(); i++) {
//            result[P.charAt(i) - 'a'][Q.charAt(i) - 'a'] = true;
//        }
//        return result;
//    }
//
//    private int findFinal(String p, String q, int count) {
//        if(p.isEmpty()) {
//            return count;
//        }
//        count++;
//        int[] letters = new int[26];
//        int max = 0;
//        for(int i = 0; i < p.length(); i++) {
//            char pl = p.charAt(i);
//            char ql = q.charAt(i);
//            letters[pl - 'a']++;
//            letters[ql - 'a']++;
//            if (letters[pl - 'a'] > max) {
//                max = letters[pl - 'a'];
//            }
//            else if (letters[ql - 'a'] > max) {
//                max = letters[ql - 'a'];
//            }
//        }
//        List<Integer> l = new ArrayList<>();
//        for(int i = 0; i < 26; i++) {
//            if(letters[i] == max) {
//                if(max <= 2) {
//                    return findFinalEnd(p, q, count);
//                }
//                else {
//                    char n = (char) ('a' + i);
//                    StringBuilder resultp = new StringBuilder();
//                    StringBuilder resultq = new StringBuilder();
//                    for(int j = 0; j<p.length(); j++) {
//                        if(p.charAt(j) != n && q.charAt(j) != n) {
//                            resultp.append(p.charAt(j));
//                            resultq.append(q.charAt(j));
//                        }
//                    }
//                    String newP = resultp.toString();
//                    String newQ = resultq.toString();
//                    l.add(findFinal(newP, newQ, count));
//                }
//            }
//        }
//        return Collections.min(l);
//    }


//        char n = 0;
//        char m = 0;
//
//        for(int i = 0; i<26; i++) {
//            for(int j = 0; j<26; j++) {
//                if(f[i][j] && f[j][i]) {
//                    f[j][i] = false;
//                    if(!p.isEmpty()) {
//                        count++;
//                        n = (char) ('a' + i);
//                        m = (char) ('a' + j);
//
//                        StringBuilder resultp = new StringBuilder();
//                        StringBuilder resultq = new StringBuilder();
//                        for(int x = 0; x<p.length(); x++) {
//                            if(p.charAt(x) != n && q.charAt(x) != n && p.charAt(x) != m && q.charAt(x) != m) {
//                                resultp.append(p.charAt(x));
//                                resultq.append(q.charAt(x));
//                            }
//                        }
//                        p = resultp.toString();
//                        q = resultq.toString();
//                    }
//                }
//            }
//        }

//private String[] removeLetter(String p, String q, int index, int[] letters, int[][] lettersLeft, HashSet<Character> usedChars) {
//    char letterToRemove = (char) ('a' + index);
//    int len = p.length();
//    StringBuilder newP = new StringBuilder(len);
//    StringBuilder newQ = new StringBuilder(len);
//
//    for (int i = 0; i < len; i++) {
//        char charP = p.charAt(i);
//        char charQ = q.charAt(i);
//
//        if (charP != letterToRemove && charQ != letterToRemove) {
//            newP.append(charP);
//            newQ.append(charQ);
//        } else {
//            int pIdx = charP - 'a';
//            int qIdx = charQ - 'a';
//            letters[pIdx]--;
//            letters[qIdx]--;
//            if (pIdx != qIdx) {
//                lettersLeft[pIdx][qIdx]--;
//                lettersLeft[qIdx][pIdx]--;
//            }
//        }
//    }
//
//    usedChars.remove(letterToRemove);
//    return new String[]{newP.toString(), newQ.toString()};
//}