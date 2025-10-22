package org.example.web;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import org.example.dynamodb.BucketSettings;
import org.example.model.Bucket;
import org.example.service.PutService;

import java.util.Map;

@RequestScoped
@Path("/settings")
public class PutResource {

  private final Bucket bucket;
  private final PutService putService;

  public PutResource(Bucket bucket, PutService putService) {
    this.bucket = bucket;
    this.putService = putService;
  }

  @PUT
  public Uni<Void> put(Map<String, String> updates) {
    return putService.run(
        updates.entrySet().stream().map(entry -> this.convert(bucket, entry))::iterator);
  }

  private BucketSettings convert(Bucket bucket, Map.Entry<String, String> entry) {
    return BucketSettings.builder()
        .bucket(bucket.value())
        .key(entry.getKey())
        .value(entry.getValue())
        .build();
  }
}
