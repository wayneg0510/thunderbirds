package io.wayneg.thunderbirds.io.sender;

import io.wayneg.thunderbirds.io.utils.ClassUtilsEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@Slf4j
public abstract class RestClient {

    protected static final String defaultUserAgent;
    protected HttpMethod httpMethod;

    protected static final WebClient webClient;

    static {
        log.info("start to initialize RestClient......");
        defaultUserAgent = generateUserAgentByManifestInfo();
        webClient = initializeWebClient();
        log.info("RestClient initialize finished");
    }

    public static WebClient getWebClient() { return webClient; }

    private static String generateUserAgentByManifestInfo() {
        String serviceName = null;
        String serviceVersion = null;

        List<Manifest> serviceManifestList = ClassUtilsEx.getManifestListOfSpringBootJar();
        for(Manifest m : serviceManifestList) {
            if(m == null) {
                continue;
            }

            Attributes attributes = m.getMainAttributes();
            serviceName = attributes.getValue("Service-ArtifactId");
            serviceVersion = attributes.getValue("Service-Version");
            if(StringUtils.hasText(serviceName) && StringUtils.hasText(serviceVersion)) {
                break;
            }
        }

        if(!StringUtils.hasText(serviceName)) {
            Manifest clientManifest = ClassUtilsEx.getManifestOfSpringBootLib(RestClient.class);
            if(clientManifest != null) {
                Attributes attributes = clientManifest.getMainAttributes();
                serviceName = Objects.toString(attributes.getValue("Thurderbirds-IO-ArtifactId"), "thunderbirds-io");
                serviceVersion = Objects.toString(attributes.getValue("Thunderbirds-IO-Version"), "*");
            }
        }

        if(!StringUtils.hasText(serviceName)) {
            return "thunderbirds-io/*";
        }

        String userAgentStr = serviceName + "/" + serviceVersion;
        log.info("'{}' as default user agent", userAgentStr);
        return userAgentStr;
    }

    private static WebClient initializeWebClient() {
        WebClient webClient = WebClient.builder()
                .build();

        return webClient;
    }
}
