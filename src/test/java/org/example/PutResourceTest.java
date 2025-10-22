package org.example;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.example.dynamodb.BucketSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@QuarkusTest
public class PutResourceTest {

  @Inject DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

  @BeforeEach
  public void setupTable() {
    try {
      dynamoDbEnhancedAsyncClient
          .table("bucket-settings", TableSchema.fromImmutableClass(BucketSettings.class))
          .createTable()
          .get();
    } catch (Exception ignored) {
    }
  }

  @Test
  public void testPutEndpoint() throws IOException {
    given()
        .contentType(ContentType.JSON)
        .body(
            Files.readString(
                Path.of("src/test/resources/put_settings_request.json"), StandardCharsets.UTF_8))
        .header("bucket", "bucket_1")
        .when()
        .put("/settings")
        .then()
        .statusCode(204);
  }
}
