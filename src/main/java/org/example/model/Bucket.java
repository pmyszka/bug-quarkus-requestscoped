package org.example.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Bucket {
  String value();
}
