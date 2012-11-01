package ru.tomtrix.agentsocks.ui;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;
import ru.tomtrix.agentsocks.utils.AgentJsonSerializer;

/**
 * @author tom-trix
 *
 */
public class MVCmodel
{
	/**
	 * 
	 */
	private final Container _container = new Container(1, "MainContainer");
	/**
	 * 
	 */
	private final LogicProcess _process = new LogicProcess("fuck");
	
	/**
	 * 
	 */
	public MVCmodel()
	{
		_container.addLogicProcess(_process);
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String createAgent(String name) throws Exception
	{
		_process.addAgent(new DefaultAgent(name));
		return String.format("Agent \"%s\" added successfully", name);
	}
	
	/**
	 * @param variable
	 * @param initialValue
	 * @return
	 */
	public String addVariable(String code)
	{
		try
		{
			Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
			agent.addVariable(code);
			StringBuilder sb = new StringBuilder(String.format("Now agent \"%s\" contains the following variables: ", agent.get_name()));
			for (String s : agent.get_state())
				sb.append("\n" + s);
			return sb.toString();
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Can't add new variable", e);
			return e.toString();
		}
	}
	
	/**
	 * @param code
	 * @return
	 */
	public String addFunction(String code)
	{
		try
		{
			System.out.println(code);
			Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
			agent.addFunction(code);
			StringBuilder sb = new StringBuilder(String.format("Now agent \"%s\" contains the following functions: ", agent.get_name()));
			for (String s : agent.get_transformFunctions())
				sb.append("\n" + s);
			return sb.toString();
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Can't add new function", e);
			return e.toString();
		}
	}
	
	/**
	 * @param fid
	 * @param timestamp
	 * @return
	 */
	public String addEvent(String fid, String timestamp)
	{
		try
		{
			Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
			agent.get_eventList().addEvent(Double.parseDouble(timestamp), fid);
			StringBuffer sb = new StringBuffer(String.format("Done...\nNow agent \"%s\" contains event list with timestamps: ", agent.get_name()));
			for (Entry<Double, String> e : agent.get_eventList().getInfo().entrySet())
				sb.append(e + ", ");
			return sb.toString();
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Can't add new event", e);
			return e.toString();
		}
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void saveAgent() throws IOException
	{
		Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
		AgentJsonSerializer.getInstance().agentToFile(agent, "/home/tom-trix/agent.txt");
	}
	
	/**
	 * @throws Exception
	 */
	public void loadAgent() throws Exception
	{
		_process.get_agents().clear();
		_process.addAgent(AgentJsonSerializer.getInstance().fileToAgent("/home/tom-trix/agent.txt"));
	}
	
	public String getFullAgentInfo()
	{
		Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
		StringBuffer sb = new StringBuffer(String.format("Agent Info:\n\nName: %s\nVariables:", agent.get_name()));
		for (String s : agent.get_state())
			sb.append("\n").append(s);
		sb.append("\nFunctions:");
		for (String s : agent.get_transformFunctions())
			sb.append("\n").append(s);
		sb.append("\nEvents:");
		for (Entry<Double, String> e : agent.get_eventList().getInfo().entrySet())
			sb.append("\n").append(e);
		return sb.toString();
	}
	
	/**
	 * @throws Exception
	 */
	public void run() throws Exception
	{
		Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
		agent.compileAgent();
		_container.run();
	}
	
	public void stop()
	{
		_container.stop();
	}
}
