package com.jitterted.mobreg.adapter.out.jdbc;

import com.jitterted.mobreg.domain.Huddle;
import com.jitterted.mobreg.domain.MemberService;
import com.jitterted.mobreg.domain.Participant;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DataJdbcHuddleRepositoryAdapterTest {

    @Autowired
    DataJdbcHuddleRepositoryAdapter huddleRepositoryAdapter;

    @MockBean
    GrantedAuthoritiesMapper grantedAuthoritiesMapper;

    @MockBean
    MemberService memberService;

    @Test
    public void savedHuddleCanBeFoundByItsId() throws Exception {
        Huddle huddle = createHuddleNamed("test huddle");

        Huddle savedHuddle = huddleRepositoryAdapter.save(huddle);

        Optional<Huddle> found = huddleRepositoryAdapter.findById(savedHuddle.getId());

        assertThat(found)
                .isPresent()
                .get()
                .extracting(Huddle::name)
                .isEqualTo("test huddle");
    }

    @Test
    public void newRepositoryReturnsEmptyForFindAll() throws Exception {
        List<Huddle> huddles = huddleRepositoryAdapter.findAll();

        assertThat(huddles)
                .isEmpty();
    }

    @Test
    public void twoSavedHuddlesBothReturnedByFindAll() throws Exception {
        Huddle one = createHuddleNamed("one");
        Huddle two = createHuddleNamed("two");

        huddleRepositoryAdapter.save(one);
        huddleRepositoryAdapter.save(two);

        assertThat(huddleRepositoryAdapter.findAll())
                .hasSize(2);
    }

    @NotNull
    private Huddle createHuddleNamed(String huddleName) {
        Huddle huddle = new Huddle(huddleName, ZonedDateTime.now());
        huddle.register(
                new Participant(
                        "test participant", "github", "email", "discord", false));
        return huddle;
    }
}