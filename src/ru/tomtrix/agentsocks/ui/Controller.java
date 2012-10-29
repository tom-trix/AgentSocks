package ru.tomtrix.agentsocks.ui;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;

/**
 * @author tom-trix
 *
 */
public class Controller
{
	private final Container _container = new Container(1, "MainContainer");
	private final LogicProcess _process = new LogicProcess("fuck");
	
	public Controller()
	{
		_container.addLogicProcess(_process);
	}
	
	public String createAgent(String name)
	{
		_process.addAgent(new DefaultAgent(name));
		return String.format("Agent \"%s\" added successfully", name);
	}
	
	public String addVariable(String variable, Object initialValue)
	{
		try
		{
			Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
			agent.get_state().addStateItem(variable, StateItemAccess.PUBLIC, initialValue);
			StringBuffer sb = new StringBuffer(String.format("Done...\nNow agent \"%s\" contains variables: ", agent.get_name()));
			for (String s : agent.get_state().getStateItems())
				sb.append(s + ", ");
			return sb.substring(0, sb.length()-2);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Can't add new variable", e);
			return e.toString();
		}
	}
	
	public String addFunction(String fid, String name, String code, Class<?> ... argTypes)
	{
		try
		{
			System.out.println(code);
			Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
			agent.get_transformFunctions().addNewMethod(fid, code, name, argTypes);
			StringBuffer sb = new StringBuffer(String.format("Done...\nNow agent \"%s\" contains functions: ", agent.get_name()));
			for (String s : agent.get_transformFunctions().getAllFids())
				sb.append(s + ", ");
			return sb.substring(0, sb.length()-2);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("Can't add new function", e);
			return e.toString();
		}
	}
	
	public String addEvent(String fid, String timestamp)
	{
		try
		{
			Agent agent = _process.get_agents().get(_process.get_agents().size()-1);
			agent.get_eventList().addEvent(Double.parseDouble(timestamp), fid, agent);
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
	
	public void run()
	{
		_container.run();
	}
}
