package ru.tomtrix.agentsocks.utils;

import java.io.*;
import ru.tomtrix.agentsocks.mathmodel.Agent;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author tom-trix
 *
 */
public class AgentJsonSerializer
{
	/**
	 * 
	 */
	private final ObjectMapper _mapper = new ObjectMapper();
	
	/**
	 * @param agent
	 * @param filename
	 * @throws IOException
	 */
	public void agentToFile(Agent agent, String filename) throws IOException
	{
		File f = new File(filename);
		if (!f.exists()) f.createNewFile();
		_mapper.writeValue(f, agent);
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public Agent fileToAgent(String filename) throws Exception
	{
		File f = new File(filename);
		if (!f.exists()) throw new FileNotFoundException("fdsfsdr");
		return _mapper.readValue(f, Agent.class);
	}
}
