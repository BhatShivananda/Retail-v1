package com.app.func.tools

import com.app.func.base.ContentType
import groovyx.net.http.ChainedHttpConfig
import groovyx.net.http.FromServer
import groovyx.net.http.NativeHandlers

import java.util.function.BiFunction

class ResponseParser<T, OUT extends HttpResponse<T>> implements BiFunction<ChainedHttpConfig, FromServer, OUT> {

    @Override
    HttpResponse<T> apply(ChainedHttpConfig chainedHttpConfig, FromServer fromServer) {

        Object responseBody

        if (fromServer.getContentType() == ContentType.APPLICATION_JSON) {
            responseBody = NativeHandlers.Parsers.json(chainedHttpConfig, fromServer)
        } else {
            // Handle Text/html response
            responseBody = NativeHandlers.Parsers.textToString(chainedHttpConfig, fromServer)
        }

        return new HttpResponse<T>(statusCode: fromServer.statusCode,
                responseBody: responseBody,
                cookies: fromServer.cookies,
                contentType: fromServer.getContentType())
    }

}
