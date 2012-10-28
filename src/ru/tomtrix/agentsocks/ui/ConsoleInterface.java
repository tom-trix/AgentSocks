package ru.tomtrix.agentsocks.ui;

import java.util.*;
import java.io.IOException;
import org.apache.log4j.Logger;
import ru.tomtrix.consoleui.ConsoleUI;
import ru.tomtrix.agentsocks.labirint.Man;
import ru.tomtrix.agentsocks.labirint.Tester;
import ru.tomtrix.agentsocks.mathmodel.*;

/**
 * @author tom-trix
 *
 */
public class ConsoleInterface
{
	private final List<Agent> _agents = new ArrayList<>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			ConsoleUI cui = new ConsoleUI("/home/tom-trix/1.txt", new ConsoleInterface());
			cui.run();
		}
		catch (IOException e)
		{
			Logger.getLogger(ConsoleInterface.class).error("Error during reading the file", e);
		}
	}
	
	public String createAgent(String name)
	{
		_agents.add(new DefaultAgent(name));
		return String.format("Agent \"%s\" added successfully", name);
	}
	
	public String addVariable(String variable, Object initialValue)
	{
		try
		{
			Agent agent = _agents.get(_agents.size()-1);
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
	
	public String addFunction(String fid, String name, String code)
	{
		try
		{
			System.out.println(code);
			Agent agent = _agents.get(_agents.size()-1);
			agent.get_transformFunctions().addNewMethod(fid, code, name);
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
			Agent agent = _agents.get(_agents.size()-1);
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
	
	public void run()
	{
		Container container = new Container(1, "MainContainer");
		LogicProcess process = new LogicProcess("fuck");
		process.addAgents(_agents);
		container.addLogicProcess(process);
	}
}
