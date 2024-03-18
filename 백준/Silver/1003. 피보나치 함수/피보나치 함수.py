n = int(input())
ans_zero = [0] * 41
ans_one = [0] * 41
ans_zero[0] = 1
ans_one[1] = 1

for i in range(2, 41):
    ans_zero[i] = ans_zero[i-1] + ans_zero[i-2]
    ans_one[i] = ans_one[i-1] + ans_one[i-2]

for i in range(n):
    t = int(input())
    print(f'{ans_zero[t]} {ans_one[t]}')
