package com.iinsys.uuid.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for UUID with timestamp.
 */
@Schema(description = "Response containing a UUID with generation timestamp")
public record UuidWithTimestampResponse(
        @Schema(description = "The generated UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String uuid,

        @Schema(description = "Timestamp when the UUID was generated", example = "2025-12-12T10:30:00.000Z")
        String generatedAt
) {}
