// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: wso2/discovery/throttle/throttle_data.proto

package org.wso2.gateway.discovery.throttle;

public final class ThrottleDataProto {
  private ThrottleDataProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_wso2_discovery_throttle_ThrottleData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_wso2_discovery_throttle_ThrottleData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n+wso2/discovery/throttle/throttle_data." +
      "proto\022\027wso2.discovery.throttle\0321wso2/dis" +
      "covery/throttle/blocking_conditions.prot" +
      "o\"\210\001\n\014ThrottleData\022\025\n\rkey_templates\030\001 \003(" +
      "\t\022\033\n\023blocking_conditions\030\002 \003(\t\022D\n\026ip_blo" +
      "cking_conditions\030\003 \003(\0132$.wso2.discovery." +
      "throttle.IPConditionB\203\001\n#org.wso2.gatewa" +
      "y.discovery.throttleB\021ThrottleDataProtoP" +
      "\001ZGgithub.com/envoyproxy/go-control-plan" +
      "e/wso2/discovery/throttle;throttleb\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.wso2.gateway.discovery.throttle.BlockingConditionsProto.getDescriptor(),
        });
    internal_static_wso2_discovery_throttle_ThrottleData_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_wso2_discovery_throttle_ThrottleData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_wso2_discovery_throttle_ThrottleData_descriptor,
        new java.lang.String[] { "KeyTemplates", "BlockingConditions", "IpBlockingConditions", });
    org.wso2.gateway.discovery.throttle.BlockingConditionsProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
