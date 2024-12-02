package io.pivotal.literx;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletionStageExample {
    public static void main(String[] args) {
        // 创建一个异步计算
        CompletionStage<String> stage = CompletableFuture.supplyAsync(() -> {
            // 模拟一些计算
            try {
                Thread.sleep(1000); // 模拟延迟
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello, World!";
        });

        // 注册回调处理结果
        stage.thenApply(result -> {
            System.out.println("Result: " + result);
            return result.toUpperCase();
        }).thenAccept(upperResult -> {
            System.out.println("Upper Case Result: " + upperResult);
        }).exceptionally(ex -> {
            System.err.println("Error: " + ex.getMessage());
            return null;
        });

        // 主线程继续执行其他任务
        System.out.println("Main thread is free to do other work...");
        try {
            stage.toCompletableFuture().cancel(true);
        }catch (Exception e){
            return;
        }
        System.out.println("main thread done");
        // 等待异步计算完成（可选）
        stage.toCompletableFuture().join();
    }
}
