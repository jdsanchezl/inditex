package com.inditex;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.inditex.domain.model.Prices;

@SpringBootTest
public class InditexApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@DisplayName("Obtener todas las Peticiones")
	public void testGetAllPrices() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd-hh.mm.ss");

		Prices prices = new Prices(1L, new Timestamp(formatter.parse("2020-06-15-16.00.00").getTime()),
				new Timestamp(formatter.parse("2020-12-31-23.59.59").getTime()), 4, 35455L, 1, new BigDecimal("38.95"),
				"EUR");

		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();

		List<Prices> founds = mapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<Prices>>() {
				});
		assertTrue(founds.stream().anyMatch(found -> found.getPrice().compareTo(prices.getPrice()) == 0));

	}

	@Test
	@DisplayName("Petición a las 10:00 del día 14 del producto 35455 para brand 1 (ZARA)")
	public void testGetPriceCase1() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/prices/2020-06-14-10.00.00/35455/1")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-13 22:00:00"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-12-31 22:59:59"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
				.andExpect(MockMvcResultMatchers.jsonPath("$.curr").value("EUR"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Petición a las 16:00 del día 14 del producto 35455 para brand 1 (ZARA)")
	public void testGetPriceCase2() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/prices/2020-06-14-16.00.00/35455/1")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-14 13:00:00"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-06-14 16:30:00"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
				.andExpect(MockMvcResultMatchers.jsonPath("$.curr").value("EUR"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(25.45)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Petición a las 21:00 del día 14 del producto 35455 para brand 1 (ZARA)")
	public void testGetPriceCase3() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/prices/2020-06-14-21.00.00/35455/1")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-13 22:00:00"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-12-31 22:59:59"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
				.andExpect(MockMvcResultMatchers.jsonPath("$.curr").value("EUR"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Petición a las 10:00 del día 15 del producto 35455 para brand 1 (ZARA)")
	public void testGetPriceCase4() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/prices/2020-06-15-10.00.00/35455/1")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-14 22:00:00"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-06-15 09:00:00"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
				.andExpect(MockMvcResultMatchers.jsonPath("$.curr").value("EUR"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30.50)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Petición a las 21:00 del día 16 del producto 35455 para brand 1 (ZARA)")
	public void testGetPriceCase5() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/prices/2020-06-16-21.00.00/35455/1")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-15 14:00:00"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-12-31 22:59:59"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
				.andExpect(MockMvcResultMatchers.jsonPath("$.curr").value("EUR"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(4))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(38.95)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Salvar nuevo precio")
	public void testPostPriceCase6() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd-hh.mm.ss");

		Prices prices = new Prices(1L, new Timestamp(formatter.parse("2023-11-23-16.00.00").getTime()),
				new Timestamp(formatter.parse("2023-12-31-23.59.59").getTime()), 4, 35455L, 1, new BigDecimal("60"),
				"EUR");
		
		ObjectMapper mapperPost = new ObjectMapper();
		ObjectWriter ow = mapperPost.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(prices);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/prices/").content(requestJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andReturn();

		ObjectMapper mapperReturn = new ObjectMapper();

		Prices found = mapperReturn.readValue(result.getResponse().getContentAsString(), new TypeReference<Prices>() {
		});
		assertNotNull(found.getPricesId());
		assertEquals(new BigDecimal("60"), found.getPrice());
	}
}
