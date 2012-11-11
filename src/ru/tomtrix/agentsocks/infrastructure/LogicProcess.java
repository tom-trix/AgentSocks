package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import ru.tomtrix.agentsocks.mathmodel.Agent;

/** @author tom-trix */
public class LogicProcess implements ICodeLoadable
{
	/** efssef */
	private String				_name;
	/** dwaawd */
	private final List<Agent>	_agents			= new ArrayList<>();

	/** @param name */
	LogicProcess(@JsonProperty("_name") String name)
	{
		if (name == null || name.trim().isEmpty()) throw new NullPointerException("fsfgrq");
		_name = name;
	}

	/** @return
	 * @throws Exception */
	boolean nextStep() throws Exception
	{
		// TODO: можеть быть такое, что состояние агентов изменится, пока ищем минимум
		if (_agents.isEmpty()) return false;
		// find the minimum of the event timestamps
		Agent currentAgent = null;
		for (Agent agent : _agents)
		{
			Double time = agent.getNextEventTime();
			if (time == null) continue;
			if (currentAgent == null || time < currentAgent.getNextEventTime()) currentAgent = agent;
		}
		if (currentAgent == null) return false;
		// handle the event
		currentAgent.executeNextEvent();
		return true;
	}

	/** @param agent */
	public void addAgent(Agent agent)
	{
		if (agent == null) throw new IllegalArgumentException("Agent can't be equal to null");
		_agents.add(agent);
	}
	
	public Agent getAgentByName(String name)
	{
		if (name == null) throw new IllegalArgumentException("Agent can't be equal to null");
		for (Agent agent : _agents)
			if (agent.get_name().toLowerCase().trim().equals(name.toLowerCase().trim()))
				return agent;
		return null;
	}
	
	public Agent getAgentByNumber(int num)
	{
		if (num <0 || num >= _agents.size()) throw new ArrayIndexOutOfBoundsException("Agent can't be equal to null");
		return _agents.get(num);
	}
	
	public void removeAgent(Agent agent)
	{
		_agents.remove(agent);
	}
	
	@Override
	public void loadCode() throws Exception
	{
		for (ICodeLoadable agent : _agents)
			agent.loadCode();
	}

	@Override
	public void compileAgents() throws Exception
	{
		for (ICodeLoadable agent : _agents)
			agent.compileAgents();
	}

	/**
	 * @param _name the _name to set
	 */
	public void set_name(String name)
	{
		if (name == null || name.trim().isEmpty()) throw new NullPointerException("fsfgrq");
		_name = name;
	}

	/**
	 * @return the _name
	 */
	public String get_name()
	{
		return _name;
	}
}
