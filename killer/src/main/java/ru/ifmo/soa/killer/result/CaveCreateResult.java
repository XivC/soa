package ru.ifmo.soa.killer.result;

import lombok.*;
import ru.ifmo.soa.killer.model.Cave;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaveCreateResult extends ServiceResult {
    Cave cave;
}
