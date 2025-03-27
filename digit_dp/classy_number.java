// package Compitetive_Programming.digit_dp;

public class classy_number{
    static int dp[][][] = new int[20][2][4];   
    public static int solve(String s, int idx, int tight, int cnt){
        if(cnt > 3)return 0;
        if(idx == s.length())
            return 1;
        
        if(dp[idx][tight][cnt] != -1)return dp[idx][tight][cnt];

       
        int limit = tight == 1?s.charAt(idx) - '0':9;
        int ans = 0;
        for(int i=0; i<=limit; i++){
            ans += solve(s, idx+1, tight == 1 && i == limit?1:0, i != 0?cnt+1:cnt); 
        }
        return dp[idx][tight][cnt] = ans;
    }
    public static void main(String[] args){
        dp = new int[20][2][4];
        for(int i=0; i<20; i++){
            for(int j=0; j<2; j++){
                for(int k=0; k<4; k++){
                    dp[i][j][k] = -1;
                }
            }
        }
        String s = "100000";

        System.out.println(solve(s, 0, 1, 0));  

    }
}