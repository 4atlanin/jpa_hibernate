package com.jpa.hibernate.sample.table.embedded.id;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class EmbeddedIdSample {
    @EmbeddedId
    private MyEmbeddedId id;
}

