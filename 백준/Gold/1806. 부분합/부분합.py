N, S = map(int, input().split())
nums = list(map(int, input().split()))
left, right = 0, 0
sum = 0
minLength = N + 1

while True:
    if sum < S:
        if right > N - 1:
            break
        sum += nums[right]
        right += 1
    else:
        minLength = min(right - left, minLength)
        if minLength == 1:
            break
        sum -= nums[left]
        left += 1

print(0 if minLength > N else minLength)