package com.iinsys.uuid.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for a single UUID.
 */
@Schema(description = "Response containing a generated UUID")
public record UuidResponse(
        @Schema(description = "The generated UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String uuid
) {}
