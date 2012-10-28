package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;

import org.apache.log4j.Logger;

/** @author tom-trix */
public class LogicProcess
{
	private static int					_currentName	= 0;
	private final String _name;
	private final List<Agent>	_agents	= new ArrayList<>();
	
	public LogicProcess(String name)
	{
		_name = name != null && !name.isEmpty() ? name : String.format("%s%d", getClass(), _currentName);
	}

	public void addAgent(Agent agent)
	{
		if (agent == null) throw new IllegalArgumentException("Agent can't be equal to null");
		_agents.add(agent);
	}
	
	public void addAgents(Collection<Agent> agents)
	{
		if (agents == null || agents.size() == 0) throw new IllegalArgumentException("Agent list must contain at least 1 element");
		_agents.addAll(agents);
	}

	public boolean nextStep() throws Exception
	{
		// TODO: можеть быть такое, что состояние агентов изменится, пока ищем минимум
		if (_agents.isEmpty()) return false;
		// find the minimum of the event timestamps
		Agent currentAgent = null;
		for (Agent agent : _agents)
		{
			Double time = agent.get_eventList().getNextEventTime();
			if (time == null) continue;
			if (currentAgent == null || time < currentAgent.get_eventList().getNextEventTime())
				currentAgent = agent;
		}
		if (currentAgent == null) return false;
		// handle the event
		currentAgent.get_eventList().executeNextEvent();
		Logger.getLogger(currentAgent.getClass()).debug("x = " + currentAgent._state.get("x"));
		return true;
	}

	/**
	 * @return the name
	 */
	public String get_name()
	{
		return _name;
	}
}
