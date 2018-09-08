package com.app.func.tools

class HttpResponse<T> {
    int statusCode
    T responseBody
    List<HttpCookie> cookies
    String contentType
}
