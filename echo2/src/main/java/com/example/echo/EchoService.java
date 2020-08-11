package com.example.echo;

import com.example.echo.gen.proto.EchoRequest;
import com.example.echo.gen.proto.EchoResponse;
import com.example.echo.gen.proto.EchoServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
public class EchoService extends EchoServiceGrpc.EchoServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(EchoService.class);

    @Override
    public void echo(EchoRequest request, StreamObserver<EchoResponse> responseObserver) {
        logger.info("Got Request with message : " + request.getMessage());
        EchoResponse echoResponse = EchoResponse.newBuilder().setMessage(request.getMessage()).build();
        responseObserver.onNext(echoResponse);
        responseObserver.onCompleted();
    }
}
