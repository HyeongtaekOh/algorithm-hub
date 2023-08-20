import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int n;
    static int[] nums, lis, reversedLis;

    public static void main(String[] args) throws IOException {

        n = Integer.parseInt(br.readLine());
        nums = new int[n];
        lis = new int[n];
        reversedLis = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n; ++i) {
            getLis(i);
        }

        for (int i = n-1; i >= 0; --i) {
            getReversedLis(i);
        }

        int answer = 0;

        for (int i = 0; i < n; i++) {
            answer = Math.max(answer, lis[i] + reversedLis[i] - 1);
        }

        System.out.println(answer);
    }

    static int getLis(int index) {

        if (lis[index] > 0) {
            return lis[index];
        }

        lis[index] = 1;

        for (int i = index - 1; i >= 0; --i) {
            if (nums[index] > nums[i]) {
                lis[index] = Math.max(lis[index], 1 + getLis(i));
            }
        }

        return lis[index];
    }
    static int getReversedLis(int index) {

        if (reversedLis[index] > 0) {
            return reversedLis[index];
        }

        reversedLis[index] = 1;

        for (int i = index + 1; i < n; ++i) {
            if (nums[index] > nums[i]) {
                reversedLis[index] = Math.max(reversedLis[index], 1 + getReversedLis(i));
            }
        }

        return reversedLis[index];
    }
}
