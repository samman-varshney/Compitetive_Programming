package Compitetive_Programming.segment_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.util.Arrays;

class SegmentTree2{
    int[] tree;
    public SegmentTree2(int n){
        tree = new int[4*n];
    }
    public void build(int arr[], int v, int l, int r){
        if(l == r)
            tree[v] = arr[l];
        else{
            int mid = (r - l)/2+l;
            build(arr, v*2+1, l, mid);
            build(arr, v*2+2, mid+1, r);
            tree[v] = tree[v*2+1]+tree[v*2+2];
        }
    }
    public int query(int v, int l, int r, int ql, int qr){
        if(ql <= l && qr >= r){
            return tree[v];
        }else if(ql > r || qr < l){
            return 0;
        }else{
            int mid = (r - l)/2 + l;
            int left = query(v*2+1, l, mid, ql, qr);
            int right = query(v*2+2, mid+1, r, ql, qr);
            return left + right;
        }
    }
}
public class sum_intervals {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./Compitetive_Programming/segment_tree/input.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("./Compitetive_Programming/segment_tree/output.txt"));

            int n = Integer.parseInt(br.readLine());
            int[] arr = new int[n];
            String[] inputArr = br.readLine().split(" ");
            
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(inputArr[i]);
            }

            SegmentTree2 st = new SegmentTree2(n);
            st.build(arr, 0, 0, n - 1);


            int q = Integer.parseInt(br.readLine());
            while (q-- > 0) {
                String[] queryInput = br.readLine().split(" ");
                int l = Integer.parseInt(queryInput[0]);
                int r = Integer.parseInt(queryInput[1]);
                bw.write(st.query(0, 0, n - 1, l, r) + "\n");
            }

            // int q = Integer.parseInt(br.readLine());
            // while(q-- > 0){
            //     String[] queryInput = br.readLine().split(" ");
            //     int type = Integer.parseInt(queryInput[0]);
            //     int l = Integer.parseInt(queryInput[1]);
            //     int r = Integer.parseInt(queryInput[2]);
            //     if(type == 1){
            //         bw.write(st.query(0, 0, n-1, l, r) + "\n");
            //     }else if(type == 0){
            //         st.updateAtPoint(arr, 0, 0, n-1, l, r);
            //     }
            // }
            // bw.write(Arrays.toString(arr) + "\n");
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
