// package Compitetive_Programming.digit_dp;

import java.util.*;

public class LuciferNumber {
    static int[][][][] dp;
    static boolean[] prime;

  
    static void precomputePrimes() {
        prime = new boolean[90];
        Arrays.fill(prime, true);
        prime[0] = prime[1] = false; // 0 and 1 are NOT prime
        for (int i = 2; i * i <= 89; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= 89; j += i) {
                    prime[j] = false;
                }
            }
        }
    }

    public static boolean isPrime(int sae, int sao, int len) {
        int diff = 0;
        if(len%2 == 0){
            diff = sae - sao;
        }else{
            diff = sao - sae;
        }
        return diff<0 ? false: prime[diff]; 
    }
    
    public static int solve(String r, int idx, int tight, int sae, int sao, int isOdd) {
        if (idx == r.length()) 
            return isPrime(sae, sao, r.length()) ? 1 : 0; 

        if (dp[idx][tight][sae][sao] != -1)
            return dp[idx][tight][sae][sao];

        int limit = (tight == 1) ? (r.charAt(idx) - '0') : 9;
        int ans = 0;

        for (int i = 0; i <= limit; i++) {
            int newTight = (tight == 1 && i == limit) ? 1 : 0;
            int newSae = (isOdd == 0) ? sae + i : sae;
            int newSao = (isOdd == 1) ? sao + i : sao;
            ans += solve(r, idx + 1, newTight, newSae, newSao, isOdd ^ 1);
        }

        return dp[idx][tight][sae][sao] = ans;
    }

    
    public static int countNumbers(int num) {
        if (num < 0) return 0;
        String numStr = String.valueOf(num);
        dp = new int[numStr.length()][2][90][90]; 

        for (int i = 0; i < numStr.length(); i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 90; k++)
                    for (int m = 0; m < 90; m++)
                        dp[i][j][k][m] = -1;

        return solve(numStr, 0, 1, 0, 0, 0);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        precomputePrimes(); 
        int tcase = sc.nextInt();

        while (tcase-- > 0) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            System.out.println(countNumbers(r) - countNumbers(l - 1));
        }

        sc.close();
    }
}
