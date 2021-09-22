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

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.choreo.connect.enforcer.api.RequestContext;
import org.wso2.choreo.connect.enforcer.common.CacheProvider;
import org.wso2.choreo.connect.enforcer.constants.APIConstants;
import org.wso2.choreo.connect.enforcer.constants.APISecurityConstants;
import org.wso2.choreo.connect.enforcer.dto.JWTTokenPayloadInfo;
import org.wso2.choreo.connect.enforcer.exception.APISecurityException;
import org.wso2.choreo.connect.enforcer.security.AuthenticationContext;
import org.wso2.choreo.connect.enforcer.util.FilterUtils;

import java.text.ParseException;
import java.util.Map;

/**
 * Extends the APIKeyHandler to authenticate request using API Key.
 */
public class APIKeyAuthenticator extends APIKeyHandler {

    private static final Log log = LogFactory.getLog(APIKeyAuthenticator.class);

    public APIKeyAuthenticator() {
        log.info("API key authenticator initialized.");
    }

    @Override
    public boolean canAuthenticate(RequestContext requestContext) {
        String apiKey = retrieveAPIKeyHeaderValue(requestContext);
        return isAPIKey(apiKey);
    }

    private String retrieveAPIKeyHeaderValue(RequestContext requestContext) {
        Map<String, String> headers = requestContext.getHeaders();
        return headers.get(FilterUtils.getAPIKeyHeaderName(requestContext));
    }

    @Override
    public AuthenticationContext authenticate(RequestContext requestContext) throws APISecurityException {
        if (requestContext.getMatchedAPI() != null) {
            log.debug("API Key Authentication initialized");

            try {
                String apiKey = retrieveAPIKeyHeaderValue(requestContext);

                // gives an error if API key not found
                getKeyNotFoundError(apiKey);

                String[] splitToken = apiKey.split("\\.");
                SignedJWT signedJWT = SignedJWT.parse(apiKey);
                JWSHeader jwsHeader = signedJWT.getHeader();
                JWTClaimsSet payload = signedJWT.getJWTClaimsSet();

                // Check if the decoded header contains type as 'InternalKey' ???

                String tokenIdentifier = payload.getJWTID();

                //check for contain in revoke map.
                checkInRevokedMap(tokenIdentifier, splitToken);

                String apiVersion = requestContext.getMatchedAPI().getAPIConfig().getVersion();
                String apiContext = requestContext.getMatchedAPI().getAPIConfig().getBasePath();

                // Verify the token if it is found in cache
                JWTTokenPayloadInfo jwtTokenPayloadInfo = (JWTTokenPayloadInfo)
                        CacheProvider.getGatewayAPIKeyDataCache().getIfPresent(tokenIdentifier);
                boolean isVerified = verifyTokenInCache(tokenIdentifier, apiKey, payload, splitToken,
                        "API Key", jwtTokenPayloadInfo);

                // Verify token when it is not found in cache
                if (!isVerified) {
                    isVerified = verifyTokenNotInCache(jwsHeader, signedJWT, splitToken, payload, "API Key");
                }

                if (isVerified) {
                    log.debug("API Key signature is verified.");

                    if (jwtTokenPayloadInfo == null) {
                        log.debug("InternalKey payload not found in the cache.");

                        jwtTokenPayloadInfo = new JWTTokenPayloadInfo();
                        jwtTokenPayloadInfo.setPayload(payload);
                        jwtTokenPayloadInfo.setAccessToken(apiKey);
                        CacheProvider.getGatewayAPIKeyDataCache().put(tokenIdentifier, jwtTokenPayloadInfo);
                    }

                    JSONObject api = validateAPISubscription(apiContext, apiVersion, payload, splitToken, false);

                    log.debug("API Key authentication successful.");

                    return FilterUtils.generateAuthenticationContext(tokenIdentifier, payload, api,
                            requestContext.getMatchedAPI().getAPIConfig().getTier(),
                            requestContext.getMatchedAPI().getAPIConfig().getUuid());
                }
            } catch (ParseException e) {
                log.debug("API Key authentication failed. ", e);
            }

        }

        throw new APISecurityException(APIConstants.StatusCodes.UNAUTHENTICATED.getCode(),
                APISecurityConstants.API_AUTH_GENERAL_ERROR, APISecurityConstants.API_AUTH_GENERAL_ERROR_MESSAGE);
    }

    @Override
    public String getChallengeString() {
        return "";
        //check again
    }

    @Override
    public int getPriority() {
        return 10;
        //check again
    }
}
