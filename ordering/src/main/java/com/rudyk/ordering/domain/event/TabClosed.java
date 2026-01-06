package com.rudyk.ordering.domain.event;


import com.rudyk.ordering.domain.vo.TabId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TabClosed implements Serializable {
    private final TabId tabId;
    private final BigDecimal total;
    private final LocalDateTime closedAt;

    public TabClosed(TabId tabId, BigDecimal total, LocalDateTime closedAt) {
        this.tabId = Objects.requireNonNull(tabId);
        this.total = Objects.requireNonNull(total);
        this.closedAt = Objects.requireNonNull(closedAt);
    }

    public TabId getTabId() { return tabId; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getClosedAt() { return closedAt; }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof TabClosed
                && tabId.equals(((TabClosed) o).tabId)
                && total.compareTo(((TabClosed) o).total) == 0
                && closedAt.equals(((TabClosed) o).closedAt));
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabId, total.stripTrailingZeros(), closedAt);
    }

    @Override
    public String toString() {
        return "TabClosed{" +
                "tabId=" + tabId +
                ", total=" + total +
                ", closedAt=" + closedAt +
                '}';
    }
}