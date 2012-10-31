package ru.tomtrix.agentsocks.ui;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;

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
			for (String s : agent.getVariables())
				sb.append(s + ", ");
			return sb.substring(0, sb.length()-2);
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
			for (String s : agent.getFunctions())
				sb.append(s + ", ");
			return sb.substring(0, sb.length()-2);
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
			for (Double d : agent.get_eventList().getAllTimestamps())
				sb.append(d + ", ");
			return sb.substring(0, sb.length()-2);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Can't add new event", e);
			return e.toString();
		}
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
