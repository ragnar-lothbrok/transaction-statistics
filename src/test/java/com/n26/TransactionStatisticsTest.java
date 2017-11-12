package com.n26;

import static org.junit.Assert.*;

import java.time.Instant;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.n26.transactions.dto.Transaction;
import com.n26.transactions.dto.TransactionStatistics;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TransactionStatisticsTest {

	private MockMvc mockMvc;

	private Gson gson = new Gson();

	@Resource
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testSaveTransaction1() throws Exception {
		Transaction tx = new Transaction();
		tx.setAmount(100);
		tx.setTimestamp(Instant.now().getEpochSecond());

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transactions")
				.contentType(MediaType.APPLICATION_JSON).content(gson.toJson(tx).getBytes("UTF-8"));

		MvcResult result = mockMvc.perform(builder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		if (HttpStatus.CREATED.value() == response.getStatus()) {
			builder = MockMvcRequestBuilders.get("/statistics");

			result = mockMvc.perform(builder).andReturn();

			response = result.getResponse();

			TransactionStatistics txStats = gson.fromJson(response.getContentAsString(), TransactionStatistics.class);

			assertEquals(tx.getAmount(), txStats.getMax(), 0);
		}

	}

}
