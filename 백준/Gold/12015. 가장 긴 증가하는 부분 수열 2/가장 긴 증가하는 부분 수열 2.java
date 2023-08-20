import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int n, index = 1;
    static int[] nums, lis;

    public static void main(String[] args) throws IOException {

        n = Integer.parseInt(br.readLine());
        nums = new int[n];
        lis = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        lis[0] = nums[0];

        for (int i = 1; i < n; i++) {

            if (lis[index - 1] < nums[i]) {
                lis[index++] = nums[i];
            } else {
                int insert = binarySearch(lis, 0, index, nums[i]);
                if (insert < 0) {
                    lis[-insert - 1] = nums[i];
                } else {
                    lis[insert] = nums[i];
                }
            }
        }

        System.out.println(index);
    }

    static int binarySearch(int[] nums, int from, int to, int key) {

        int low = from, hi = to;

        while (low < hi) {

            int mid = low + (hi - low) / 2;

            if (nums[mid] == key) {
                return mid;
            }

            if (nums[mid] > key) {
                hi = mid;
            } else {
                low = mid + 1;
            }
        }

        return -low - 1;
    }
}
