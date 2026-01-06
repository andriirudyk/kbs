package com.rudyk.catalog.domain.model;


import javax.persistence.*;
import java.util.*;

@Entity
public class ModifierGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SelectionType selectionType;

    @ManyToMany
    @JoinTable(
            name = "modifiergroup_option",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private List<ModifierOption> options = new ArrayList<>();




    protected ModifierGroup() { }
    public ModifierGroup(String name, SelectionType selectionType) {
        this.name = name;
        this.selectionType = selectionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModifierOption> getOptions() {
        return options;
    }

    public void setOptions(List<ModifierOption> options) {
        this.options = options;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }
}
