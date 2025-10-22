package org.example.dynamodb;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.MappedTableResource;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

public class DdbBatchHandler {

  private static final int MAX_BATCH_SIZE = 5;

  private final DynamoDbEnhancedAsyncClient ddbClient;
  private final MappedTableResource<BucketSettings> table;

  public DdbBatchHandler(
      DynamoDbEnhancedAsyncClient ddbClient, MappedTableResource<BucketSettings> table) {
    this.ddbClient = ddbClient;
    this.table = table;
  }

  public Uni<Void> putBatch(Iterable<BucketSettings> batch) {
    return Multi.createFrom()
        .iterable(batch)
        .group()
        .intoLists()
        .of(MAX_BATCH_SIZE)
        .onItem()
        .transformToUniAndConcatenate(this::processSubBatch)
        .toUni();
  }

  private Uni<Void> processSubBatch(List<BucketSettings> subBatch) {
    return Uni.createFrom()
        .completionStage(
            ddbClient.batchWriteItem(builder -> builder.addWriteBatch(createWriteBatch(subBatch))))
        .replaceWithVoid();
  }

  private WriteBatch createWriteBatch(Iterable<BucketSettings> batch) {
    var batchBuilder = WriteBatch.builder(BucketSettings.class).mappedTableResource(table);
    batch.forEach(batchBuilder::addPutItem);
    return batchBuilder.build();
  }
}
