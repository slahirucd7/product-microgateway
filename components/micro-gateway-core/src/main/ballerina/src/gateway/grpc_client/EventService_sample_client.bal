import ballerina/io;


EventServiceBlockingClient blockingEp = new("http://localhost:9801");



public function main (string... args) {

    EventServiceBlockingClient blockingEp = new("http://localhost:9801");

    Event e = {payload : "Sampleee"};
    var response = blockingEp->consume(e);
}




public analyticDataPass(string analyticsString) {
        Event e = {payload : "Sampleeee"};
        var result = blockingEp->consume(e);
        io:println(result); 
}

