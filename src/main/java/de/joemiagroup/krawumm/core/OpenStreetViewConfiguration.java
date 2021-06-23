package de.joemiagroup.krawumm.core;


import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.NominatimOptions;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class OpenStreetViewConfiguration {
    @Bean
    public JsonNominatimClient nominatimClient(@Value("nominatim.signing.mail") String nominatimSigningMail) {
        final CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactory.getSocketFactory())
                .build();

        final NominatimOptions options = new NominatimOptions();
        options.setAcceptLanguage(Locale.getDefault());
        options.setBounded(true);
        return new JsonNominatimClient(httpClient, nominatimSigningMail, options);
    }
}
