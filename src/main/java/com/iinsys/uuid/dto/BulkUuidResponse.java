package com.iinsys.uuid.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Response DTO for bulk UUID generation.
 */
@Schema(description = "Response containing multiple generated UUIDs")
public record BulkUuidResponse(
        @Schema(description = "List of generated UUIDs")
        List<String> uuids,

        @Schema(description = "Number of UUIDs generated", example = "10")
        int count
) {}
