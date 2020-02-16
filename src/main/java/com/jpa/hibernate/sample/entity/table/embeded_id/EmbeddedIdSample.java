package com.jpa.hibernate.sample.entity.table.embeded_id;

import com.jpa.hibernate.sample.entity.table.embeded_id.MyEmbeddedId;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class EmbeddedIdSample {
    @EmbeddedId
    private MyEmbeddedId id;
}

