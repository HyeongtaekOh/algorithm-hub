import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static long prime = (long) Math.pow(10, 9) + 7;

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static long n, m;

	static long moduloAdd(long a, long b) {
		return ((a + b) % prime);
	}

	static long moduloMul(long a, long b) {
		return ((a % prime) * (b % prime)) % prime;
	}

	static long moduloPow(long exp) {

		if (exp == 0) {
			return 1;
		}

		if (exp % 2 != 0) {
			return (moduloPow(exp - 1) * 2) % prime;
		}

		long square = moduloPow(exp >> 1);
		return square * square % prime;
	}

	static long hanoiTower(long N) {
		return moduloPow(N) - 1;
	}

	static long difficultHanoiTower(long N, long M) {
		return moduloMul(moduloPow(N + 1) - 2, M) - 1;
	}

	public static void main(String[] args) throws IOException {

		st = new StringTokenizer(br.readLine());

		n = Long.parseLong(st.nextToken());
		m = Long.parseLong(st.nextToken());

		if (m == 1) {
			System.out.println(hanoiTower(n));
		} else {
			System.out.println(difficultHanoiTower(n, m));
		}
	}
}
