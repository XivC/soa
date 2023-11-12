package ru.ifmo.soa.killer.result;


import lombok.*;
import ru.ifmo.soa.killer.model.Dragon;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DragonKillResult extends ServiceResult {
    Dragon dragon;
}