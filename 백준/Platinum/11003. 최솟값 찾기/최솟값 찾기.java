import java.io.IOException;

public class Main {

    static int N, L;
    static int[] a, d, tree;

    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder();

        N = read();
        L = read();

        a = new int[N];
        d = new int[N];
        tree = new int[N << 1];

        for (int i = 0; i < N; i++) {
            a[i] = read();
            tree[N + i] = a[i];
        }

        init();

        d[0] = a[0];
        sb.append(d[0]);

        for (int i = 1; i < L; i++) {
            d[i] = Math.min(d[i - 1], a[i]);
            sb.append(" ").append(d[i]);
        }

        for (int i = L; i < N; i++) {
            if (d[i - 1] == a[i - L]) {
                d[i] = query(i - L + 1, i + 1);
            } else {
                d[i] = Math.min(d[i - 1], a[i]);
            }
            sb.append(" ").append(d[i]);
        }

        System.out.println(sb);
    }

    private static void init() {
        for (int i = N - 1; i >= 0; i--) {
            tree[i] = Math.min(tree[i << 1], tree[i << 1 | 1]);
        }
    }

    private static int query(int left, int right) {

        int result = Integer.MAX_VALUE;
        left += N;
        right += N;

        while (left < right) {

            if ((left & 1) != 0) {
                result = Math.min(result, tree[left]);
                ++left;
            }

            if ((right & 1) != 0) {
                result = Math.min(result, tree[right - 1]);
                --right;
            }

            left /= 2;
            right /= 2;
        }

        return result;
    }

    private static int read() throws IOException {

        int c, n = System.in.read() & 15;

        boolean isNegative = n == 13;

        if (isNegative) {
            n = System.in.read() & 15;
        }

        while ((c = System.in.read()) > 32) {
            n = (n << 3) + (n << 1) + (c & 15);
        }

        return isNegative ? ~n + 1 : n;
    }
}
