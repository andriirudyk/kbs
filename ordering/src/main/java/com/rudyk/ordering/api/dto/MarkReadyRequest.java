package com.rudyk.ordering.api.dto;



/**
 * Request payload for marking a ticket as READY.
 * Currently empty, but can hold additional data if required later
 * (e.g., who marked it ready, completion notes).
 */
public class MarkReadyRequest {

    private String confirmedBy;   // optional: staff username/ID
    private String note;          // optional: any comments

    public MarkReadyRequest() {
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}