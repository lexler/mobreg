package com.jitterted.mobreg.adapter.in.web.admin;


import com.jitterted.mobreg.application.EnsembleService;
import com.jitterted.mobreg.application.port.EnsembleRepository;
import com.jitterted.mobreg.application.port.MemberRepository;
import com.jitterted.mobreg.application.port.Notifier;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberManagementController.class)
@Tag("mvc")
@WithMockUser(username = "tedyoung", authorities = {"ROLE_MEMBER","ROLE_ADMIN"})
public class AdminMembershipEndpointTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    EnsembleRepository ensembleRepository;

    @MockBean
    Notifier notifier;

    @MockBean
    GrantedAuthoritiesMapper grantedAuthoritiesMapper;

    @MockBean
    EnsembleService ensembleService;

    @Test
    public void getOfMemberAdminPageIsStatus200Ok() throws Exception {
        mockMvc.perform(get("/admin/members"))
               .andExpect(status().isOk());
    }

    @Test
    public void postToAddMemberRedirects() throws Exception {
        mockMvc.perform(post("/admin/add-member")
                                .with(csrf()))
               .andExpect(status().is3xxRedirection());
    }

}
