import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	static Stack<Integer> stack = new Stack<>();
	static Stack<Integer> questionStack = new Stack<>();

	public static void main(String[] args) throws IOException {

		int T = Integer.parseInt(br.readLine());

		testcase: for (int i = 0; i < T; i++) {

			String line = br.readLine();
			stack.clear();
			questionStack.clear();

			for (int j = 0, len = line.length(); j < len; j++) {

				char c = line.charAt(j);

				if (c == '(') {
					stack.push(j);
				} else if (c == '?') {
					questionStack.push(j);
				} else if (c == '*') {
					stack.push(-1);
					questionStack.clear();
				} else {
					if (stack.empty()) {
						if (questionStack.size() == 0) {
							// System.out.println("tc = " + (i + 1) + " Here!");
							sb.append("NO\n");
							continue testcase;
						} else {
							questionStack.pop();
						}
					} else if (stack.peek() >= 0) {
						stack.pop();
					}
				}
			}

			while (!stack.empty() && stack.peek() >= 0) {

				if (questionStack.empty()) {
					break;
				}

				if (questionStack.peek() < stack.peek()) {
					break;
				}

				questionStack.pop();
				stack.pop();
			}

			if ((stack.empty() && questionStack.size() % 2 == 0) || (!stack.empty() && stack.peek() == -1)) {
				sb.append("YES\n");
			} else {
				sb.append("NO\n");
			}
		}

		System.out.print(sb);
	}

}