package net.rumq.corejava;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorDemo {


    private final ExecutorService executor;
    private final long taskCount;
    private final long intCount;
    // Keep a list of tasks
    private final ArrayList<Callable<Long>> tasks = new ArrayList<Callable<Long>>();

    public ExecutorDemo(long taskCount, long intCount, ExecutorService executor) {
        if(intCount%taskCount !=0 ) throw new IllegalArgumentException("The intCount should be a multiple of taskCount");
        this.taskCount = taskCount;
        this.intCount = intCount;
        this.executor = executor;
    }

    // Create tasks
    private ArrayList<Callable<Long>> createTasks() {
        long part = intCount / taskCount;  // 30/3
        for (long i = 0; i < taskCount; i++) {  // 0, 1, 2
            long[] load = new long[Math.toIntExact(part)];   // long[10]
            for (long k = 1; k <= part; k++) {     // 1 to 10
                load[Math.toIntExact(k)-1] = (i * part) + k;  // 1..10, 11..20, 20..30
            }

            Callable<Long> task = () -> {
                long sum = 0;
                for (int j = 0; j < load.length; j++) {
                    sum += load[j];
                }
                return sum;
            };
            tasks.add(task);
        }
        return tasks;
    }

    private long executeTasks() throws InterruptedException, ExecutionException {

        List<Future<Long>> results = executor.invokeAll(tasks);
        long total = 0;
        for (Future<Long> result : results) {
            total += result.get();
        }
        return total;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var executor = Executors.newSingleThreadExecutor();
        var demo = new ExecutorDemo(3, 30, executor);
        var result = demo.run();
        System.out.println("Elapsed time : " + result.elapsedTime());
        System.out.println("Total :" + result.total());
        executor.shutdown();
    }

    public Result run() throws ExecutionException, InterruptedException {
        Instant startTime = Instant.now();
        var tasks = createTasks();
        long total = executeTasks();
        Instant endTime = Instant.now();
        long elapsedTime = endTime.toEpochMilli() - startTime.toEpochMilli();
        return new Result(elapsedTime, total);
    }

    public record Result(long elapsedTime, long total) {
    }

}
