public class Counter {

    static final int size = 10000000;
    static final int h = size / 2;
    float[] arr;


    public void setArr1() {
        arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long t = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время в однопоточном режиме: " + (System.currentTimeMillis() - t));
    }

    public void setArr2() {
        arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long t = System.currentTimeMillis();

        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Время в многопоточном режиме: " + (System.currentTimeMillis() - t));
    }
}
