/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org).
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

package org.wso2.choreo.connect.enforcer.commons.logging;

import net.minidev.json.JSONObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.message.ParameterizedMessage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * This will implement a custom JSON type layout for support JSON format logging
 */
@Plugin(name = "CustomJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class CustomJsonLayout extends AbstractStringLayout {
    protected CustomJsonLayout(Charset charset) {
        super(charset);
    }

    @Override
    public String toSerializable(LogEvent event) {
        StringBuilder throwable = new StringBuilder();
        if (event.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            event.getThrown().printStackTrace(pw);
            pw.close();
            throwable.append(sw.toString());
        }
        JSONObject obj = new JSONObject();
        StringBuilder retValue = new StringBuilder();
        obj.put("timestamp", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:S").format(event.getTimeMillis()));
        obj.put("level", event.getLevel().toString());
        obj.put("logger", event.getLoggerName());
        obj.put("message", event.getMessage().getFormattedMessage());
        if (event.getClass() == Log4jLogEvent.class) {
            Log4jLogEvent logEvent = (Log4jLogEvent) event;
            if (event.getLevel() == Level.ERROR) {
                if (logEvent.getMessage().getClass() == ParameterizedMessage.class) {
                    Object[] parameters = ((ParameterizedMessage) logEvent.getMessage()).getParameters();
                    if (Arrays.stream(parameters).anyMatch(p ->
                            p.getClass().getName().equals(ErrorLog.class.getName()))) {
                        Arrays.stream(parameters)
                                .filter(p -> p.getClass().getName().equals(ErrorLog.class.getName())).forEach((c) -> {
                                    ErrorLog errorLog = (ErrorLog) c;
                                    obj.put("severity", errorLog.getSeverity());
                                    obj.put("code", errorLog.getCode());
                                });
                    } else {
                        obj.put("severity", LoggingConstants.Severity.DEFAULT);
                        obj.put("code", 0);
                    }
                } else {
                    obj.put("severity", LoggingConstants.Severity.DEFAULT);
                    obj.put("code", 0);
                }
            }
        }
        retValue.append(obj.toString()).append(throwable).append("\n");
        return retValue.toString();
    }

    @PluginFactory
    public static CustomJsonLayout createLayout(
            @PluginAttribute(value = "charset", defaultString = "UTF-8") Charset charset) {
        return new CustomJsonLayout(charset);
    }
}
