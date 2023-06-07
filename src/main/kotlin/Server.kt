import io.grpc.Server
import io.grpc.ServerBuilder

class HelloWorldServer(private val port: Int) {
    val server: Server = ServerBuilder
        .forPort(port)
        .addService(GreeterService())
        .build()


    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@HelloWorldServer.stop()
                println("*** server shut down")
            }
        )
    }


    private fun stop() {
        server.shutdown()
    }


    fun blockUntilShutdown() {
        server.awaitTermination()
    }


    class GreeterService : GreeterGrpcKt.GreeterCoroutineImplBase(){
        override suspend fun sayHello(request: Helloworld.HelloRequest): Helloworld.HelloReply {
            val message = "Hello, ${request.name}!"
            return Helloworld.HelloReply.newBuilder().setMessage(message).build()
        }
    }
}


fun main() {
    val port = 50051
    val server = HelloWorldServer(port)
    server.start()
    server.blockUntilShutdown()
}

