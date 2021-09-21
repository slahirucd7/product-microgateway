/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.choreo.connect.enforcer.security.jwt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.choreo.connect.enforcer.api.RequestContext;
import org.wso2.choreo.connect.enforcer.exception.APISecurityException;
import org.wso2.choreo.connect.enforcer.security.AuthenticationContext;
import org.wso2.choreo.connect.enforcer.util.FilterUtils;

import java.util.Map;

/**
 * Extends the APIKeyHandler to authenticate request using API Key.
 */
public class APIKeyAuthenticator extends APIKeyHandler {

    private static final Log log = LogFactory.getLog(APIKeyAuthenticator.class);

    public APIKeyAuthenticator() {
        log.info("API key authenticator created.");
    }

    @Override
    public boolean canAuthenticate(RequestContext requestContext) {
        String apiKey = retrieveAPIKeyHeaderValue(requestContext);
        if (apiKey != null && apiKey.split("\\.").length == 3) {
            return true;
        }
        return false;
    }

    private String retrieveAPIKeyHeaderValue(RequestContext requestContext) {
        Map<String, String> headers = requestContext.getHeaders();
        return headers.get(FilterUtils.getAPIKeyHeaderName(requestContext));
    }

    @Override
    public AuthenticationContext authenticate(RequestContext requestContext) throws APISecurityException {
        return null;
    }

    @Override
    public String getChallengeString() {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
