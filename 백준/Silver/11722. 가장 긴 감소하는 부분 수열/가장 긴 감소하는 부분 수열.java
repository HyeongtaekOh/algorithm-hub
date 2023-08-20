import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int n;
    static int[] nums, dp;

    public static void main(String[] args) throws IOException {

        n = Integer.parseInt(br.readLine());
        nums = new int[n];
        dp = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            answer = Math.max(answer, lds(i));
        }

        System.out.println(answer);
    }

    static int lds(int index) {

        if (dp[index] > 0) {
            return dp[index];
        }

        dp[index] = 1;

        for (int i = index - 1; i >= 0; --i) {
            if (nums[index] < nums[i]) {
                dp[index] = Math.max(dp[index], 1 + lds(i));
            }
        }

        return dp[index];
    }
}
