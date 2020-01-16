import ballerina/grpc;

public type AnalyticsSendServiceClient client object {

    *grpc:AbstractClientEndpoint;

    private grpc:Client? grpcClient = ();

    public function __init(string url, grpc:ClientConfiguration? config = ()) {
        // initialize client endpoint.
        grpc:Client c = new(url, config);
        grpc:Error? result = c.initStub(self, "non-blocking", ROOT_DESCRIPTOR, getDescriptorMap());
        if (result is grpc:Error) {
            error err = result;
            panic err;
        } else {
            self.grpcClient = c;
        }
    }

    public remote function sendAnalytics(service msgListener, grpc:Headers? headers = ()) returns (grpc:StreamingClient|grpc:Error) {
        if !(self.grpcClient is grpc:Client) {
            error err = error("UninitializedFieldsErrorType", message = "Field(s) are not initialized");
            return grpc:prepareError(grpc:INTERNAL_ERROR, "Field(s) are not initialized", err);
        }
        grpc:Client tempGrpcClient = <grpc:Client> self.grpcClient;
        return tempGrpcClient->streamingExecute("AnalyticsSendService/sendAnalytics", msgListener, headers);
    }
};

public type Empty record {|
    
|};


public type AnalyticsStreamMessage record {|
    string messageStreamName = "";
    string meta_clientType = "";
    string applicationConsumerKey = "";
    string applicationName = "";
    string applicationId = "";
    string applicationOwner = "";
    string apiContext = "";
    string apiName = "";
    string apiVersion = "";
    string apiResourcePath = "";
    string apiResourceTemplate = "";
    string apiMethod = "";
    string apiCreator = "";
    string apiCreatorTenantDomain = "";
    string apiTier = "";
    string apiHostname = "";
    string username = "";
    string userTenantDomain = "";
    string userIp = "";
    string userAgent = "";
    int requestTimestamp = 0;
    boolean throttledOut = false;
    int responseTime = 0;
    int serviceTime = 0;
    int backendTime = 0;
    boolean responseCacheHit = false;
    int responseSize = 0;
    string protocol = "";
    int responseCode = 0;
    string destination = "";
    int securityLatency = 0;
    int throttlingLatency = 0;
    int requestMedLat = 0;
    int responseMedLat = 0;
    int backendLatency = 0;
    int otherLatency = 0;
    string gatewayType = "";
    string label = "";
    string subscriber = "";
    string throttledOutReason = "";
    int throttledOutTimestamp = 0;
    string hostname = "";
    string errorCode = "";
    string errorMessage = "";
    
|};



