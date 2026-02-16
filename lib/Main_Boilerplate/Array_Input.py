# Main code
testCases = int(input(""));
assert(testCases > 0);

while(testCases >0):

    size = int(input(""));
    arr = [];

    arr = input("").split();
    
    for x in range(size):
        arr[x] = int(arr[x]);
    
    freq = frequencyCount(arr);

    for x in arr:
        print(f"{x} : {freq[x-1]}");

    testCases -= 1;