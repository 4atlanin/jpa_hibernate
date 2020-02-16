package com.jpa.hibernate.sample.id;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.entity.table.embeded_id.EmbeddedIdSample;
import com.jpa.hibernate.sample.entity.table.embeded_id.MyEmbeddedId;
import com.jpa.hibernate.sample.repository.table.EmbeddedIdRepository;
import com.jpa.hibernate.sample.repository.table.IdClassSampleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Random;

public class IdTests extends JpaHibernateBaseTest {
    @Autowired
    private EmbeddedIdRepository embeddedIdRepository;

    @Autowired
    private IdClassSampleRepository idClassSampleRepository;

    private Random random = new Random();

    @Test
    public void testEmbeddedId() {
        EmbeddedIdSample eis = getEmbeddedIdSample();
        EmbeddedIdSample eis2 = getEmbeddedIdSample();
        embeddedIdRepository.save(eis);
        embeddedIdRepository.save(eis2);

        embeddedIdRepository.findAll().forEach(System.out::println);
    }

    private EmbeddedIdSample getEmbeddedIdSample() {
        EmbeddedIdSample eis = new EmbeddedIdSample();
        MyEmbeddedId mei = new MyEmbeddedId();
        mei.setPartOne(random.nextInt());
        mei.setPartTwo(random.nextInt());
        eis.setId(mei);

        return eis;
    }
}
