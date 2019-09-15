import ballerina/grpc;
import ballerina/io;

public type EventServiceBlockingClient client object {
    private grpc:Client grpcClient = new;
    private grpc:ClientEndpointConfig config = {};
    private string url;

    function __init(string url, grpc:ClientEndpointConfig? config = ()) {
        self.config = config ?: {};
        self.url = url;
        // initialize client endpoint.
        grpc:Client c = new;
        c.init(self.url, self.config);
        error? result = c.initStub("blocking", ROOT_DESCRIPTOR, getDescriptorMap());
        if (result is error) {
            panic result;
        } else {
            self.grpcClient = c;
        }
    }


    remote function process(Event req, grpc:Headers? headers = ()) returns ((Event, grpc:Headers)|error) {
        
        var payload = check self.grpcClient->blockingExecute("org.wso2.grpc.eventservice.EventService/process", req, headers = headers);
        grpc:Headers resHeaders = new;
        any result = ();
        (result, resHeaders) = payload;
        var value = Event.convert(result);
        if (value is Event) {
            return (value, resHeaders);
        } else {
            error err = error("{ballerina/grpc}INTERNAL", {"message": value.reason()});
            return err;
        }
    }

    remote function consume(Event req, grpc:Headers? headers = ()) returns (grpc:Headers|error) {
        
        var payload = check self.grpcClient->blockingExecute("org.wso2.grpc.eventservice.EventService/consume", req, headers = headers);
        grpc:Headers resHeaders = new;
        (_, resHeaders) = payload;
        return resHeaders;
    }

};

public type EventServiceClient client object {
    private grpc:Client grpcClient = new;
    private grpc:ClientEndpointConfig config = {};
    private string url;

    function __init(string url, grpc:ClientEndpointConfig? config = ()) {
        self.config = config ?: {};
        self.url = url;
        // initialize client endpoint.
        grpc:Client c = new;
        c.init(self.url, self.config);
        error? result = c.initStub("non-blocking", ROOT_DESCRIPTOR, getDescriptorMap());
        if (result is error) {
            panic result;
        } else {
            self.grpcClient = c;
        }
    }


    remote function process(Event req, service msgListener, grpc:Headers? headers = ()) returns (error?) {
        
        return self.grpcClient->nonBlockingExecute("org.wso2.grpc.eventservice.EventService/process", req, msgListener, headers = headers);
    }

    remote function consume(Event req, service msgListener, grpc:Headers? headers = ()) returns (error?) {
        
        return self.grpcClient->nonBlockingExecute("org.wso2.grpc.eventservice.EventService/consume", req, msgListener, headers = headers);
    }

};

type Empty record {
    !...;
};


type Event record {
    string payload;
    !...;
};



const string ROOT_DESCRIPTOR = "0A0F616E616C79746963732E70726F746F121A6F72672E77736F322E677270632E6576656E74736572766963651A1B676F6F676C652F70726F746F6275662F656D7074792E70726F746F22210A054576656E7412180A077061796C6F616418012001280952077061796C6F616432A9010A0C4576656E745365727669636512510A0770726F6365737312212E6F72672E77736F322E677270632E6576656E74736572766963652E4576656E741A212E6F72672E77736F322E677270632E6576656E74736572766963652E4576656E74220012460A07636F6E73756D6512212E6F72672E77736F322E677270632E6576656E74736572766963652E4576656E741A162E676F6F676C652E70726F746F6275662E456D707479220042110A0D6F72672E77736F322E677270635001620670726F746F33";
function getDescriptorMap() returns map<string> {
    return {
        "analytics.proto":"0A0F616E616C79746963732E70726F746F121A6F72672E77736F322E677270632E6576656E74736572766963651A1B676F6F676C652F70726F746F6275662F656D7074792E70726F746F22210A054576656E7412180A077061796C6F616418012001280952077061796C6F616432A9010A0C4576656E745365727669636512510A0770726F6365737312212E6F72672E77736F322E677270632E6576656E74736572766963652E4576656E741A212E6F72672E77736F322E677270632E6576656E74736572766963652E4576656E74220012460A07636F6E73756D6512212E6F72672E77736F322E677270632E6576656E74736572766963652E4576656E741A162E676F6F676C652E70726F746F6275662E456D707479220042110A0D6F72672E77736F322E677270635001620670726F746F33",
        "google/protobuf/empty.proto":"0A1B676F6F676C652F70726F746F6275662F656D7074792E70726F746F120F676F6F676C652E70726F746F62756622070A05456D70747942540A13636F6D2E676F6F676C652E70726F746F627566420A456D70747950726F746F50015A057479706573F80101A20203475042AA021E476F6F676C652E50726F746F6275662E57656C6C4B6E6F776E5479706573620670726F746F33"
        
    };
}

