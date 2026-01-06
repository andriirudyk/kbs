package com.rudyk.kitchenbar.api.dto;



import com.rudyk.kitchenbar.domain.model.Ticket;
import com.rudyk.kitchenbar.domain.vo.MenuItem;


import java.util.List;
import java.util.stream.Collectors;

/**
 * Response DTO for returning ticket data to clients.
 */
public class TicketResponse {

    private String id;
    private List<String> items;
    private String status;
    private String stationOrAssignee;
    private String createdAt;
    private String startedAt;
    private String completedAt;
    private Integer lastDelayMinutes;
    private String lastDelayReason;

    public static TicketResponse from(Ticket t) {
        TicketResponse dto = new TicketResponse();
        dto.id = t.getId().toString();
        dto.items = t.getItems().stream().map(MenuItem::toString).collect(Collectors.toList());
        dto.status = t.getStatus().name();
        dto.stationOrAssignee = t.getStationOrAssignee();
        dto.createdAt = t.getCreatedAt() != null ? t.getCreatedAt().toString() : null;
        dto.startedAt = t.getStartedAt() != null ? t.getStartedAt().toString() : null;
        dto.completedAt = t.getCompletedAt() != null ? t.getCompletedAt().toString() : null;
        dto.lastDelayMinutes = t.getLastDelayMinutes();
        dto.lastDelayReason = t.getLastDelayReason();
        return dto;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStationOrAssignee() { return stationOrAssignee; }
    public void setStationOrAssignee(String stationOrAssignee) { this.stationOrAssignee = stationOrAssignee; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getStartedAt() { return startedAt; }
    public void setStartedAt(String startedAt) { this.startedAt = startedAt; }

    public String getCompletedAt() { return completedAt; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }

    public Integer getLastDelayMinutes() { return lastDelayMinutes; }
    public void setLastDelayMinutes(Integer lastDelayMinutes) { this.lastDelayMinutes = lastDelayMinutes; }

    public String getLastDelayReason() { return lastDelayReason; }
    public void setLastDelayReason(String lastDelayReason) { this.lastDelayReason = lastDelayReason; }
}
