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
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.choreo.connect.enforcer.common.CacheProvider;
import org.wso2.choreo.connect.enforcer.constants.APIConstants;
import org.wso2.choreo.connect.enforcer.constants.APISecurityConstants;
import org.wso2.choreo.connect.enforcer.dto.JWTTokenPayloadInfo;
import org.wso2.choreo.connect.enforcer.exception.APISecurityException;
import org.wso2.choreo.connect.enforcer.exception.EnforcerException;
import org.wso2.choreo.connect.enforcer.security.Authenticator;
import org.wso2.choreo.connect.enforcer.security.jwt.validator.RevokedJWTDataHolder;
import org.wso2.choreo.connect.enforcer.util.FilterUtils;

/**
 * An abstract class which can be used to handle API keys.
 */
public abstract class APIKeyHandler implements Authenticator {

    private static final Log log = LogFactory.getLog(APIKeyHandler.class);

    public boolean isAPIKey(String apiKey) {
        if (apiKey != null && apiKey.split("\\.").length == 3) {
            return true;
        }
        return false;
    }

    public void getKeyNotFoundError(String apiKey) throws APISecurityException {
        if (StringUtils.isEmpty(apiKey)) {
            log.error("Cannot find API key header");
            throw new APISecurityException(APIConstants.StatusCodes.UNAUTHENTICATED.getCode(),
                    APISecurityConstants.API_AUTH_INVALID_CREDENTIALS,
                    APISecurityConstants.API_AUTH_INVALID_CREDENTIALS_MESSAGE);
        }
    }

    public void checkInRevokedMap(String tokenIdentifier, String[] splitToken) throws APISecurityException {
        if (RevokedJWTDataHolder.isJWTTokenSignatureExistsInRevokedMap(tokenIdentifier)) {
            if (log.isDebugEnabled()) {
                log.debug("API key retrieved from the revoked jwt token map. Token: "
                        + FilterUtils.getMaskedToken(splitToken[0]));
            }
            log.error("Invalid API Key. " + FilterUtils.getMaskedToken(splitToken[0]));
            throw new APISecurityException(APIConstants.StatusCodes.UNAUTHENTICATED.getCode(),
                    APISecurityConstants.API_AUTH_INVALID_CREDENTIALS,
                    APISecurityConstants.API_AUTH_INVALID_CREDENTIALS_MESSAGE);
        }
    }

    public boolean verifyTokenInCache(String tokenIdentifier, String apiKey, JWTClaimsSet payload,
                                      String[] splitToken, String apiKeyType,
                                      JWTTokenPayloadInfo jwtTokenPayloadInfo) throws APISecurityException {
        boolean isVerified = false;
        boolean isInvalidInternalAPIKey = CacheProvider.getInvalidGatewayInternalKeyCache()
                .getIfPresent(tokenIdentifier) != null &&
                apiKey.equals(CacheProvider.getInvalidGatewayInternalKeyCache().getIfPresent(tokenIdentifier));
        boolean isInvalidAPIKey = CacheProvider.getInvalidGatewayAPIKeyCache()
                .getIfPresent(tokenIdentifier) != null &&
                apiKey.equals(CacheProvider.getInvalidGatewayAPIKeyCache().getIfPresent(tokenIdentifier));
        if (jwtTokenPayloadInfo != null) {
            String cachedToken = jwtTokenPayloadInfo.getAccessToken();
            isVerified = cachedToken.equals(apiKey) && !isJwtTokenExpired(payload, apiKeyType);
        } else if (isInvalidInternalAPIKey || isInvalidAPIKey) {
            getInvalidKeyInCacheError(splitToken);
        }
        return isVerified;
    }

    public void getInvalidKeyInCacheError(String[] splitToken) throws APISecurityException {
        if (log.isDebugEnabled()) {
            log.debug("API key retrieved from the invalid API key cache. "
                    + FilterUtils.getMaskedToken(splitToken[0]));
        }
        log.error("Invalid API key. " + FilterUtils.getMaskedToken(splitToken[0]));
        throw new APISecurityException(APIConstants.StatusCodes.UNAUTHENTICATED.getCode(),
                APISecurityConstants.API_AUTH_INVALID_CREDENTIALS,
                APISecurityConstants.API_AUTH_INVALID_CREDENTIALS_MESSAGE);
    }

    public boolean verifyTokenNotInCache(JWSHeader jwsHeader, SignedJWT signedJWT, String[] splitToken,
                                         JWTClaimsSet payload, String apiKeyType) throws APISecurityException {
        boolean isVerified = false;
        if (log.isDebugEnabled()) {
            log.debug(apiKeyType + " not found in the cache.");
        }

        String alias = "";
        if (jwsHeader != null && StringUtils.isNotEmpty(jwsHeader.getKeyID())) {
            alias = jwsHeader.getKeyID();
        }

        try {
            isVerified = JWTUtil.verifyTokenSignature(signedJWT, alias) && !isJwtTokenExpired(payload, apiKeyType);
        } catch (EnforcerException e) {
            log.error(apiKeyType + " authentication failed. " +
                    FilterUtils.getMaskedToken(splitToken[0]));
            throw new APISecurityException(APIConstants.StatusCodes.UNAUTHENTICATED.getCode(),
                    APISecurityConstants.API_AUTH_INVALID_CREDENTIALS,
                    APISecurityConstants.API_AUTH_INVALID_CREDENTIALS_MESSAGE);
        }
        return isVerified;
    }


