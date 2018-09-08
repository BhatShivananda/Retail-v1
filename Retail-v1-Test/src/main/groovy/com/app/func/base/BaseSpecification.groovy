package com.app.func.base

import com.app.func.tools.HttpResponse
import com.app.func.tools.ResponseParser
import groovy.util.logging.Slf4j
import groovyx.net.http.FromServer
import groovyx.net.http.HttpBuilder
import spock.lang.Specification

@Slf4j
class BaseSpecification extends Specification {

    HttpBuilder createConnection(String contentType) {

        HttpBuilder.configure {
            request.uri = "http://localhost:8080"
            request.accept = contentType
            request.contentType = contentType

            response.parser(contentType, new ResponseParser<Status, HttpResponse>())

            // this trick is needed when there is no response body
            response.success { FromServer fromServer, responseBody ->
                if (responseBody) {
                    responseBody
                } else {
                    new HttpResponse<Map>(statusCode: fromServer.statusCode, responseBody: [:])
                }
            }

            response.failure { FromServer fromServer, responseBody ->
                if (responseBody) {
                    responseBody
                } else {
                    new HttpResponse<Map>(statusCode: fromServer.statusCode, responseBody: [:])
                }
            }
        }
    }

    static class Status {
        String status
    }
}
