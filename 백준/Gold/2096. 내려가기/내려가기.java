import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        int N = Integer.parseInt(br.readLine());

        int[] prevMax = new int[3];
        int[] prevMin = new int[3];
        int[] max = new int[3];
        int[] min = new int[3];

        st = new StringTokenizer(br.readLine());

        max[0] = min[0] = prevMax[0] = prevMin[0] = Integer.parseInt(st.nextToken());
        max[1] = min[1] = prevMax[1] = prevMin[1] = Integer.parseInt(st.nextToken());
        max[2] = min[2] = prevMax[2] = prevMin[2] = Integer.parseInt(st.nextToken());

        for (int i = 1; i < N; i++) {

            st = new StringTokenizer(br.readLine());

            int left = Integer.parseInt(st.nextToken());
            int mid = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            max[0] = Math.max(prevMax[0], prevMax[1]) + left;
            max[1] = Math.max(prevMax[0], Math.max(prevMax[1], prevMax[2])) + mid;
            max[2] = Math.max(prevMax[1], prevMax[2]) + right;

            min[0] = Math.min(prevMin[0], prevMin[1]) + left;
            min[1] = Math.min(prevMin[0], Math.min(prevMin[1], prevMin[2])) + mid;
            min[2] = Math.min(prevMin[1], prevMin[2]) + right;

            prevMax[0] = max[0];
            prevMax[1] = max[1];
            prevMax[2] = max[2];
            prevMin[0] = min[0];
            prevMin[1] = min[1];
            prevMin[2] = min[2];
        }

        sb.append(Math.max(max[0], Math.max(max[1], max[2])))
                .append(" ").append(Math.min(min[0], Math.min(min[1], min[2])));
        System.out.println(sb);
    }
}
