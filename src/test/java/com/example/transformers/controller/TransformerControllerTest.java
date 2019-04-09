package com.example.transformers.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.model.BattleResult;
import com.example.model.Transformer;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransformerController.class)
public class TransformerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	TransformerController transformerController;

	Transformer transformer = new Transformer("12345","Starscream", "D", 5, 1, 8, 2, 2, 4, 5, 6);

	@Test
	public void addTransformer() throws Exception {
		Mockito.when(transformerController.addTransformer(Mockito.any(Transformer.class)))
				.thenReturn("Transformer details added successfully for ::Starscream");

		MvcResult result_0 = mockMvc.perform(MockMvcRequestBuilders.post("/create-transformer")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\n" + "        \"transformerName\": \"Starscream\",\n"
						+ "        \"transformerType\": \"D\",\n" + "        \"strength\": 1,\n"
						+ "        \"intelligence\": 1,\n" + "        \"speed\": 8,\n" + "        \"endurance\": 2,\n"
						+ "        \"rank\": 2,\n" + "        \"courage\": 4,\n" + "        \"firepower\": 5,\n"
						+ "        \"skill\": 6\n" + "    }")
				.accept(MediaType.ALL)).andReturn();

		MockHttpServletResponse response_0 = result_0.getResponse();

		assertEquals(HttpStatus.OK.value(), response_0.getStatus());

		assertEquals("Transformer details added successfully for ::Starscream",
				result_0.getResponse().getContentAsString());
		
		//Add another transformer
		Mockito.when(transformerController.addTransformer(Mockito.any(Transformer.class)))
		.thenReturn("Transformer details added successfully for ::Bumblebee");
		MvcResult result_1 = mockMvc.perform(MockMvcRequestBuilders.post("/create-transformer")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\n" + "        \"transformerName\": \"Bumblebee\",\n"
						+ "        \"transformerType\": \"D\",\n" + "        \"strength\": 1,\n"
						+ "        \"intelligence\": 2,\n" + "        \"speed\": 9,\n" + "        \"endurance\": 2,\n"
						+ "        \"rank\": 2,\n" + "        \"courage\": 4,\n" + "        \"firepower\": 8,\n"
						+ "        \"skill\": 6\n" + "    }")
				.accept(MediaType.ALL)).andReturn();
		
		MockHttpServletResponse response_1 = result_1.getResponse();

		assertEquals(HttpStatus.OK.value(), response_1.getStatus());

		assertEquals("Transformer details added successfully for ::Bumblebee",
				result_1.getResponse().getContentAsString());
		
		
		//Add another transformer
				Mockito.when(transformerController.addTransformer(Mockito.any(Transformer.class)))
				.thenReturn("Transformer details added successfully for ::Optimus Prime");
				MvcResult result_2 = mockMvc.perform(MockMvcRequestBuilders.post("/create-transformer")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content("{\n" + "        \"transformerName\": \"Optimus Prime\",\n"
								+ "        \"transformerType\": \"D\",\n" + "        \"strength\": 1,\n"
								+ "        \"intelligence\": 2,\n" + "        \"speed\": 9,\n" + "        \"endurance\": 2,\n"
								+ "        \"rank\": 2,\n" + "        \"courage\": 4,\n" + "        \"firepower\": 8,\n"
								+ "        \"skill\": 6\n" + "    }")
						.accept(MediaType.ALL)).andReturn();
				
				MockHttpServletResponse response_2 = result_2.getResponse();

				assertEquals(HttpStatus.OK.value(), response_2.getStatus());

				assertEquals("Transformer details added successfully for ::Optimus Prime",
						result_2.getResponse().getContentAsString());
	}

	@Test
	public void updateTransformer() throws Exception {
		Mockito.when(transformerController.updateTransformer(Mockito.any(Transformer.class)))
				.thenReturn("Update successful");

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put("/update-transformer")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content("{\n" + "        \"transformerName\": \"Starscream\",\n"
								+ "        \"transformerType\": \"D\",\n" + "        \"strength\": 2,\n"
								+ "        \"intelligence\": 15,\n" + "        \"speed\": 1,\n"
								+ "        \"endurance\": 3,\n" + "        \"rank\": 1,\n" + "        \"courage\": 4,\n"
								+ "        \"firepower\": 5,\n" + "        \"skill\": 6\n" + "}")
						.accept(MediaType.ALL))
						.andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		assertEquals("Update successful", result.getResponse().getContentAsString());
	}

	@Test
	public void deleteTransformer() throws Exception {
		Mockito.when(transformerController.deleteTransformer(Mockito.anyString())).thenReturn("Delete successful");

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete("/delete-transformer/Starscream")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.ALL))
						.andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		assertEquals("Delete successful", result.getResponse().getContentAsString());
	}

	@Test
	public void getAllTransformers() throws Exception {

		List<Transformer> transformerList = new ArrayList<Transformer>();
		transformerList.add(transformer);

		Mockito.when(transformerController.getAllTransformers()).thenReturn(transformerList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/list-transformers").accept(MediaType.ALL);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"transformerId\":\"12345\",\"transformerName\":\"Starscream\",\"transformerType\":\"D\",\"strength\":5,\"intelligence\":1,\"speed\":8,\"endurance\":2,\"rank\":2,\"courage\":4,\"firepower\":5,\"skill\":6}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}
	
	
	@Test
	public void battleTransformer() throws Exception {
		
		
		  BattleResult battleResult = new BattleResult();
		  List<String> survivors=  new ArrayList<String>();
		  List<String> winners=  new ArrayList<String>();
		  winners.add("Bumblebee");
		  
		  battleResult.setNumberOfBattles(1);
		  battleResult.setSurvivors(survivors);
		  battleResult.setWinners(winners);
		  battleResult.setWinningTeam("Autobots");
		  
		  Mockito.when(transformerController.battleTransformer(Mockito.anyMap())).
		  thenReturn(battleResult);
		 
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/battle-transformer")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\n" + 
						"	\"names\" : [\"Bumblebee\",\"Starscream\"]\n" + 
						"}")
				.accept(MediaType.ALL)).andReturn();

		String expected = "{\n" + 
				"    \"numberOfBattles\": 1,\n" + 
				"    \"winningTeam\": \"Autobots\",\n" + 
				"    \"winners\": [Bumblebee],\n" + 
				"    \"survivors\": []\n" + 
				"}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}
	
	
	/* Special case of battling with Optimus Prime */
	@Test
	public void battleTransformerWithOptimus() throws Exception {
		
		
		  BattleResult battleResult = new BattleResult();
		  List<String> survivors=  new ArrayList<String>();
		  List<String> winners=  new ArrayList<String>();
		  winners.add("Optimus Prime");
		  
		  battleResult.setNumberOfBattles(1);
		  battleResult.setSurvivors(survivors);
		  battleResult.setWinners(winners);
		  battleResult.setWinningTeam("Autobots");
		  
		  Mockito.when(transformerController.battleTransformer(Mockito.anyMap())).
		  thenReturn(battleResult);
		 
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/battle-transformer")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\n" + 
						"	\"names\" : [\"Optimus Prime\",\"Starscream\"]\n" + 
						"}")
				.accept(MediaType.ALL)).andReturn();

		String expected = "{\n" + 
				"    \"numberOfBattles\": 1,\n" + 
				"    \"winningTeam\": \"Autobots\",\n" + 
				"    \"winners\": [\"Optimus Prime\"],\n" + 
				"    \"survivors\": []\n" + 
				"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		

	}

}

