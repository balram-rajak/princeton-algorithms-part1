
void print(vector<auto>& arr){

    for(auto ele : arr) cout<< ele << " ";
    cout<< endl;
}

int main(){

    int T;

    cin >> T;
    while(T--){

        int sz, k ;

        cin >> sz >> k;

        vector<int> arr;

        int temp;

        while(sz--){
           cin >> temp;
           arr.push_back(temp);
        }

        cout << maxFrequency(arr,k) << endl;

    }
}