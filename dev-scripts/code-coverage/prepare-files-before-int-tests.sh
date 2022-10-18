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

# Sets the Choreo Connect version
echo "Preparing code coverage files before integration tests..."
pwd
mkdir ../../resources/enforcer/dropins/
#cp ../../enforcer-parent/enforcer/target/coverage-aggregate-reports/aggregate.exec ../../resources/enforcer/dropins/
#chmod 777 ../../resources/enforcer/dropins/aggregate.exec
chmod 777 ../../enforcer-parent/enforcer/target/coverage-aggregate-reports/aggregate.exec
#JAVA_OPTS=-javaagent:/home/wso2/lib/org.jacoco.agent-0.8.8-runtime.jar=destfile=/home/wso2/lib/dropins/aggregate.exec,append=true
echo "Preparing code coverage files before integration tests completed successfully..."
