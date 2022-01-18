/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package model

import (
	"errors"

	"github.com/go-openapi/spec"
	"github.com/google/uuid"
	logger "github.com/wso2/product-microgateway/adapter/internal/loggers"
)

// SetInfoSwagger populates the MgwSwagger object with the properties within the openAPI v2
// (swagger) definition.
// The title, version, description, vendor extension map, endpoints based on host and schemes properties,
// and pathItem level information are populated here.
//
// for each pathItem; vendor extensions, available http Methods,
// are populated. Each resource corresponding to a pathItem, has the property called iD, which is a
// UUID.
//
// No operation specific information is extracted.
func (swagger *MgwSwagger) SetInfoSwagger(swagger2 spec.Swagger) error {
	if swagger2.Info != nil {
		swagger.description = swagger2.Info.Description
		swagger.title = swagger2.Info.Title
		swagger.version = swagger2.Info.Version
	}
	swagger.vendorExtensions = swagger2.VendorExtensible.Extensions
	swagger.securityScheme = setSecurityDefinitions(swagger2)
	swagger.security = swagger2.Security
	swagger.resources = setResourcesSwagger(swagger2, swagger)

	if(swagger.IsProtoTyped) {
		swagger.apiType = PROTOTYPE
	} else {
		swagger.apiType = HTTP
	}
	swagger.xWso2Basepath = swagger2.BasePath
	// According to the definition, multiple schemes can be mentioned. Since the microgateway can assign only one scheme
	// https is prioritized over http. If it is ws or wss, the microgateway will print an error.
	// If the schemes property is not mentioned at all, http will be assigned. (Only swagger 2 version has this property)
	// For prototyped APIs, the prototype endpoint is only assinged from api.Yaml. Hence,
	// an exception is made where host property is not processed when the API is prototyped.
	if swagger2.Host != "" && !swagger.IsProtoTyped {
		urlScheme := ""
		for _, scheme := range swagger2.Schemes {
			//TODO: (VirajSalaka) Introduce Constants
			if scheme == "https" {
				urlScheme = "https://"
				break
			} else if scheme == "http" {
				urlScheme = "http://"
			} else {
				//TODO: (VirajSalaka) Throw an error and stop processing
				logger.LoggerOasparser.Errorf("The scheme : %v for the swagger definition %v:%v is not supported", scheme,
					swagger2.Info.Title, swagger2.Info.Version)
			}
		}
		endpoint, err := getHostandBasepathandPort(urlScheme + swagger2.Host + swagger2.BasePath)
		if err == nil {
			productionEndpoints := append([]Endpoint{}, *endpoint)
			swagger.productionEndpoints = generateEndpointCluster(prodClustersConfigNamePrefix, productionEndpoints, LoadBalance)
			swagger.sandboxEndpoints = nil
		} else {
			return errors.New("error encountered when parsing the endpoint")
		}
	}
	return nil
}

