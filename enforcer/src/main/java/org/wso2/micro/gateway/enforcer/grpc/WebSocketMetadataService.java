/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.micro.gateway.enforcer.grpc;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.micro.gateway.enforcer.websocket.RateLimitRequest;
import org.wso2.micro.gateway.enforcer.websocket.RateLimitResponse;
import org.wso2.micro.gateway.enforcer.websocket.WebSocketMetadataServiceGrpc;

import java.util.concurrent.ConcurrentHashMap;

/**
 * gRPC service for processing web socket frame related metadata for websocket throttling and analytics. This class
 * contains a ConcurrentHashMap of the StreamObservers that corresponds to open grpc bidirectional streams with
 * envoy mgw_websocket filter.
 */
public class WebSocketMetadataService extends WebSocketMetadataServiceGrpc.WebSocketMetadataServiceImplBase {
    private static final Logger logger = LogManager.getLogger(WebSocketMetadataService.class);
    private static ConcurrentHashMap<String, WebSocketResponseObserver> responseObservers = new ConcurrentHashMap<>();
    @Override
    public StreamObserver<RateLimitRequest> publishMetadata(StreamObserver<RateLimitResponse> responseObserver) {
        logger.debug("publishMetadata invoked from websocket meatadata service");
        return new WebSocketResponseObserver(responseObserver);
    }

    public static void addObserver(String streamId, WebSocketResponseObserver observer) {
        responseObservers.put(streamId, observer);
    }

    public static void removeObserver(String streamId) {
        responseObservers.remove(streamId);
    }


}
