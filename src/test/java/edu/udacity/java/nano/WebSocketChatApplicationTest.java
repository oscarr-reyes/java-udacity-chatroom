package edu.udacity.java.nano;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebSocketChatApplicationTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should_render_login() throws Exception {
		this.mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("/login"));
	}

	@Test
	public void should_redirect_to_login_if_username_is_undefined() throws Exception {
		this.mockMvc.perform(get("/index"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@Test
	public void should_render_chat_with_username() throws Exception {
		String username = "foo";

		this.mockMvc.perform(get("/index").param("username", username))
			.andExpect(status().isOk())
			.andExpect(view().name("chat"))
			.andExpect(model().attribute("username", username));
	}
}
