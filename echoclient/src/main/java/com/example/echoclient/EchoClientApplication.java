package com.example.echoclient;

import com.example.echo.gen.proto.EchoRequest;
import com.example.echo.gen.proto.EchoResponse;
import com.example.echo.gen.proto.EchoServiceGrpc;
import com.example.echoclient.factories.MultiAddressNameResolverFactory;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class EchoClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EchoClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        NameResolver.Factory nameResolverFactory = new MultiAddressNameResolverFactory(
                new InetSocketAddress("localhost", 6000),
                new InetSocketAddress("localhost", 6001),
                new InetSocketAddress("localhost", 6002)
        );

        ManagedChannel managedChannelForEcho = ManagedChannelBuilder.forTarget("echo-service")
                .nameResolverFactory(nameResolverFactory)
                .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
                .usePlaintext()
                .build();

        EchoServiceGrpc.EchoServiceBlockingStub stub = EchoServiceGrpc.newBlockingStub(managedChannelForEcho);
        for (int i = 0; i < 100; i++) {
            EchoResponse response = stub.echo(EchoRequest.newBuilder()
                    .setMessage("Hello!")
                    .build());
            System.out.println(response.getMessage());
        }
    }
}
