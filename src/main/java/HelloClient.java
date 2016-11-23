import com.kyl.grpc.GreeterGrpc;
import com.kyl.grpc.HelloWorldProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kongyl4 on 2016/11/23.
 */
public class HelloClient {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8883).usePlaintext(true).build();
      //  GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(channel);
    GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        HelloWorldProto.HelloRequest request=HelloWorldProto.HelloRequest.newBuilder().setName("kongyanli").build();

       System.out.println(stub.sayHello(request));

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
        System.out.println(Thread.currentThread().getName());
        return;
    }
}