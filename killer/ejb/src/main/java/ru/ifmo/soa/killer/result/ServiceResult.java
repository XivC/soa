package ru.ifmo.soa.killer.result;


import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ServiceResult implements Serializable {
    protected List<String> errors = new ArrayList<>();

}