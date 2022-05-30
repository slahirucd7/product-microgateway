#!/bin/sh
# --------------------------------------------------------------------
# Copyright (c) 2022, WSO2 Inc. (http://wso2.com) All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# -----------------------------------------------------------------------
echo "Creating test suit for latest API-M image."
cp ../test-integration/src/test/resources/testng-cc-with-apim.xml ../test-integration/src/test/resources/testng-cc-with-latest-apim.xml
echo "Changing test suit name."
sed -i 's/Choreo-Connect-With-APIM-Test-Suite/Choreo-Connect-With-Latest-APIM-Test-Suite/' ../test-integration/src/test/resources/testng-cc-with-latest-apim.xml
echo "Inserting API-M image name determining parameter."
sed -i '/Choreo-Connect-With-Latest-APIM-Test-Suite/a  \\t<parameter name=\"apimImageName\" value=\"latestApimFromSupport\"\/>' ../test-integration/src/test/resources/testng-cc-with-latest-apim.xml
echo "Successfully created test suit for latest API-M image."
