package com.app.func.tools

import groovyx.net.http.ChainedHttpConfig
import groovyx.net.http.FromServer

import java.util.function.BiFunction

/**
 * When registered as a parser for a content-type, it will return only the status code from
 * the responseBody.
 */
class StatusParser implements BiFunction<ChainedHttpConfig, FromServer, Integer> {

    @Override
    Integer apply(ChainedHttpConfig chainedHttpConfig, FromServer fromServer) {
        return Integer.valueOf(fromServer.statusCode)
    }
}
