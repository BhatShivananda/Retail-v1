package com.app.func.tests

import com.app.func.base.BaseSpecification
import com.app.func.base.ContentType
import com.app.func.tools.HttpResponse
import groovyx.net.http.HttpBuilder
import spock.lang.Stepwise

@Stepwise
class RetailAppTests extends BaseSpecification {

    static HttpBuilder httpBuilder

    def setup() {
        httpBuilder = createConnection(ContentType.APPLICATION_JSON)
    }


    def "create item price record  - 201 response"() {
        when: "I pass valid body and headers"
        HttpResponse<Status> createItemResponse = httpBuilder.post({
            request.uri.path = '/retail/v1/products'
            request.setBody("""
                     {
                      "tcin": "12345",
                      "price": "11"
                      }
                        """)

            //printing the base url to the report
            reportInfo('POST - ' + request.uri.parent.parent.scheme.toString() + '//' + request.uri.parent.parent.host.toString() + request.uri.path.toString())
        })

        then: "Status code is 201"
        reportInfo('STATUS -' + createItemResponse.statusCode)
        reportInfo(createItemResponse.responseBody.toString())
        createItemResponse.statusCode == HttpURLConnection.HTTP_CREATED

    }

    def "update item price record  - 200 response"() {
        when: "I pass valid body and headers"
        HttpResponse<Status> updateItemResponse = httpBuilder.put({
            request.uri.path = '/retail/v1/products/12345'
            request.setBody("""
                     {
                      "price": "11"
                      }
                        """)

            //printing the base url to the report
            reportInfo('PUT - ' + request.uri.parent.parent.scheme.toString() + '//' + request.uri.parent.parent.host.toString() + request.uri.path.toString())
        })

        then: "Status code is 200"
        reportInfo('STATUS -' + updateItemResponse.statusCode)
        reportInfo(updateItemResponse.responseBody.toString())
        updateItemResponse.statusCode == HttpURLConnection.HTTP_OK

    }


    def "get product details - 200 response"() {
        when: "I pass valid body and headers"
        HttpResponse<Status> productResponse = httpBuilder.get({
            request.uri.path = '/retail/v1/products/13860428'
            //printing the base url to the report
            reportInfo('GET - ' + request.uri.parent.parent.scheme.toString() + '//' + request.uri.parent.parent.host.toString() + request.uri.path.toString())
        })

        then: "Status code is 200"
        reportInfo('STATUS -' + productResponse.statusCode)
        reportInfo(productResponse.responseBody.toString())
        productResponse.statusCode == HttpURLConnection.HTTP_OK
        productResponse.getResponseBody().id == "13860428"
        productResponse.getResponseBody().current_price.value == "13.49"

    }

    def "delete item details - 200 response"() {
        when: "I pass valid body and headers"
        HttpResponse<Status> productResponse = httpBuilder.delete({
            request.uri.path = '/retail/v1/products/12345'
            //printing the base url to the report
            reportInfo('DELETE - ' + request.uri.parent.parent.scheme.toString() + '//' + request.uri.parent.parent.host.toString() + request.uri.path.toString())
        })

        then: "Status code is 200"
        reportInfo('STATUS -' + productResponse.statusCode)
        reportInfo(productResponse.responseBody.toString())
        productResponse.statusCode == HttpURLConnection.HTTP_OK

    }
}
