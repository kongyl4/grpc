import java.io.IOException;

import com.kyl.grpc.GreeterGrpc;
import com.kyl.grpc.HelloWorldProto;
import io.grpc.stub.StreamObserver;

/**
 * Created by kongyl4 on 2016/11/23.
 */
public class HelloServer {
    private int port=8883;
    private io.grpc.Server server;

    public void run(){
        server=io.grpc.ServerBuilder.forPort(port).addService(new GreetRpc()).build();
        try {
            server.start();
            server.awaitTermination();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        HelloServer hs=new HelloServer();
        hs.run();
        return;
    }

    private class GreetRpc extends GreeterGrpc.AbstractGreeter{
        @Override
        public void sayHello(HelloWorldProto.HelloRequest request, StreamObserver<HelloWorldProto.HelloReponse> responseObserver) {
            System.out.println("Server sayhello");
            String name=request.getName();
            name="Hello"+name;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HelloWorldProto.HelloReponse reponse= HelloWorldProto.HelloReponse.newBuilder().setMessage(name).build();
            responseObserver.onNext(reponse);
            responseObserver.onCompleted();
        }

    }

}
