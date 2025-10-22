package org.example.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.dynamodb.BucketSettings;
import org.example.dynamodb.DdbBatchHandler;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@ApplicationScoped
public class PutService {

  private final DdbBatchHandler handler;

  public PutService(DynamoDbEnhancedAsyncClient ddbClient) {
    handler =
        new DdbBatchHandler(
            ddbClient,
            ddbClient.table(
                "bucket-settings", TableSchema.fromImmutableClass(BucketSettings.class)));
  }

  public Uni<Void> run(Iterable<BucketSettings> items) {
    return Uni.join().all(handler.putBatch(items)).andFailFast().replaceWithVoid();
  }
}
