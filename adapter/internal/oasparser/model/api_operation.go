/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

// Package model contains the implementation of DTOs to convert OpenAPI/Swagger files
// and create a common model which can represent both types.
package model

import "reflect"

// Operation type object holds data about each http method in the REST API.
type Operation struct {
	method              string
	security            []map[string][]string
	tier                string
	disableSecurity     bool
	xMediationScript	PrototypeConfig    
}

// GetMethod returns the http method name of the give API operation
func (operation *Operation) GetMethod() string {
	return operation.method
}

// GetDisableSecurity returns if the resouce is secured.
func (operation *Operation) GetDisableSecurity() bool {
	return operation.disableSecurity
}

// GetSecurity returns the security schemas defined for the http opeartion
func (operation *Operation) GetSecurity() []map[string][]string {
	return operation.security
}

// SetSecurity sets the security schemas for the http opeartion
func (operation *Operation) SetSecurity(security []map[string][]string) {
	operation.security = security
}

// GetTier returns the operation level throttling tier
func (operation *Operation) GetTier() string {
	return operation.tier
}

// GetXMediationScript returns the operation level prototype implementation configs
func (operation *Operation) GetXMediationScript() PrototypeConfig {
	return operation.xMediationScript
}

// NewOperation Creates and returns operation type object
func NewOperation(method string, security []map[string][]string, extensions map[string]interface{}, 
	prototypeConfig PrototypeConfig) *Operation {
	tier := ResolveThrottlingTier(extensions)
	disableSecurity := ResolveDisableSecurity(extensions)
	if reflect.DeepEqual(PrototypeConfig{},prototypeConfig) {
		return &Operation{method, security, tier, disableSecurity,PrototypeConfig{}}
	} 
	return &Operation{method, security, tier, disableSecurity, prototypeConfig}
}
