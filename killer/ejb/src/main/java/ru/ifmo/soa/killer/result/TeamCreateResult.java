package ru.ifmo.soa.killer.result;

import lombok.*;
import ru.ifmo.soa.killer.model.Team;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateResult extends ServiceResult {
    Team team;
}