// setResourcesSwagger sets swagger (openapi v2) paths as mgwSwagger resources.
func setResourcesSwagger(swagger2 spec.Swagger, mgwSwagger *MgwSwagger) []*Resource {
	var resources []*Resource
	// Check if the "x-wso2-disable-security" vendor ext is present at the API level.
	// If API level vendor ext is present, then the same key:value should be added to
	// resourve level, if it's not present at resource level using "addResourceLevelDisableSecurity"
	if swagger2.Paths != nil {
		for path, pathItem := range swagger2.Paths.Paths {
			disableSecurity, found := swagger2.VendorExtensible.Extensions.GetBool(xWso2DisableSecurity)
			// Checks for resource level security, if security is disabled in resource level,
			// below code segment will override above two variable values (disableSecurity & found)
			disableResourceLevelSecurity, foundInResourceLevel := pathItem.Extensions.GetBool(xWso2DisableSecurity)
			if foundInResourceLevel {
				logger.LoggerOasparser.Infof("x-wso2-disable-security extension is available in the API: %v %v's resource %v.",
					swagger2.Info.Title, swagger2.Info.Version, path)
				disableSecurity = disableResourceLevelSecurity
				found = true
			}
			var methodsArray []*Operation
			methodFound := false
			var prototypeConfig PrototypeConfig = PrototypeConfig{}
			var methodName string
			if pathItem.Get != nil {
				methodName = "GET"
				if found {
					addResourceLevelDisableSecurity(&pathItem.Get.VendorExtensible, disableSecurity)
				}
				if (mgwSwagger.IsProtoTyped) {
					xMediationScriptValue ,_ := pathItem.Get.VendorExtensible.Extensions.GetString(xMediationScript)
					getPrototypeConfig(xMediationScriptValue, &prototypeConfig, methodName)
				}
				methodsArray = append(methodsArray, NewOperation(methodName, pathItem.Get.Security,
					pathItem.Get.Extensions, prototypeConfig))
				methodFound = true
			}
			if pathItem.Post != nil {
				methodName = "POST"
				if found {
					addResourceLevelDisableSecurity(&pathItem.Post.VendorExtensible, disableSecurity)
				}
				if (mgwSwagger.IsProtoTyped) {
					xMediationScriptValue ,_ := pathItem.Post.VendorExtensible.Extensions.GetString(xMediationScript)
					getPrototypeConfig(xMediationScriptValue, &prototypeConfig, methodName)
				}
				methodsArray = append(methodsArray, NewOperation(methodName, pathItem.Post.Security,
					pathItem.Post.Extensions, prototypeConfig))
				methodFound = true
			}
			if pathItem.Put != nil {
				methodName = "PUT"
				if found {
					addResourceLevelDisableSecurity(&pathItem.Put.VendorExtensible, disableSecurity)
				}
				if (mgwSwagger.IsProtoTyped) {
					xMediationScriptValue ,_ := pathItem.Put.VendorExtensible.Extensions.GetString(xMediationScript)
					getPrototypeConfig(xMediationScriptValue, &prototypeConfig, methodName)
				}
				methodsArray = append(methodsArray, NewOperation(methodName, pathItem.Put.Security,
					pathItem.Put.Extensions, prototypeConfig))
				methodFound = true
			}
			if pathItem.Delete != nil {
				methodName = "DELETE"
				if found {
					addResourceLevelDisableSecurity(&pathItem.Delete.VendorExtensible, disableSecurity)
				}
				if (mgwSwagger.IsProtoTyped) {
					xMediationScriptValue ,_ := pathItem.Delete.VendorExtensible.Extensions.GetString(xMediationScript)
					getPrototypeConfig(xMediationScriptValue, &prototypeConfig, methodName)
				}
				methodsArray = append(methodsArray, NewOperation(methodName, pathItem.Delete.Security,
					pathItem.Delete.Extensions, prototypeConfig))
				methodFound = true
			}
			if pathItem.Head != nil {
				methodName = "HEAD"
				if found {
					addResourceLevelDisableSecurity(&pathItem.Head.VendorExtensible, disableSecurity)
				}
				if (mgwSwagger.IsProtoTyped) {
					xMediationScriptValue ,_ := pathItem.Head.VendorExtensible.Extensions.GetString(xMediationScript)
					getPrototypeConfig(xMediationScriptValue, &prototypeConfig, methodName)
				}
				methodsArray = append(methodsArray, NewOperation(methodName, pathItem.Head.Security,
					pathItem.Head.Extensions, prototypeConfig))
				methodFound = true
			}
			if pathItem.Patch != nil {
				methodName = "PATCH"
				if found {
					addResourceLevelDisableSecurity(&pathItem.Patch.VendorExtensible, disableSecurity)
				}
				if (mgwSwagger.IsProtoTyped) {
					xMediationScriptValue ,_ := pathItem.Patch.VendorExtensible.Extensions.GetString(xMediationScript)
					getPrototypeConfig(xMediationScriptValue, &prototypeConfig, methodName)
				}
				methodsArray = append(methodsArray, NewOperation(methodName, pathItem.Patch.Security,
					pathItem.Patch.Extensions, prototypeConfig))
				methodFound = true
			}
			if pathItem.Options != nil {
				methodName = "OPTION"
				if found {
					addResourceLevelDisableSecurity(&pathItem.Options.VendorExtensible, disableSecurity)
				}
				if (mgwSwagger.IsProtoTyped) {
					xMediationScriptValue ,_ := pathItem.Options.VendorExtensible.Extensions.GetString(xMediationScript)
					getPrototypeConfig(xMediationScriptValue, &prototypeConfig, methodName)
				}
				methodsArray = append(methodsArray, NewOperation(methodName, pathItem.Options.Security,
					pathItem.Options.Extensions, prototypeConfig))
				methodFound = true
			}
			if methodFound {
				resource := setOperationSwagger(path, methodsArray, pathItem)
				resources = append(resources, &resource)
			}
		}
	}
	return SortResources(resources)
}

// Sets security definitions defined in swagger 2 format.
func setSecurityDefinitions(swagger2 spec.Swagger) []SecurityScheme {
	var securitySchemes []SecurityScheme

	for key, val := range swagger2.SecurityDefinitions {
		scheme := SecurityScheme{DefinitionName: key, Type: val.Type, Name: val.Name, In: val.In}
		securitySchemes = append(securitySchemes, scheme)
	}
	logger.LoggerOasparser.Debugf("Security schemes in setSecurityDefinitions  %v:", securitySchemes)
	return securitySchemes
}

// This methods adds x-wso2-disable-security vendor extension
// if it's not present in the given vendor extensions.
func addResourceLevelDisableSecurity(v *spec.VendorExtensible, enable bool) {
	if _, found := v.Extensions.GetBool(xWso2DisableSecurity); !found {
		v.AddExtension(xWso2DisableSecurity, enable)
	}
}

func setOperationSwagger(path string, methods []*Operation, pathItem spec.PathItem) Resource {
	return Resource{
		path:    path,
		methods: methods,
		// TODO: (VirajSalaka) This will not solve the actual problem when incremental Xds is introduced (used for cluster names)
		iD: uuid.New().String(),
		// PathItem object in swagger 2 specification does not contain summary and description properties
		summary:     "",
		description: "",
		//schemes:          operation.Schemes,
		//tags:             operation.Tags,
		//security:         operation.Security,
		vendorExtensions: pathItem.VendorExtensible.Extensions,
	}
}
