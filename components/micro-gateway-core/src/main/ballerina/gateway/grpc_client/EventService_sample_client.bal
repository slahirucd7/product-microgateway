import ballerina/grpc;
import ballerina/io;

// public function main (string... args) {
//      EventServiceClient ep = new("http://localhost:9090");

//     EventServiceBlockingClient blockingEp = new("http://localhost:9090");

// }

public function grpcPuublisher(string analyticsStream){
     EventServiceBlockingClient ep = new("http://localhost:9800");
    Event e = {payload : analyticsStream };
    grpc:Headers? headers = ();
    var  result = ep-> process(e);
    io:println(result);
}


service EventServiceMessageListener = service {

    resource function onMessage(string message) {
        io:println("Response received from server: " + message);
    }

    resource function onError(error err) {
        io:println("Error from Connector: " + err.reason() + " - " + <string>err.detail().message);
    }

    resource function onComplete() {
        io:println("Server Complete Sending Responses.");
    }
};

