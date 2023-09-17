package ru.ifmo.soa.killer.client;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.ifmo.soa.killer.model.Dragon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
public class RestServiceClient {

    private static final String propertiesPath = "services.properties";
    private final String baseUrl;

    public RestServiceClient(){
        Properties properties = readProperties();
        baseUrl = properties.getProperty("rest-service.base-url");
    }

    public Dragon getById(Long dragonId) throws ClientError{
        String url = baseUrl + String.format("/api/dragons/%s/", dragonId);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();

                String responseBody = EntityUtils.toString(entity);
                XmlMapper mapper = new XmlMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(responseBody, Dragon.class);


            }
        } catch (Exception e) {
            throw new ClientError();
        }
    }

    private Properties readProperties(){

        Properties props = new Properties();
        try {
            InputStream resourceStream = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(propertiesPath);
            props.load(resourceStream);
        }
        catch (IOException ex){
            throw new RuntimeException(ex);
        }
        return props;
    }

}
