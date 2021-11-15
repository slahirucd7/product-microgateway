/*
 * Choreo-Connect Interceptor Service
 *
 * Interceptor Service
 *
 * API version: v1
 * Generated by: Swagger Codegen (https://github.com/swagger-api/swagger-codegen.git)
 */
package swagger

type InvocationContextAuthenticationContext struct {
	TokenType string `json:"tokenType,omitempty"`

	Token string `json:"token,omitempty"`

	KeyType string `json:"keyType,omitempty"`
}
