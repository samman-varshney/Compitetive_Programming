package Compitetive_Programming.segment_tree_2d;

import java.io.*;

public class SegmentTree2D {
    int n, m;
    int[][] tree;
    public SegmentTree2D(int n, int m) {
        this.n = n;
        this.m = m;
        tree = new int[4 * n][4 * m];
    }
    public void buildx(int[][] arr, int nodex, int leftx, int rightx) {
        if (leftx == rightx) {
            buildy(arr, nodex, leftx, rightx,  0, 0, m - 1);
        } else {
            int mid = (rightx - leftx) / 2 + leftx;
            buildx(arr, nodex * 2 + 1, leftx, mid);
            buildx(arr, nodex * 2 + 2, mid + 1, rightx);
            buildy(arr, nodex, leftx, rightx, 0, 0, m-1);
        }
    }
    public void buildy(int[][] arr, int nodex, int leftx, int rightx, int nodey, int lefty, int righty) {
        if (lefty == righty) {
            if(leftx == rightx){
                tree[nodex][nodey] = arr[leftx][lefty];
            }else{
                tree[nodex][nodey] = tree[nodex * 2 + 1][nodey] + tree[nodex * 2 + 2][nodey];
            }
        } else {
            int mid = (righty - lefty) / 2 + lefty;
            buildy(arr, nodex, leftx, rightx, nodey * 2 + 1, lefty, mid);
            buildy(arr, nodex, leftx, rightx, nodey * 2 + 2, mid + 1, righty);
            tree[nodex][nodey] = tree[nodex][nodey * 2 + 1] + tree[nodex][nodey * 2 + 2];
        }
    }
    public void updateAtPointX(int[][] arr, int nodex, int leftx, int rightx, int x, int y, int val){
        if( x > rightx || x < leftx){
           return;
        }else if(leftx == rightx){
            updateAtPointY(arr, nodex, leftx, rightx, 0, 0, m-1, x, y, val);
        }else{
            int mid = (rightx - leftx) / 2 + leftx;
            updateAtPointX(arr, nodex * 2 + 1, leftx, mid, x, y, val);
            updateAtPointX(arr, nodex * 2 + 2, mid + 1, rightx, x, y, val);
            updateAtPointY(arr, nodex, leftx, rightx, 0, 0, m-1, x, y, val);


        }
    }
    public void updateAtPointY(int[][] arr, int nodex, int leftx, int rightx, int nodey, int lefty, int righty, int x, int y, int val){
    
        if(y <lefty || y > righty){
            return;
        }if(lefty == righty){
            if(leftx == rightx){
                arr[x][y] = val;
                tree[nodex][nodey] = val;
            }else{
                tree[nodex][nodey] = tree[nodex * 2 + 1][nodey] + tree[nodex * 2 + 2][nodey];
            }
        }else{
            int mid = (righty - lefty)/2 + lefty;
            updateAtPointY(arr, nodex, leftx, rightx, nodey*2+1, lefty, mid, x, y, val);
            updateAtPointY(arr, nodex, leftx, rightx, nodey*2+2, mid+1, righty, x, y, val);
            tree[nodex][nodey] = tree[nodex][nodey*2+1] + tree[nodex][nodey*2+2];
        }
    }
    public int queryY(int nodex, int nodey, int lefty, int righty, int y1, int y2){
        if(y1 <= lefty && y2 >= righty){
            return tree[nodex][nodey];
        }else if(y1 > righty || y2 < lefty){
            return 0;
        }else{
            int mid = (righty - lefty) / 2 + lefty;
            int left = queryY(nodex, nodey * 2 + 1, lefty, mid, y1, y2);
            int right = queryY(nodex, nodey * 2 + 2, mid + 1, righty,y1, y2);
            return left + right;
        }
    }
    public int queryX(int nodex, int leftx, int rightx, int x1, int x2, int y1, int y2){
        if(x1 <= leftx && x2 >= rightx){
            return queryY(nodex, 0, 0, m-1, y1, y2);
        }else if(x1 > rightx || x2 < leftx){
            return 0;
        }else{
            int mid = (rightx - leftx)/2 + leftx;
            int left = queryX(nodex * 2 + 1, leftx, mid, x1, x2, y1, y2);
            int right = queryX(nodex * 2 + 2, mid + 1, rightx, x1, x2, y1, y2);
            return left + right;
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./Compitetive_Programming/segment_tree_2D/IO_files/input.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("./Compitetive_Programming/segment_tree_2D/IO_files/output.txt"));

            String[] queryString = br.readLine().split(" ");
            int n = Integer.parseInt(queryString[0]), m = Integer.parseInt(queryString[1]);
            int[][] arr = new int[n][m];
            
            
            for (int i = 0; i < n; i++) {
                String[] inputArr = br.readLine().split(" ");
                for (int j = 0; j < m; j++) {
                    arr[i][j] = Integer.parseInt(inputArr[j]);
                }
            }

            SegmentTree2D st = new SegmentTree2D(n, m);
            st.buildx(arr, 0, 0, n - 1);


            // int q = Integer.parseInt(br.readLine());
            // while (q-- > 0) {
            //     String[] queryInput = br.readLine().split(" ");
            //     int l = Integer.parseInt(queryInput[0]);
            //     int r = Integer.parseInt(queryInput[1]);
            //     bw.write(st.query(0, 0, n - 1, l, r) + "\n");
            // }

            int queries = Integer.parseInt(br.readLine());
            while(queries-- > 0){
                String[] queryInput = br.readLine().split(" ");
                if(queryInput[0].equals("u")){
                    int x = Integer.parseInt(queryInput[1]);
                    int y = Integer.parseInt(queryInput[2]);
                    int val = Integer.parseInt(queryInput[3]);
                    st.updateAtPointX(arr, 0, 0, n-1, x, y, val);
                }else{
                    int x1 = Integer.parseInt(queryInput[1]);
                    int x2 = Integer.parseInt(queryInput[2]);
                    int y1 = Integer.parseInt(queryInput[3]);
                    int y2 = Integer.parseInt(queryInput[4]);
                    bw.write(st.queryX(0, 0, n-1, x1, x2, y1, y2) + "\n");
                }
            }   
            
            
            br.close();
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