const string ROOT_DESCRIPTOR = "0A0F416E616C79746963732E70726F746F1A1B676F6F676C652F70726F746F6275662F656D7074792E70726F746F22810D0A16416E616C797469637353747265616D4D657373616765122C0A116D65737361676553747265616D4E616D6518012001280952116D65737361676553747265616D4E616D6512270A0F6D6574615F636C69656E7454797065180220012809520E6D657461436C69656E745479706512360A166170706C69636174696F6E436F6E73756D65724B657918032001280952166170706C69636174696F6E436F6E73756D65724B657912280A0F6170706C69636174696F6E4E616D65180420012809520F6170706C69636174696F6E4E616D6512240A0D6170706C69636174696F6E4964180520012809520D6170706C69636174696F6E4964122A0A106170706C69636174696F6E4F776E657218062001280952106170706C69636174696F6E4F776E6572121E0A0A617069436F6E74657874180720012809520A617069436F6E7465787412180A076170694E616D6518082001280952076170694E616D65121E0A0A61706956657273696F6E180920012809520A61706956657273696F6E12280A0F6170695265736F7572636550617468180A20012809520F6170695265736F757263655061746812300A136170695265736F7572636554656D706C617465180B2001280952136170695265736F7572636554656D706C617465121C0A096170694D6574686F64180C2001280952096170694D6574686F64121E0A0A61706943726561746F72180D20012809520A61706943726561746F7212360A1661706943726561746F7254656E616E74446F6D61696E180E20012809521661706943726561746F7254656E616E74446F6D61696E12180A0761706954696572180F2001280952076170695469657212200A0B617069486F73746E616D65181020012809520B617069486F73746E616D65121A0A08757365726E616D651811200128095208757365726E616D65122A0A107573657254656E616E74446F6D61696E18122001280952107573657254656E616E74446F6D61696E12160A067573657249701813200128095206757365724970121C0A09757365724167656E741814200128095209757365724167656E74122A0A107265717565737454696D657374616D7018152001280352107265717565737454696D657374616D7012220A0C7468726F74746C65644F7574181620012808520C7468726F74746C65644F757412220A0C726573706F6E736554696D65181720012803520C726573706F6E736554696D6512200A0B7365727669636554696D65181820012803520B7365727669636554696D6512200A0B6261636B656E6454696D65181920012803520B6261636B656E6454696D65122A0A10726573706F6E73654361636865486974181A200128085210726573706F6E7365436163686548697412220A0C726573706F6E736553697A65181B20012803520C726573706F6E736553697A65121A0A0870726F746F636F6C181C20012809520870726F746F636F6C12220A0C726573706F6E7365436F6465181D20012805520C726573706F6E7365436F646512200A0B64657374696E6174696F6E181E20012809520B64657374696E6174696F6E12280A0F73656375726974794C6174656E6379181F20012803520F73656375726974794C6174656E6379122C0A117468726F74746C696E674C6174656E637918202001280352117468726F74746C696E674C6174656E637912240A0D726571756573744D65644C6174182120012803520D726571756573744D65644C617412260A0E726573706F6E73654D65644C6174182220012803520E726573706F6E73654D65644C617412260A0E6261636B656E644C6174656E6379182320012803520E6261636B656E644C6174656E637912220A0C6F746865724C6174656E6379182420012803520C6F746865724C6174656E637912200A0B6761746577617954797065182520012809520B676174657761795479706512140A056C6162656C18262001280952056C6162656C121E0A0A73756273637269626572182720012809520A73756273637269626572122E0A127468726F74746C65644F7574526561736F6E18282001280952127468726F74746C65644F7574526561736F6E12340A157468726F74746C65644F757454696D657374616D7018292001280352157468726F74746C65644F757454696D657374616D70121A0A08686F73746E616D65182A200128095208686F73746E616D65121C0A096572726F72436F6465182B2001280952096572726F72436F646512220A0C6572726F724D657373616765182C20012809520C6572726F724D657373616765325A0A14416E616C797469637353656E645365727669636512420A0D73656E64416E616C797469637312172E416E616C797469637353747265616D4D6573736167651A162E676F6F676C652E70726F746F6275662E456D707479280142270A236F72672E77736F322E616E616C79746963732E6D67772E677270632E736572766963655001620670726F746F33";
function getDescriptorMap() returns map<string> {
    return {
        "Analytics.proto":"0A0F416E616C79746963732E70726F746F1A1B676F6F676C652F70726F746F6275662F656D7074792E70726F746F22810D0A16416E616C797469637353747265616D4D657373616765122C0A116D65737361676553747265616D4E616D6518012001280952116D65737361676553747265616D4E616D6512270A0F6D6574615F636C69656E7454797065180220012809520E6D657461436C69656E745479706512360A166170706C69636174696F6E436F6E73756D65724B657918032001280952166170706C69636174696F6E436F6E73756D65724B657912280A0F6170706C69636174696F6E4E616D65180420012809520F6170706C69636174696F6E4E616D6512240A0D6170706C69636174696F6E4964180520012809520D6170706C69636174696F6E4964122A0A106170706C69636174696F6E4F776E657218062001280952106170706C69636174696F6E4F776E6572121E0A0A617069436F6E74657874180720012809520A617069436F6E7465787412180A076170694E616D6518082001280952076170694E616D65121E0A0A61706956657273696F6E180920012809520A61706956657273696F6E12280A0F6170695265736F7572636550617468180A20012809520F6170695265736F757263655061746812300A136170695265736F7572636554656D706C617465180B2001280952136170695265736F7572636554656D706C617465121C0A096170694D6574686F64180C2001280952096170694D6574686F64121E0A0A61706943726561746F72180D20012809520A61706943726561746F7212360A1661706943726561746F7254656E616E74446F6D61696E180E20012809521661706943726561746F7254656E616E74446F6D61696E12180A0761706954696572180F2001280952076170695469657212200A0B617069486F73746E616D65181020012809520B617069486F73746E616D65121A0A08757365726E616D651811200128095208757365726E616D65122A0A107573657254656E616E74446F6D61696E18122001280952107573657254656E616E74446F6D61696E12160A067573657249701813200128095206757365724970121C0A09757365724167656E741814200128095209757365724167656E74122A0A107265717565737454696D657374616D7018152001280352107265717565737454696D657374616D7012220A0C7468726F74746C65644F7574181620012808520C7468726F74746C65644F757412220A0C726573706F6E736554696D65181720012803520C726573706F6E736554696D6512200A0B7365727669636554696D65181820012803520B7365727669636554696D6512200A0B6261636B656E6454696D65181920012803520B6261636B656E6454696D65122A0A10726573706F6E73654361636865486974181A200128085210726573706F6E7365436163686548697412220A0C726573706F6E736553697A65181B20012803520C726573706F6E736553697A65121A0A0870726F746F636F6C181C20012809520870726F746F636F6C12220A0C726573706F6E7365436F6465181D20012805520C726573706F6E7365436F646512200A0B64657374696E6174696F6E181E20012809520B64657374696E6174696F6E12280A0F73656375726974794C6174656E6379181F20012803520F73656375726974794C6174656E6379122C0A117468726F74746C696E674C6174656E637918202001280352117468726F74746C696E674C6174656E637912240A0D726571756573744D65644C6174182120012803520D726571756573744D65644C617412260A0E726573706F6E73654D65644C6174182220012803520E726573706F6E73654D65644C617412260A0E6261636B656E644C6174656E6379182320012803520E6261636B656E644C6174656E637912220A0C6F746865724C6174656E6379182420012803520C6F746865724C6174656E637912200A0B6761746577617954797065182520012809520B676174657761795479706512140A056C6162656C18262001280952056C6162656C121E0A0A73756273637269626572182720012809520A73756273637269626572122E0A127468726F74746C65644F7574526561736F6E18282001280952127468726F74746C65644F7574526561736F6E12340A157468726F74746C65644F757454696D657374616D7018292001280352157468726F74746C65644F757454696D657374616D70121A0A08686F73746E616D65182A200128095208686F73746E616D65121C0A096572726F72436F6465182B2001280952096572726F72436F646512220A0C6572726F724D657373616765182C20012809520C6572726F724D657373616765325A0A14416E616C797469637353656E645365727669636512420A0D73656E64416E616C797469637312172E416E616C797469637353747265616D4D6573736167651A162E676F6F676C652E70726F746F6275662E456D707479280142270A236F72672E77736F322E616E616C79746963732E6D67772E677270632E736572766963655001620670726F746F33",
        "google/protobuf/empty.proto":"0A1B676F6F676C652F70726F746F6275662F656D7074792E70726F746F120F676F6F676C652E70726F746F62756622070A05456D70747942540A13636F6D2E676F6F676C652E70726F746F627566420A456D70747950726F746F50015A057479706573F80101A20203475042AA021E476F6F676C652E50726F746F6275662E57656C6C4B6E6F776E5479706573620670726F746F33"
        
    };
}

