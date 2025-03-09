import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {


        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int answer = n + 1;

        st = new StringTokenizer(br.readLine());
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            nums[i] = num;
        }
        
        int left = 0, right = 0;
        int sum = nums[left];

        for (; left < n; left++) {
            right = Math.max(left, right);
            while (right < n-1 && sum < s) {
                right++;
                sum += nums[right];
            }
            if (sum >= s) {
                answer = Math.min(answer, right - left + 1);
            }
            sum -= nums[left];
        }

        System.out.println(answer > n ? 0 : answer);
    }
}
