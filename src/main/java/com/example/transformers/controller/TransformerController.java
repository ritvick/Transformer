package com.example.transformers.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.BattleResult;
import com.example.model.Transformer;
import com.example.transformers.operations.TransformerOperations;

import io.swagger.annotations.ApiOperation;
import java.util.UUID;

@Controller
public class TransformerController {

	private static final Logger logger = LoggerFactory.getLogger(TransformerController.class);

	/* Method for adding or creating Transformer */
	@RequestMapping(method = RequestMethod.POST, value = "/create-transformer")
	@ResponseBody
	@ApiOperation("Adding a Transformer to the Application")
	public String addTransformer(@RequestBody Transformer transformer) {
		String uniqueId = UUID.randomUUID().toString(); 
		transformer.setTransformerId(uniqueId);
		logger.info("In Adding Transformer method");
		TransformerOperations.getInstance().add(transformer);

		return "Transformer details added successfully for ::" + transformer.getTransformerName();
	}

	/* Method mapping for listing all the transformers available */
	@RequestMapping(method = RequestMethod.GET, value = "/list-transformers")
	@ResponseBody
	@ApiOperation("Listing all the available Transformers")
	public List<Transformer> getAllTransformers() {
		return TransformerOperations.getInstance().getTransformerRecords();
	}

	/* Method for updating or modifying the transformer details */
	@RequestMapping(method = RequestMethod.PUT, value = "/update-transformer")
	@ResponseBody
	@ApiOperation("Updating a particular Transformer")
	public String updateTransformer(@RequestBody Transformer transformer) {
		logger.info("In update transformers method");
		return TransformerOperations.getInstance().updateTransformer(transformer);
	}

	/* Method for deleting the transformer details */
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete-transformer/{transformerName}")
	@ResponseBody
	@ApiOperation("Deleting a particular transformer")
	public String deleteTransformer(@PathVariable("transformerName") String transformerName) {
		logger.info("In delete transformer method");
		return TransformerOperations.getInstance().deleteTransformer(transformerName);
	}

	/* Method for deciding the winner in the battle */
	@RequestMapping(method = RequestMethod.POST, value = "/battle-transformer")
	@ResponseBody
	@ApiOperation("APi which decides the winning team and the survivors")
	public BattleResult battleTransformer(@RequestBody Map<String, List<String>> transformers) {
		logger.info("In battle transformer method");
		return TransformerOperations.getInstance().battleTransformer(transformers);
	}

}
