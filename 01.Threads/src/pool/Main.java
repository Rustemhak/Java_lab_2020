package pool;


public class Main {
    public static void main(String[] args) {
        /*
        Сделать проверки:

        1) Одна задача - один поток
        2) Две задачи - один поток (поочередно выполнить каждую)
        3) Три задачи - три потока (каждый поток выполняет свою задачу)
        4) Четыре задачи - три потока (три потока выполняют три задачи, четвертая задача выполняется первым свободным)
         */
        ThreadPool threadPool = new ThreadPool(3);
        Runnable task1 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(i+ " A");
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(i + " B");
            }
        };
        Runnable task3 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(i+ " C");
            }
        };
        Runnable task4 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(i+ " D");
            }
        };
        threadPool.submit(task1);
        threadPool.submit(task2);
        threadPool.submit(task3);
        threadPool.submit(task4);
    }
}
