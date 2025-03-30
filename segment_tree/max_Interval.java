package Compitetive_Programming.segment_tree;
import java.io.*;
// import java.util.*;
import java.util.Arrays;

class SegmentTree {
    int[] tree;

    public SegmentTree(int n) {
        tree = new int[4 * n];
    }

    public int build(int[] arr, int v, int left, int right) {
        if (left == right) {
            return tree[v] = arr[left];
        } else {
            int mid = (right - left) / 2 + left;
            int l = build(arr, v * 2 + 1, left, mid);
            int r = build(arr, v * 2 + 2, mid + 1, right);
            return tree[v] = Math.max(l, r);
        }
    }

    public int query(int v, int l, int r, int ql, int qr) {
        if (ql <= l && qr >= r)
            return tree[v];
        else if (ql > r || qr < l)
            return Integer.MIN_VALUE;
        else {
            int mid = (r - l) / 2 + l;
            int left = query(v * 2 + 1, l, mid, ql, qr);
            int right = query(v * 2 + 2, mid + 1, r, ql, qr);
            return Math.max(left, right);
        }
    }

    public int updateAtPoint(int[] arr, int v, int l, int r, int idx, int value){
        if(l== idx && r == idx){
            arr[idx] = value;
            return tree[v] = value;
        }else if(l > idx || r < idx){
            return tree[v];
        }else{
            int mid = (r-l)/2+l;
            int left = updateAtPoint(arr, v*2+1, l, mid, idx, value);
            int right = updateAtPoint(arr, v*2+2, mid+1, r, idx, value);
            return tree[v] = Math.max(left, right);
        }
    }
}

public class max_Interval {
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

            SegmentTree st = new SegmentTree(n);
            st.build(arr, 0, 0, n - 1);


            // int q = Integer.parseInt(br.readLine());
            // while (q-- > 0) {
            //     String[] queryInput = br.readLine().split(" ");
            //     int l = Integer.parseInt(queryInput[0]);
            //     int r = Integer.parseInt(queryInput[1]);
            //     bw.write(st.query(0, 0, n - 1, l, r) + "\n");
            // }

            int q = Integer.parseInt(br.readLine());
            while(q-- > 0){
                String[] queryInput = br.readLine().split(" ");
                int type = Integer.parseInt(queryInput[0]);
                int l = Integer.parseInt(queryInput[1]);
                int r = Integer.parseInt(queryInput[2]);
                if(type == 1){
                    bw.write(st.query(0, 0, n-1, l, r) + "\n");
                }else if(type == 0){
                    st.updateAtPoint(arr, 0, 0, n-1, l, r);
                }
            }
            bw.write(Arrays.toString(arr) + "\n");
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
