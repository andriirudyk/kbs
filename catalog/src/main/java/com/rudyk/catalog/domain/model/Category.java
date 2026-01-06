package com.rudyk.catalog.domain.model;



import javax.persistence.*;
import java.util.Map;

@Entity
public class Category {
    @Id
    @Column(name = "code", length = 32, nullable = false)
    private String code;

    @Column(nullable = false)
    private String displayName;

    protected Category() { }
    public Category(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
