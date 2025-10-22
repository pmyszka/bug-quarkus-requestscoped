package org.example.model;

import org.immutables.value.Value;

@Value.Immutable
public interface BucketSettingsEntry {

  Bucket bucket();

  String key();

  String value();
}
