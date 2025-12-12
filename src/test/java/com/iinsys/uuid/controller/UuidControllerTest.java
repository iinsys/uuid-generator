package com.iinsys.uuid.controller;

import com.iinsys.uuid.service.UuidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UuidController.class)
class UuidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UuidService uuidService;

    @Test
    void generateUuid_ShouldReturnUuid() throws Exception {
        String testUuid = "550e8400-e29b-41d4-a716-446655440000";
        when(uuidService.generateUuid()).thenReturn(testUuid);

        mockMvc.perform(get("/api/uuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(testUuid));
    }

    @Test
    void generateBulkUuids_ShouldReturnMultipleUuids() throws Exception {
        List<String> testUuids = List.of(
                "550e8400-e29b-41d4-a716-446655440000",
                "6ba7b810-9dad-11d1-80b4-00c04fd430c8"
        );
        when(uuidService.generateBulkUuids(2)).thenReturn(testUuids);

        mockMvc.perform(get("/api/uuid/bulk").param("count", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuids").isArray())
                .andExpect(jsonPath("$.uuids.length()").value(2))
                .andExpect(jsonPath("$.count").value(2));
    }

    @Test
    void generateBulkUuids_WithInvalidCount_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/uuid/bulk").param("count", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void generateUuidWithTimestamp_ShouldReturnUuidWithTimestamp() throws Exception {
        String testUuid = "550e8400-e29b-41d4-a716-446655440000";
        String testTimestamp = "2025-12-12T10:30:00.000Z";
        UuidService.UuidWithTimestamp result = new UuidService.UuidWithTimestamp(testUuid, testTimestamp);
        when(uuidService.generateUuidWithTimestamp()).thenReturn(result);

        mockMvc.perform(get("/api/uuid/timestamp"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(testUuid))
                .andExpect(jsonPath("$.generatedAt").value(testTimestamp));
    }
}
