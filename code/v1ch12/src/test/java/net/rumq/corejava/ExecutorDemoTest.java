package net.rumq.corejava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemoTest {
    @Test
    void testSingleThreadSimple() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorDemo demo1 = new ExecutorDemo(2, 10, executor);
        ExecutorDemo.Result result1 = demo1.run();
        Assertions.assertEquals(55, result1.total());

        ExecutorDemo demo2 = new ExecutorDemo(3, 30, executor);
        ExecutorDemo.Result result2 = demo2.run();
        Assertions.assertEquals(465, result2.total());

    }
    @Test
    void testSingleThreadSameCountDifferentNumberOfTasks() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorDemo demo1 = new ExecutorDemo(1, 10, executor);
        ExecutorDemo.Result result1 = demo1.run();
        Assertions.assertEquals(55, result1.total());

        ExecutorDemo demo2 = new ExecutorDemo(2, 10, executor);
        ExecutorDemo.Result result2 = demo2.run();
        Assertions.assertEquals(55, result2.total());
    }
    @Test
    void testLargeNumberDifferentExecutors() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorDemo demo1 = new ExecutorDemo(4, Long.parseLong("1000000"), executor);
        ExecutorDemo.Result result1 = demo1.run();
        Assertions.assertEquals(Long.parseLong("500000500000"), result1.total());
        System.out.println(result1.elapsedTime());

        ExecutorService executor2 = Executors.newCachedThreadPool();
        ExecutorDemo demo2 = new ExecutorDemo(10, Long.parseLong("1000000"), executor2);
        ExecutorDemo.Result result2 = demo2.run();
        Assertions.assertEquals(Long.parseLong("500000500000"), result2.total());
        System.out.println(result2.elapsedTime());


    }
}
