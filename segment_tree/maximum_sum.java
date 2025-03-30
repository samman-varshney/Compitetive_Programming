package Compitetive_Programming.segment_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

class SegmentTree3{
    int tree[];
    int max[][];
    public SegmentTree3(int n){
        tree = new int[4*n];
        max = new int[4*n][2];
    }
    public int[] populateMax(int[] left, int[] right){
        int[] res = new int[2];
        res[0] = Math.max(left[0], right[0]);
        res[1] = Math.max(Math.min(right[0], left[0]), Math.max(right[1], left[1]));
        return res;
    }
    public void build(int arr[], int v, int l, int r){
        if(l == r){
            tree[v] = arr[l];
            max[v][0] = arr[l];
            max[v][1] = 0;
        }else{
            int mid = (r-l)/2+l;
            build(arr, v*2+1, l, mid);
            build(arr, v*2+2, mid+1, r);
            tree[v] = Math.max(tree[v*2+1], tree[v*2+2]);
            max[v] = populateMax(max[v*2+1], max[v*2+2]);
        }
    }

    public int[] query(int v, int l, int r, int ql, int qr){
        if(ql <= l && qr >= r){
            return max[v];
        }else if(ql > r || qr < l){
            return new int[]{0, 0};
        }else{
            int mid = (r - l)/2+l;
            int[] left = query(v*2+1, l, mid, ql, qr);
            int[] right = query(v*2+2, mid+1, r, ql, qr);
            return populateMax(left, right);
        }
    }

    public int maximumSum(int l, int r){
        int[] ans = query(0, 0, tree.length/4-1, l, r);
        return ans[0]+ans[1];
    }
    public void updateAtPoint(int[] arr, int v, int l, int r, int idx, int value){
        if(l== idx && r == idx){
            arr[idx] = value;
            tree[v] = value;
            max[v][0] = value;
            max[v][1] = 0;
        }else if(l > idx || r < idx){
            return;
        }else{
            int mid = (r-l)/2+l;
            updateAtPoint(arr, v*2+1, l, mid, idx, value);
            updateAtPoint(arr, v*2+2, mid+1, r, idx, value);
            tree[v] = Math.max(tree[v*2+1], tree[v*2+2]);
            max[v] = populateMax(max[v*2+1], max[v*2+2]);
        }
    }
}
public class maximum_sum {
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

            SegmentTree3 st = new SegmentTree3(n);
            st.build(arr, 0, 0, n - 1);


            // int q = Integer.parseInt(br.readLine());
            // while (q-- > 0) {
            //     String[] queryInput = br.readLine().split(" ");
            //     int l = Integer.parseInt(queryInput[0]);
            //     int r = Integer.parseInt(queryInput[1]);
            //     bw.write(st.maximumSum(l, r) + "\n");
            // }

            int q = Integer.parseInt(br.readLine());
            while(q-- > 0){
                String[] queryInput = br.readLine().split(" ");
                int type = Integer.parseInt(queryInput[0]);
                int l = Integer.parseInt(queryInput[1]);
                int r = Integer.parseInt(queryInput[2]);
                if(type == 1){
                    bw.write(st.maximumSum(l, r) + "\n");
                }else{
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
