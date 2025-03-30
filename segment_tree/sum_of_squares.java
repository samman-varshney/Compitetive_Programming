package Compitetive_Programming.segment_tree;

import java.io.*;

public class sum_of_squares {
    int[][] tree;
    int[] lazy;

    public sum_of_squares(int n) {
        tree = new int[4 * n][2];
        lazy = new int[4 * n];
    }

    public void build(int arr[], int v, int l, int r) {
        if (l == r) {
            tree[v][0] = arr[l];
            tree[v][1] = arr[l] * arr[l];
        } else {
            int mid = (l + r) / 2;
            build(arr, v * 2 + 1, l, mid);
            build(arr, v * 2 + 2, mid + 1, r);
            tree[v][0] = tree[v * 2 + 1][0] + tree[v * 2 + 2][0];
            tree[v][1] = tree[v * 2 + 1][1] + tree[v * 2 + 2][1];
        }
    }

    public void removeLaziness(int v, int l, int r) {
        if (lazy[v] != 0) {
            int len = (r - l + 1);
            tree[v][1] += 2 * lazy[v] * tree[v][0] + len * lazy[v] * lazy[v];
            tree[v][0] += lazy[v] * len;

            if (l != r) {
                lazy[v * 2 + 1] += lazy[v];
                lazy[v * 2 + 2] += lazy[v];
            }
            lazy[v] = 0;
        }
    }

    public int query(int v, int l, int r, int ql, int qr) {
        removeLaziness(v, l, r);
        if (ql <= l && qr >= r) {
            return tree[v][1];
        } else if (ql > r || qr < l) {
            return 0;
        } else {
            int mid = (l + r) / 2;
            int left = query(v * 2 + 1, l, mid, ql, qr);
            int right = query(v * 2 + 2, mid + 1, r, ql, qr);
            return left + right;
        }
    }

    public void updateRange(int v, int l, int r, int ql, int qr, int value) {
        removeLaziness(v, l, r);
        if (ql > r || qr < l) {
            return;
        }
        if (ql <= l && qr >= r) {
            lazy[v] += value;
            removeLaziness(v, l, r);
            return;
        }
        int mid = (l + r) / 2;
        updateRange(v * 2 + 1, l, mid, ql, qr, value);
        updateRange(v * 2 + 2, mid + 1, r, ql, qr, value);
        tree[v][0] = tree[v * 2 + 1][0] + tree[v * 2 + 2][0];
        tree[v][1] = tree[v * 2 + 1][1] + tree[v * 2 + 2][1];
    }

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

            sum_of_squares st = new sum_of_squares(n);
            st.build(arr, 0, 0, n - 1);

            int q = Integer.parseInt(br.readLine());
            while (q-- > 0) {
                String[] queryInput = br.readLine().split(" ");
                int type = Integer.parseInt(queryInput[0]);
                int l = Integer.parseInt(queryInput[1]);
                int r = Integer.parseInt(queryInput[2]);
                if (type == 0) {
                    bw.write(st.query(0, 0, n - 1, l, r) + "\n");
                } else {
                    int value = Integer.parseInt(queryInput[3]);
                    st.updateRange(0, 0, n - 1, l, r, value);
                }
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
