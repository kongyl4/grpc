import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.kyl.grpc.GreeterGrpc;
import com.kyl.grpc.HelloWorldProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by kongyl4 on 2016/11/23.
 */
public class HelloClient {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8883).usePlaintext(true).build();
        //  GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(channel);
        testBlockingStub(channel);
        testFutureStub(channel);

        // final CountDownLatch countDownLatch = new CountDownLatch(1);
/*
        stub.sayHello(request, new StreamObserver<HelloWorldProto.HelloReponse>() {
            @Override
            public void onNext(HelloWorldProto.HelloReponse helloReponse) {
                System.out.println(Thread.currentThread().getName() + " helloReponse :" + helloReponse.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                System.out.println("onError");
                //countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                //countDownLatch.countDown();
            }
        });
*/
        //countDownLatch.await();
       // System.out.println(Thread.currentThread().getName());
        return;
    }

    private static void testBlockingStub(ManagedChannel channel) {
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        HelloWorldProto.HelloRequest request = HelloWorldProto.HelloRequest.newBuilder().setName("kongyanli").build();

        long start = System.currentTimeMillis();
        System.out.println(stub.sayHello(request));
        System.out.println("Blocking stub using " + (System.currentTimeMillis() - start) + "ms");
    }


    private static void testFutureStub(ManagedChannel channel) {
        GreeterGrpc.GreeterFutureStub stub = GreeterGrpc.newFutureStub(channel);
        HelloWorldProto.HelloRequest request = HelloWorldProto.HelloRequest.newBuilder().setName("kongyanli").build();
        ListenableFuture<HelloWorldProto.HelloReponse> future = stub.sayHello(request);

        final long start = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        future.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
                System.out.println("Future stub is finished, using " + (System.currentTimeMillis() - start) + "ms");
            }
        }, threadPool);
        System.out.println(Thread.currentThread());
        System.out.println("Future stub using " + (System.currentTimeMillis() - start) + "ms, waiting rpc finished.");
        try {
            System.out.println(future.get());
            threadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}