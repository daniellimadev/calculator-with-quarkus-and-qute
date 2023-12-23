package com.github.daniellimadev.resource;

import com.github.daniellimadev.dto.ExternalTextDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://mocki.io/v1")
public interface RestHttpClient {

    @GET
    @Path("/236d6d03-e99a-4281-b84d-35a135571bca")
    public ExternalTextDTO fetchExternalData();

}
