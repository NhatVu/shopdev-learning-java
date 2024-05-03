package com.learning.shopdevjava.learning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadFile {
    public static void main(String[] args) {
        String fileName = "";

        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream("data/input-text.txt");
            int BATCH_SIZE = 4;
            byte[] data      = new byte[BATCH_SIZE];

            int    bytesRead = 0;

            while(true) {
                bytesRead = inputstream.read(data);
                if(bytesRead == -1){
                    break;
                }
                doSomethingWithData(data, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(inputstream != null){
                try {
                    inputstream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    private static void doSomethingWithData(byte[] data, int bytesRead){

        String str = new String(data, 0, bytesRead);
        System.out.print(str);
    }
}
