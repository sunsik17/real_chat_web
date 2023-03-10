package com.zerobase.real_time_chat.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.real_time_chat.user.domain.User;
import com.zerobase.real_time_chat.user.dto.RegisterUser;
import com.zerobase.real_time_chat.user.dto.UserInfo;
import com.zerobase.real_time_chat.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	private JpaMetamodelMappingContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	void registerSuccess() throws Exception {
		//given

		given(userService.registerUser(any()))
			.willReturn(UserInfo.fromEntity(User.builder()
				.username("ss")
				.password("1234")
				.userEmail("ss@ss")
				.phoneNumber("010")
				.build()));
		//when
		//then
		mockMvc.perform(post("/user/register")
				.with(csrf())
				.content(objectMapper.writeValueAsString(
					new RegisterUser.Request("ss@ss",
						"1234",
						"ss",
						"010")
				))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email").value("ss@ss"))
			.andExpect(jsonPath("$.name").value("ss"))
			.andExpect(jsonPath("$.phoneNumber").value("010"));
	}

}