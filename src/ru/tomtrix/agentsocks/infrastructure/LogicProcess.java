package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;
import ru.tomtrix.agentsocks.mathmodel.Agent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author tom-trix */
public class LogicProcess implements IAgentProcessible
{
	/** efssef */
	private String				_name;
	/** dwaawd */
	private final List<Agent>	_agents	= new ArrayList<>();

	/** fsvggrdokpo
	 * @param name */
	LogicProcess(@JsonProperty("_name") String name)
	{
		if (name == null || name.trim().isEmpty()) throw new NullPointerException("Logic process must have a name");
		_name = name;
	}

	/** @return
	 * @throws Exception */
	void nextStep() throws Exception
	{
		if (_agents.isEmpty()) return;
		// find the minimum of the event timestamps
		Agent currentAgent = null;
		for (Agent agent : _agents)
		{
			Double time = agent.getNextEventTime();
			if (time == null) continue;
			if (currentAgent == null || time < currentAgent.getNextEventTime()) currentAgent = agent;
		}
		if (currentAgent == null) return;
		// handle the event
		currentAgent.executeNextEvent();
	}

	/** fes
	 * @param agent */
	public void addAgent(Agent agent)
	{
		if (agent == null) throw new IllegalArgumentException("Agent can't be equal to null");
		_agents.add(agent);
	}

	/** sefef
	 * @param name
	 * @return */
	public Agent getAgentByName(String name)
	{
		if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Agent can't be equal to null");
		for (Agent agent : _agents)
			if (agent.get_name().toLowerCase().trim().equals(name.toLowerCase().trim())) return agent;
		return null;
	}

	/** sefgrs
	 * @param num
	 * @return */
	public Agent getAgentByNumber(int num)
	{
		if (num < 0 || num >= _agents.size()) throw new ArrayIndexOutOfBoundsException(String.format("Wrong number argument: %d", num));
		return _agents.get(num);
	}

	/** sge
	 * @param agent */
	public void removeAgent(Agent agent)
	{
		if (agent == null || !_agents.contains(agent)) throw new NullPointerException("There is no such an agent!");
		_agents.remove(agent);
	}

	@Override
	public void loadCode() throws Exception
	{
		for (IAgentProcessible agent : _agents)
			agent.loadCode();
	}

	@Override
	public void compileAgents() throws Exception
	{
		for (IAgentProcessible agent : _agents)
			agent.compileAgents();
	}

	@Override
	public String toString()
	{
		StringBuffer sbuf = new StringBuffer(String.format("Process \"%s\". Logic process possesses the following agents:\n", _name));
		if (_agents.size() == 0) sbuf.append("<no processes>\n");
		for (Agent agent : _agents)
			sbuf.append(agent);
		return sbuf.toString();
	}

	/** @param _name the _name to set */
	public void set_name(String name)
	{
		if (name == null || name.trim().isEmpty()) throw new NullPointerException();
		_name = name;
	}

	/** @return the _name */
	public String get_name()
	{
		return _name;
	}
}
