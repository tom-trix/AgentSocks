package ru.tomtrix.agentsocks.utils;

import java.io.*;
import java.util.Iterator;

import ru.tomtrix.agentsocks.Control;
import ru.tomtrix.agentsocks.mathmodel.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/** @author tom-trix */
public class AgentJsonSerializer
{
	private static AgentJsonSerializer	_instance	= new AgentJsonSerializer();

	public static AgentJsonSerializer getInstance()
	{
		return _instance;
	}

	private AgentJsonSerializer()
	{}

	/** feses */
	private final ObjectMapper	_mapper	= new ObjectMapper();

	/** @param agent
	 * @param filename
	 * @throws IOException */
	public void agentToFile(Agent agent, String filename) throws IOException
	{
		// get a file
		File f = new File(filename);
		if (!f.exists()) f.createNewFile();

		// serialize
		Control.CONSTRUCTOR_ACCESS_DENIED = false;
		_mapper.writeValue(f, agent);
		Control.CONSTRUCTOR_ACCESS_DENIED = true;
	}

	/** @param filename
	 * @return
	 * @throws Exception */
	public Agent fileToAgent(String filename) throws Exception
	{
		// get a file
		File f = new File(filename);
		if (!f.exists()) throw new FileNotFoundException("fdsfsdr");
		JsonNode root = _mapper.readTree(f);
		Agent result = new Agent(root.path("_name").textValue());
		for (Iterator<JsonNode> i = root.path("_state").elements(); i.hasNext();)
			result.addVariable(i.next().textValue());
		for (Iterator<JsonNode> i = root.path("_transformFunctions").elements(); i.hasNext();)
			result.addFunction(i.next().textValue());
		return result;
	}
}
