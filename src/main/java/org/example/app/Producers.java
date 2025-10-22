package org.example.app;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import org.example.model.Bucket;

public class Producers {

  @RequestScoped
  @Produces
  Bucket bucket(HttpHeaders headers) {
    return new Bucket(headers.getRequestHeader("bucket").getFirst());
  }
}
