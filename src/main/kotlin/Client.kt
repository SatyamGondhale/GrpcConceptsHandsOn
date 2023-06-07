import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit


class HelloWorldClient(private val channel: ManagedChannel) : Closeable {

    private val stub: GreeterGrpcKt.GreeterCoroutineStub = GreeterGrpcKt.GreeterCoroutineStub(channel)
    suspend fun greet(name: String) {
        val request = Helloworld.HelloRequest.newBuilder().setName(name).build()
        val response = stub.sayHello(request)
        println("Received: ${response.message}")
        close()
    }


    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
        println("Server Closed")
    }
}


suspend fun main(args: Array<String>) {
    val port = 50051
    //A gRPC channel provides a connection to a gRPC server on a specified host and port.
    val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()
    val client = HelloWorldClient(channel)
    val user =  "Satyam"
    client.greet(user)
}


/*
fun main() = runBlocking {
   val channel = ManagedChannelBuilder
       .forAddress("localhost", 50051)
       .usePlaintext()
       .build()


   val stub = GreeterGrpcKt.GreeterCoroutineStub(channel)


   val request = HelloRequest.newBuilder()
       .setName("John")
       .build()


   val response = stub.sayHello(request)


   println("Received response: ${response.message}")


   channel.shutdown()
}*/

