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
package org.wso2.choreo.connect.enforcer.api.config;

import org.wso2.choreo.connect.discovery.api.EndpointSecurity;
import org.wso2.choreo.connect.enforcer.throttle.ThrottleConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the metadata related to the API. Common collection to hold data about any API type like REST, gRPC and etc.
 */
public class APIConfig {
    private String name;
    private String version;
    private String vhost;
    private String basePath;
    private String apiType;
    private List<String> productionUrls;
    private List<String> sandboxUrls;
    private String apiLifeCycleState;
    private String authorizationHeader;
    private EndpointSecurity endpointSecurity;
    private String organizationId;
    private String uuid;

    private List<String> securitySchemes = new ArrayList<>();
    private String tier = ThrottleConstants.UNLIMITED_TIER;
    private boolean disableSecurity = false;
    private List<ResourceConfig> resources = new ArrayList<>();

    public String getApiType() {
        return apiType;
    }

    public List<String> getProductionUrls() {
        return productionUrls;
    }

    public List<String> getSandboxUrls() {
        return sandboxUrls;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getUuid() {
        return uuid;
    }

    /**
     * Implements builder pattern to build an API Config object.
     */
    public static class Builder {

        private String name;
        private String version;
        private String vhost;
        private String basePath;
        private String apiType;
        private List<String> productionUrls;
        private List<String> sandboxUrls;
        private String apiLifeCycleState;
        private String authorizationHeader;
        private EndpointSecurity endpointSecurity;
        private String organizationId;
        private String uuid;

        private List<String> securitySchemes = new ArrayList<>();
        private String tier = ThrottleConstants.UNLIMITED_TIER;
        private boolean disableSecurity = false;
        private List<ResourceConfig> resources = new ArrayList<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder vhost(String vhost) {
            this.vhost = vhost;
            return this;
        }

        public Builder basePath(String basePath) {
            this.basePath = basePath;
            return this;
        }

        public Builder apiType(String apiType) {
            this.apiType = apiType;
            return this;
        }

        public Builder apiLifeCycleState(String apiLifeCycleState) {
            this.apiLifeCycleState = apiLifeCycleState;
            return this;
        }

        public Builder tier(String tier) {
            this.tier = tier;
            return this;
        }

        public Builder disableSecurity(boolean enabled) {
            this.disableSecurity = enabled;
            return this;
        }

        public Builder resources(List<ResourceConfig> resources) {
            this.resources = resources;
            return this;
        }

        public Builder productionUrls(List<String> productionUrls) {
            this.productionUrls = productionUrls;
            return this;
        }

        public Builder sandboxUrls(List<String> sandboxUrls) {
            this.sandboxUrls = sandboxUrls;
            return this;
        }

        public Builder securitySchema(List<String> securitySchemes) {
            this.securitySchemes = securitySchemes;
            return this;
        }

        public Builder endpointSecurity(EndpointSecurity endpointSecurity) {
            this.endpointSecurity = endpointSecurity;
            return this;
        }

        public Builder authHeader(String authorizationHeader) {
            this.authorizationHeader = authorizationHeader;
            return this;
        }

        public Builder organizationId(String organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public APIConfig build() {
            APIConfig apiConfig = new APIConfig();
            apiConfig.name = this.name;
            apiConfig.vhost = this.vhost;
            apiConfig.basePath = this.basePath;
            apiConfig.version = this.version;
            apiConfig.apiLifeCycleState = this.apiLifeCycleState;
            apiConfig.resources = this.resources;
            apiConfig.apiType = this.apiType;
            apiConfig.productionUrls = this.productionUrls;
            apiConfig.sandboxUrls = this.sandboxUrls;
            apiConfig.securitySchemes = this.securitySchemes;
            apiConfig.tier = this.tier;
            apiConfig.endpointSecurity = this.endpointSecurity;
            apiConfig.authorizationHeader = this.authorizationHeader;
            apiConfig.disableSecurity = this.disableSecurity;
            apiConfig.organizationId = this.organizationId;
            apiConfig.uuid = this.uuid;
            return apiConfig;
        }
    }

    private APIConfig() {
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public EndpointSecurity getEndpointSecurity() {
        return endpointSecurity;
    }

    public String getVhost() {
        return vhost;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getAuthHeader() {
        return authorizationHeader;
    }

    public String getApiLifeCycleState() {
        return apiLifeCycleState;
    }

    public List<String> getSecuritySchemas() {
        return securitySchemes;
    }

    public String getTier() {
        return tier;
    }

    public boolean isDisableSecurity() {
        return disableSecurity;
    }

    public List<ResourceConfig> getResources() {
        return resources;
    }
}
