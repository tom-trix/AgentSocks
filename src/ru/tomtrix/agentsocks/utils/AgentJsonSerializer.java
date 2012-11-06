package ru.tomtrix.agentsocks.utils;

import java.io.*;
import ru.tomtrix.agentsocks.Control;
import ru.tomtrix.agentsocks.mathmodel.*;
import ru.tomtrix.javassistwraper.ClassStore;

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
		
		// deserialize
		Control.CONSTRUCTOR_ACCESS_DENIED = false;
		Agent result = _mapper.readValue(f, DefaultAgent.class);
		Control.CONSTRUCTOR_ACCESS_DENIED = true;
		for (String s : result.get_state())
			ClassStore.getInstance().addField(result.get_RAClassname(), s);
		for (String s : result.get_transformFunctions())
			ClassStore.getInstance().addMethod(result.get_RAClassname(), s);
		
		//well done
		return result;
	}
}
