package ballerina.swagger.interceptor;

import ballerina.lang.system;
import ballerina.lang.messages;

function main (string[] args) {
    system:println("Hello, World!");
}

function requestInterceptor (message m) (boolean, message) {
    system:println("received message");
    // TODO : validation on url
    json swaggerDef = generateSwagger();
    if (swaggerDef != null) {
        message response = {};
        messages:setJsonPayload(response, swaggerDef);
        return false, response;
    } else {
        return true, m;
    }
}

native function generateSwagger () (json);