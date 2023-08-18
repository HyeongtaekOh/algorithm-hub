import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int n;
    static long[] histogram;

    public static void main(String[] args) throws IOException {

        n = Integer.parseInt(br.readLine());
        histogram = new long[n];

        for (int i = 0; i < n; i++) {
            histogram[i] = Integer.parseInt(br.readLine());
        }

        System.out.println(largestRectangleArea(0, n));
    }

    static long largestRectangleArea(int start, int end) {
        if (start == end - 1) {
            return histogram[start];
        }

        int mid = start + (end - start) / 2;
        long leftArea = largestRectangleArea(start, mid);
        long rightArea = largestRectangleArea(mid, end);
        long maxArea = Math.max(leftArea, rightArea);

        int left = mid, right = mid;
        long width = 1, height = histogram[mid];
        long midArea = height;

        while (width < end - start) {

            ++width;

            if (left <= start) {
                ++right;
                height = Math.min(height, histogram[right]);
            } else if (right >= end - 1) {
                --left;
                height = Math.min(height, histogram[left]);
            } else {
                if (histogram[left - 1] > histogram[right + 1]) {
                    --left;
                    height = Math.min(height, histogram[left]);
                } else {
                    ++right;
                    height = Math.min(height, histogram[right]);
                }
            }

            midArea = Math.max(midArea, width * height);
        }

        return Math.max(midArea, maxArea);
    }
}
