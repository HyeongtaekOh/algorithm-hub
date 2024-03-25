import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    private static int n, m, lo, mid, hi, max;
    private static int[] request;

    public static void main(String[] args) throws IOException {

        n = Integer.parseInt(br.readLine());
        request = new int[n];
        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(br.readLine());
        int sum = 0;

        for (int i = 0; i < n; i++) {
            request[i] = Integer.parseInt(st.nextToken());
            sum += request[i];
        }


        lo = 0;
        hi = Math.min(m, sum) + 1;

        while (lo + 1 < hi) {
            mid = (lo + hi) / 2;
            sum = max = 0;
            for (int budget : request) {
                sum += Math.min(mid, budget);
                if (sum > m) {
                    break;
                }
            }
            
            if (sum > m) {
                hi = mid;
            } else {
                lo = mid;
            }
        }

        for (int budget: request) {
            max = Math.max(max, Math.min(lo, budget));
        }

        System.out.println(max);
    }
}
