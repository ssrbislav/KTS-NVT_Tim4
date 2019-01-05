package com.sbvtransport.sbvtransport.service;


import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Null;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class LineServiceTest {

    @Autowired
    private ILineService lineService;

    @Test
    public void findAllTest() {
        List<Line> lines = lineService.findAll();
        assertThat(lines).hasSize(3);
    }

    @Test
    public void getOneTest() {
        Line line = lineService.getOne(3L);

        assertThat(line).isNotNull();
        assertThat(line.getId()).isEqualTo(3L);
        assertThat(line.getName()).isEqualTo("nova_linija3");
        assertThat(line.getLine_type()).isEqualTo(TypeTransport.trolley);
    }

    @Test
    public void getNOneTest() {
        Line line = lineService.getOne(10L);
        assertThat(line).isNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createTest() {
        LineDTO lineDTO = new LineDTO("bus", "nova_linija4");
        int dbSizeBefore = lineService.findAll().size();
        String success = lineService.create(lineDTO);
        assertThat(success).isEqualTo("The line has been successfully created!");
        assertThat(dbSizeBefore).isEqualTo(lineService.findAll().size() - 1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest() {
        int dbSizeBefore = lineService.findAll().size();
        boolean success = lineService.delete(3L);
        assertThat(success).isTrue();
        assertThat(dbSizeBefore).isEqualTo(lineService.findAll().size() + 1);
        try {
            Line loc = lineService.getOne(3L);
            assertThat(loc).isNull();
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest2() {
        int dbSizeBefore = lineService.findAll().size();
        boolean success = lineService.delete(33L);
        assertThat(success).isFalse();
        assertThat(dbSizeBefore).isEqualTo(lineService.findAll().size());
    }

}
