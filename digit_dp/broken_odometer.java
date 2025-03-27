// package Compitetive_Programming.digit_dp;

public class broken_odometer {
    static int dp[][][];

    public static  int solve(String s, int idx, int tight, int flag){
        if(idx == s.length())return flag;
        if(dp[idx][tight][flag] != -1)return dp[idx][tight][flag];
        int limit = tight == 1?s.charAt(flag) - '0':9;
        int ans = 0;
        for(int i=0; i<=limit; i++){
            ans += solve(s, idx+1, tight == 1 && i == limit?1:0, i == 3?1:flag); 
        }   
        return dp[idx][tight][flag] = ans;
    }
    public static void main(String[] args) {
        dp = new int[20][2][2];
        for(int i=0; i<20; i++){
            for(int j=0; j<2; j++){
                for(int k=0; k<2; k++){
                    dp[i][j][k] = -1;
                }
            }
        }
        String s = "76";
        System.out.println(76 - solve(s, 0, 1, 0));
    }
}
