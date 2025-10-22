package org.example.app;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import org.example.model.Bucket;
import org.example.model.ImmutableBucket;

public class Producers {

  @RequestScoped
  @Produces
  Bucket bucket(HttpHeaders headers) {
    return ImmutableBucket.builder().value(headers.getRequestHeader("bucket").getFirst()).build();
  }
}
