package lib.Main_Boilerplate;

import java.io.*;

public class Array_Input {
    
    public static void main(String[] args) {

        // BufferReader Initialized
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            
            // Reading number of test cases
            int t = Integer.parseInt(br.readLine());


            while (t-- > 0) {

                // Reading raw string
                String[] tokens = br.readLine().split(" ");
                
                // Creating an array of input size
                int arr[] = new int[tokens.length];

                // Parsing array elements from string
                for (int i = 0; i < tokens.length; i++) {
                    arr[i] = Integer.parseInt(tokens[i]);

                }

                // Call the function
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
