package com.rudyk.ordering.api.dto.dto;




import java.util.List;

/**
 * Response DTO for returning ticket data to clients.
 */

public class TicketResponse {
    public Long id;
    public List<TicketItemResponse> items;
    public String status;
    public String stationOrAssignee;
    public String createdAt;
    public String startedAt;
    public String completedAt;
    public Integer lastDelayMinutes;
    public String lastDelayReason;

    public static class TicketItemResponse {
        public String itemCode;
        public String displayName;
        public int quantity;
    }
}
