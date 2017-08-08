/**
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **/

package org.ballerinalang.swagger.nativeimpl;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeEnum;
import org.ballerinalang.model.values.BJSON;
import org.ballerinalang.model.values.BMessage;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.nativeimpl.lang.utils.ErrorHandler;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.Attribute;
import org.ballerinalang.natives.annotations.BallerinaAnnotation;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.swagger.code.generator.BallerinaToSwaggerGenerator;
import org.wso2.carbon.messaging.MessageDataSource;

/**
 *  Generates the swagger definition for the current service.
 */
@BallerinaFunction(
        packageName = "ballerina.swagger.interceptor",
        functionName = "generateSwagger",
        returnType = {@ReturnType(type = TypeEnum.JSON)})
@BallerinaAnnotation(annotationName = "Description", attributes = {@Attribute(name = "value",
                                                value = "Generates the swagger definition for the current service") })
@BallerinaAnnotation(annotationName = "Return", attributes = {@Attribute(name = "json",
                                                                        value = "Swagger definition of the service.") })
public class GenerateSwagger extends AbstractNativeFunction {
    
    private static final String OPERATION = "generate swagger";
    
    @Override
    public BValue[] execute(Context ctx) {
        BJSON result = null;
        try {
            if (null != ctx.getServiceInfo()) {
                String swaggerDefAsString = BallerinaToSwaggerGenerator.generateSwagger(ctx.getServiceInfo());
                result = new BJSON(swaggerDefAsString);
            } else {
                result = null;
            }
        } catch (Throwable e) {
            ErrorHandler.handleJsonException(OPERATION, e);
        }
        return getBValues(result);
    }
}
