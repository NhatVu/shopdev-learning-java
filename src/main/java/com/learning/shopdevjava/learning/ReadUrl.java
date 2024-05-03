package com.learning.shopdevjava.learning;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadUrl {
    public static void main(String[] args) {
        try {
            URL u = new URL("https://jsonplaceholder.typicode.com/posts/1");
            InputStream in = u.openStream();
            int b;
//            while ((b = in.read()) != -1) {
//                System.out.write(b);
//            }
//            System.out.flush();


            while (true){
                b = in.read();
                if(b == -1){
                    break;
                }
                System.out.write(b);
            }
            System.out.flush();
//            StreamCopier.copy(in, System.out);
            in.close();
        } catch (MalformedURLException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
