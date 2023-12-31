package ru.ifmo.soa.killer.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Team {
    private Long id;
    private Cave startCave;
    private Integer size;
    private String name;

    public Long getStartCaveId(){
        if (this.startCave != null) return this.startCave.getId();
        return null;
    }

}