package com.learning.shopdevjava.learning;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamCopier {
    public static void copy(InputStream in, OutputStream out){
        int BATCH_SIZE = 512;
        byte[] buffer = new byte[BATCH_SIZE];
        int readCounts = 0;
        while(true){
            try {
                readCounts = in.read(buffer);
                if(readCounts == -1){
                    break;
                }
                out.write(buffer, 0, readCounts);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
