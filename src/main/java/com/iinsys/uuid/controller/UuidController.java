package com.iinsys.uuid.controller;

import com.iinsys.uuid.dto.BulkUuidResponse;
import com.iinsys.uuid.dto.UuidResponse;
import com.iinsys.uuid.dto.UuidWithTimestampResponse;
import com.iinsys.uuid.service.UuidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for UUID generation endpoints.
 */
@RestController
@RequestMapping("/api/uuid")
@Tag(name = "UUID Generator", description = "Endpoints for generating UUIDs")
public class UuidController {

    private final UuidService uuidService;

    public UuidController(UuidService uuidService) {
        this.uuidService = uuidService;
    }

    /**
     * Generates a single UUID.
     *
     * @return Response containing the generated UUID
     */
    @GetMapping
    @Operation(
            summary = "Generate a single UUID",
            description = "Generates and returns a single random UUID (version 4)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UUID generated successfully")
    })
    public ResponseEntity<UuidResponse> generateUuid() {
        String uuid = uuidService.generateUuid();
        return ResponseEntity.ok(new UuidResponse(uuid));
    }

    /**
     * Generates multiple UUIDs.
     *
     * @param count The number of UUIDs to generate (default: 10, max: 1000)
     * @return Response containing the list of generated UUIDs
     */
    @GetMapping("/bulk")
    @Operation(
            summary = "Generate multiple UUIDs",
            description = "Generates and returns multiple random UUIDs (version 4)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UUIDs generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid count parameter")
    })
    public ResponseEntity<BulkUuidResponse> generateBulkUuids(
            @Parameter(description = "Number of UUIDs to generate (1-1000)", example = "10")
            @RequestParam(defaultValue = "10") int count
    ) {
        if (count < 1 || count > 1000) {
            throw new IllegalArgumentException("Count must be between 1 and 1000");
        }
        List<String> uuids = uuidService.generateBulkUuids(count);
        return ResponseEntity.ok(new BulkUuidResponse(uuids, uuids.size()));
    }

    /**
     * Generates a UUID with timestamp.
     *
     * @return Response containing the UUID and generation timestamp
     */
    @GetMapping("/timestamp")
    @Operation(
            summary = "Generate UUID with timestamp",
            description = "Generates a UUID and returns it with the generation timestamp"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UUID with timestamp generated successfully")
    })
    public ResponseEntity<UuidWithTimestampResponse> generateUuidWithTimestamp() {
        UuidService.UuidWithTimestamp result = uuidService.generateUuidWithTimestamp();
        return ResponseEntity.ok(new UuidWithTimestampResponse(result.uuid(), result.generatedAt()));
    }
}
