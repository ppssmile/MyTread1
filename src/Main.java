import java.util.Arrays;

public class Main {


    public static void main(String[] args) {

        final int SIZE = 10000000;
        final int THREAD = 4;
        final int THREAD_SIZE = SIZE / THREAD;

        float[] arr;
        arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        float[][] subArray = new float[THREAD][THREAD_SIZE];
        Thread thread[] = new Thread[THREAD];
        for (int i = 0; i < THREAD; i++) {
            System.arraycopy(arr, (THREAD_SIZE * i), subArray[i], 0, THREAD_SIZE);
            final int j = i;
            thread[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0, x = j * THREAD_SIZE; k < THREAD_SIZE; k++, x++) {
                        subArray[j][k] = (float) (subArray[j][k] * Math.sin(0.2f + x / 5) * Math.cos(0.4f + x / 2));
                    }
                }
            });
            thread[i].start();
        }
        try {
            for (int i = 0; i < THREAD; i++) {
                thread[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < THREAD; i++) {
            System.arraycopy(subArray[i], 0, arr, THREAD_SIZE * i, THREAD_SIZE);
        }
        System.out.println("Time divided: " + (System.currentTimeMillis() - a));
    }
}
