package io.wayneg.thunderbirds.io.receiver;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

public class RestResponse {

    public static Mono<ServerResponse> created(String requestId, String location) {

        return ServerResponse.created(URI.create(location))
                .header(RestHeaders.REQUEST_ID, requestId)
                .header(RestHeaders.RESPONSE_ID, UUID.randomUUID().toString())
                .header(RestHeaders.TIME, new Date().toString())
                .build();
    }

    public static <T> Mono<ServerResponse> ok(String requestId, T object) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(RestHeaders.REQUEST_ID, requestId)
                .header(RestHeaders.RESPONSE_ID, UUID.randomUUID().toString())
                .header(RestHeaders.TIME, new Date().toString())
                .body(BodyInserters.fromObject(object));
    }
}
