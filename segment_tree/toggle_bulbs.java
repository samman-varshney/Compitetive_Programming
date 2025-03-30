package Compitetive_Programming.segment_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.util.Arrays;


public class toggle_bulbs {
    int[] tree;
    int[] lazy;
    public toggle_bulbs(int n) {
        tree = new int[4 * n];
        lazy = new int[4 * n];
    }
    public void propagate(int v, int l, int r) {
        if (lazy[v] != 0) {
            int len = (r - l + 1);
            tree[v] = len - tree[v];  // Toggle ON bulbs
            
            if (l != r) {  // Push laziness to children
                lazy[v * 2 + 1] ^= 1;
                lazy[v * 2 + 2] ^= 1;
            }
            lazy[v] = 0;  // Clear laziness
        }
    }
    
    public void updateRange(int v, int l, int r, int ql, int qr){
        // propagate(v, l, r);
        if(ql <= l && qr >= r){
            lazy[v] ^= 1;
            propagate(v, l, r);
        }else{
            propagate(v, l, r);
            int mid = (l+r)/2;
            if(ql <= mid){
                updateRange(v*2+1, l, mid, ql, qr);
            }
            if(qr > mid){
                updateRange(v*2+2, mid+1, r, ql, qr);
            }
            tree[v] = tree[v*2+1] + tree[v*2+2];
        }
    }
    public int query(int v, int l, int r, int ql, int qr){
        propagate(v, l, r);
        if(ql <= l && qr >= r){
            return tree[v];
        }else if(ql > r || qr < l){
            return 0;
        }else{
            int mid = (l+r)/2;
            int left = query(v*2+1, l, mid, ql, qr);
            int right = query(v*2+2, mid+1, r, ql, qr);
            return left + right;
        }
    }
    public static void main(String[] args){
        try {
            BufferedReader br = new BufferedReader(new FileReader("./Compitetive_Programming/segment_tree/input.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("./Compitetive_Programming/segment_tree/output.txt"));

            String[] queryInput = br.readLine().split(" ");
            int bulbs = Integer.parseInt(queryInput[0]);
            int queries = Integer.parseInt(queryInput[1]);
        

            toggle_bulbs st = new toggle_bulbs(bulbs);
            

            
            while (queries -- > 0) {
                queryInput = br.readLine().split(" ");
                int type = Integer.parseInt(queryInput[0]);
                int l = Integer.parseInt(queryInput[1]);
                int r = Integer.parseInt(queryInput[2]);
                if(type == 1){
                    st.updateRange(0, 0, bulbs-1, l-1, r-1);
                }else{
                    bw.write(st.query(0, 0, bulbs-1, l-1, r-1) + "\n");
                }
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
