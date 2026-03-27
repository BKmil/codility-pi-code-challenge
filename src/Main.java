import java.util.Random;

public class Main {
    public static String generateRandomString(int length) {
        String CHARACTERS = "abcdefghijklmnopqrst";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(index));
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        System.out.println(sol.solution("abc", "bcd")); // 2
        System.out.println(sol.solution("axxz", "yzwy")); // 2
        System.out.println(sol.solution("bacad", "abada")); // 1
        System.out.println(sol.solution("amz", "amz")); // 3
        System.out.println(sol.solution("w", "q")); // 1
        System.out.println(sol.solution("yjsleptskmxklerxklertnemtlqketyjsleptskmxklertyuoapgjsnekqjeqlfksotprnvjslekwoapdnemtlqketyjsleptskmemtlqkeyuoapgjsnekqjeqlfksotprnvjslekwoapdntyuoapgjsnekqjeqlfksotprnvjslekwoapdtyjsleptskmxklertyuoapgjsnekqjeqlfksotprnvjslekwoapdnemtlqket",
                "pfkeosngotmeosjekrlpjosngotmeosjekrlpjgnaozmwpqmednglropyspfkeosngotmeosjekrlpjgngnaozmwpqmengtspfkeosngotmeosjekrlpjgnaozmwpqmenngtjesntnigasdeotyigtjesntnigasdeotyidnglropysjesntnigasdeotyidnglropyspfkeaozmwpqmengtjesntnigasdeotyidnglropy")); // 16
        System.out.println(sol.solution("cafg", "xdaf")); // 3
        System.out.println(sol.solution("aabcbaddd", "dddafbbaa")); // 2
        System.out.println(sol.solution("dddafbbaa", "aabcbaddd")); // 2
        System.out.println(sol.solution("gspert", "alvuim")); // 6
        System.out.println(sol.solution("qwertyui", "asdfghjk")); // n
        System.out.println(sol.solution("qwertyuiopasdfghjklz", "qwertyuiopasdfghjklz")); // 20


        System.out.println(sol.solution("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij",
                "klmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrstklmnopqrst"));

        String P;
        String Q;


        int length = 200;

        for(int i = 0; i<10; i++) {
            P = generateRandomString(length);
            Q = generateRandomString(length);
            System.out.println(sol.solution(P, Q));
        }
        System.out.println("End");
    }
}