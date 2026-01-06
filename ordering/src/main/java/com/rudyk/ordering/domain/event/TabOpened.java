package com.rudyk.ordering.domain.event;





import com.rudyk.ordering.domain.vo.TabId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TabOpened implements Serializable {
    private final TabId tabId;
    private final int tableNumber;
    private final LocalDateTime openedAt;

    public TabOpened(TabId tabId, int tableNumber, LocalDateTime openedAt) {
        this.tabId = Objects.requireNonNull(tabId);
        this.tableNumber = tableNumber;
        this.openedAt = Objects.requireNonNull(openedAt);
    }

    public TabId getTabId() { return tabId; }
    public int getTableNumber() { return tableNumber; }
    public LocalDateTime getOpenedAt() { return openedAt; }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof TabOpened
                && tabId.equals(((TabOpened) o).tabId)
                && tableNumber == ((TabOpened) o).tableNumber
                && openedAt.equals(((TabOpened) o).openedAt));
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabId, tableNumber, openedAt);
    }

    @Override
    public String toString() {
        return "TabOpened{" +
                "tabId=" + tabId +
                ", tableNumber=" + tableNumber +
                ", openedAt=" + openedAt +
                '}';
    }
}