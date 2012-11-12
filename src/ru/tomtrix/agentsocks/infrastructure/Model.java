package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/** fd
 * @author tom-trix */
public class Model implements IAgentProcessible
{
	/** csdfse */
	@SuppressWarnings("unused")
	private final String				_name;
	/** sfee */
	private final Map<Integer, Node>	_nodes	= new TreeMap<>();

	/** fse
	 * @param name */
	public Model(@JsonProperty(value = "_name") String name)
	{
		_name = name;
	}

	/** @return */
	public int addNode()
	{
		int result = _nodes.size();
		_nodes.put(result, new Node(result));
		return result;
	}

	/** fsefe */
	public void deleteLastNode()
	{
		_nodes.remove(_nodes.size() - 1);
	}

	/** @param num
	 * @return */
	public Node getNodeByNumber(int num)
	{
		if (!_nodes.containsKey(num)) throw new NullPointerException(String.format("There is no such a node (%d)", num));
		return _nodes.get(num);
	}

	@Override
	public void loadCode() throws Exception
	{
		for (Node node : _nodes.values())
			node.get_container().loadCode();
	}

	@Override
	public void compileAgents() throws Exception
	{
		for (Node node : _nodes.values())
			node.get_container().compileAgents();
	}
}