    /**
     * Check whether the jwt token is expired or not.
     *
     * @param payload The payload of the JWT token
     * @return returns true if the JWT token is expired
     */
    public boolean isJwtTokenExpired(JWTClaimsSet payload, String keyType) throws APISecurityException {

        int timestampSkew = (int) getTimeStampSkewInSeconds();
        DefaultJWTClaimsVerifier jwtClaimsSetVerifier = new DefaultJWTClaimsVerifier();
        jwtClaimsSetVerifier.setMaxClockSkew(timestampSkew);
        try {
            jwtClaimsSetVerifier.verify(payload);
            if (log.isDebugEnabled()) {
                log.debug(keyType + " is not expired. User: " + payload.getSubject());
            }
        } catch (BadJWTException e) {
            if ("Expired JWT".equals(e.getMessage())) {
                if (log.isDebugEnabled()) {
                    log.debug(keyType + "API key is expired.");
                }
                if (APIConstants.JwtTokenConstants.INTERNAL_KEY_TOKEN_TYPE.equals(keyType)) {
                    CacheProvider.getGatewayInternalKeyDataCache().invalidate(payload.getJWTID());
                    CacheProvider.getInvalidGatewayInternalKeyCache().put(payload.getJWTID(), "carbon.super");
                } else {
                    CacheProvider.getGatewayAPIKeyDataCache().invalidate(payload.getJWTID());
                    CacheProvider.getInvalidGatewayAPIKeyCache().put(payload.getJWTID(), "carbon.super");
                }
                throw new APISecurityException(APIConstants.StatusCodes.UNAUTHENTICATED.getCode(),
                        APISecurityConstants.API_AUTH_INVALID_CREDENTIALS,
                        APISecurityConstants.API_AUTH_INVALID_CREDENTIALS_MESSAGE);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug(keyType + " is not expired. User: " + payload.getSubject());
        }
        return false;
    }

    protected long getTimeStampSkewInSeconds() {
        //TODO : Read from config
        return 5;
    }

    public static JSONObject validateAPISubscription(String apiContext, String apiVersion, JWTClaimsSet payload,
                                                     String[] splitToken, boolean isOauth)
            throws APISecurityException {

        JSONObject api = null;

        if (payload.getClaim(APIConstants.JwtTokenConstants.SUBSCRIBED_APIS) != null) {
            // Subscription validation
            JSONArray subscribedAPIs =
                    (JSONArray) payload.getClaim(APIConstants.JwtTokenConstants.SUBSCRIBED_APIS);
            for (Object subscribedAPI : subscribedAPIs) {
                JSONObject subscribedAPIsJSONObject = (JSONObject) subscribedAPI;
                if (apiContext
                        .equals(subscribedAPIsJSONObject.getAsString(APIConstants.JwtTokenConstants.API_CONTEXT)) &&
                        apiVersion
                                .equals(subscribedAPIsJSONObject.getAsString(APIConstants.JwtTokenConstants.API_VERSION)
                                )) {
                    api = subscribedAPIsJSONObject;
                    if (log.isDebugEnabled()) {
                        log.debug("User is subscribed to the API: " + apiContext + ", " +
                                "version: " + apiVersion + ". Token: " + FilterUtils.getMaskedToken(splitToken[0]));
                    }
                    break;
                }
            }
            if (api == null) {
                if (log.isDebugEnabled()) {
                    log.debug("User is not subscribed to access the API: " + apiContext +
                            ", version: " + apiVersion + ". Token: " + FilterUtils.getMaskedToken(splitToken[0]));
                }
                log.error("User is not subscribed to access the API.");
                throw new APISecurityException(APIConstants.StatusCodes.UNAUTHORIZED.getCode(),
                        APISecurityConstants.API_AUTH_FORBIDDEN, APISecurityConstants.API_AUTH_FORBIDDEN_MESSAGE);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("No subscription information found in the token.");
            }
            // we perform mandatory authentication for Api Keys
            if (!isOauth) {
                log.error("User is not subscribed to access the API.");
                throw new APISecurityException(APIConstants.StatusCodes.UNAUTHORIZED.getCode(),
                        APISecurityConstants.API_AUTH_FORBIDDEN, APISecurityConstants.API_AUTH_FORBIDDEN_MESSAGE);
            }
        }
        return api;
    }

}
