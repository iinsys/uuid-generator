package com.iinsys.uuid.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * Service class for generating UUIDs.
 */
@Service
public class UuidService {

    /**
     * Generates a single random UUID.
     *
     * @return A randomly generated UUID string
     */
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates multiple random UUIDs.
     *
     * @param count The number of UUIDs to generate
     * @return A list of randomly generated UUID strings
     */
    public List<String> generateBulkUuids(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> UUID.randomUUID().toString())
                .toList();
    }

    /**
     * Generates a UUID with timestamp metadata.
     *
     * @return UUID with generation timestamp
     */
    public UuidWithTimestamp generateUuidWithTimestamp() {
        return new UuidWithTimestamp(
                UUID.randomUUID().toString(),
                Instant.now().toString()
        );
    }

    /**
     * Record to hold UUID with timestamp information.
     */
    public record UuidWithTimestamp(String uuid, String generatedAt) {}
}
