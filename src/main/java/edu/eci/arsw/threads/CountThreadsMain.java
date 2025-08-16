
package edu.eci.arsw.threads;

/**
 *
 * @author daviespr1406
 */
public class CountThreadsMain {

    public static void main(String[] args) {
        CountThread t1 = new CountThread(0, 5);
        CountThread t2 = new CountThread(6, 10);
        CountThread t3 = new CountThread(11, 15);

        t1.start();
        t2.start();
        t3.start();
    }
    
}
