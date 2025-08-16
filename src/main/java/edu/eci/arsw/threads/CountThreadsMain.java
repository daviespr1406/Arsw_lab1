
package edu.eci.arsw.threads;

/**
 *
 * @author daviespr1406
 */
public class CountThreadsMain {

    public static void main(String[] args) {
        CountThread t1 = new CountThread(0, 99);
        CountThread t2 = new CountThread(100, 199);
        CountThread t3 = new CountThread(200, 299);

        t1.start();
        t2.start();
        t3.start();
    }
    
}
