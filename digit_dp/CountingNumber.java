import java.util.*;

public class CountingNumber {
    static long[][][][] dp = new long[20][2][2][11]; // Extra dimension for leading zeros

    public static long solve(String s, int idx, int tight, int leading, int prev) {
        if (idx == s.length()) return 1; // Valid number found

        if (dp[idx][tight][leading][prev + 1] != -1) return dp[idx][tight][leading][prev + 1];

        int limit = (tight == 1) ? (s.charAt(idx) - '0') : 9;
        long ans = 0;

        for (int i = 0; i <= limit; i++) {
            int newTight = (tight == 1 && i == limit) ? 1 : 0;
            int newLeading = (leading == 1 && i == 0) ? 1 : 0;

            if (leading == 1 || prev != i) { // Ensure adjacent digits are different
                ans += solve(s, idx + 1, newTight, newLeading, i);
            }
        }

        return dp[idx][tight][leading][prev + 1] = ans;
    }

    public static long countNumbers(long num) {
        if (num < 0) return 0;
        String numStr = String.valueOf(num);

        // Reset DP using Arrays.fill()
        for (long[][][] arr3D : dp) {
            for (long[][] arr2D : arr3D) {
                for (long[] arr : arr2D) {
                    Arrays.fill(arr, -1);
                }
            }
        }

        return solve(numStr, 0, 1, 1, -1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long l = sc.nextLong();
        long r = sc.nextLong();

        long ans1 = countNumbers(r);
        long ans2 = countNumbers(l - 1);
        
        System.out.println(ans1 - ans2);
        sc.close();
    }
}
