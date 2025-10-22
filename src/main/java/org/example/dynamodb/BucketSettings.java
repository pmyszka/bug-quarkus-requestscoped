package org.example.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbImmutable(builder = BucketSettings.Builder.class)
public record BucketSettings(
    @DynamoDbPartitionKey String bucket, @DynamoDbSortKey String key, String value) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String bucket;
    private String key;
    private String value;

    public Builder bucket(String bucket) {
      this.bucket = bucket;
      return this;
    }

    public Builder key(String key) {
      this.key = key;
      return this;
    }

    public Builder value(String value) {
      this.value = value;
      return this;
    }

    public BucketSettings build() {
      return new BucketSettings(bucket, key, value);
    }
  }
}
