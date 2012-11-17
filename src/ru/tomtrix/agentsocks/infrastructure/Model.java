package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;
import java.util.Map.Entry;
import com.fasterxml.jackson.annotation.JsonProperty;

/** fd
 * @author tom-trix */
public class Model implements IAgentProcessible
{
	/** csdfse */
	private final String				_name;
	/** sfee */
	private final Map<Integer, Node>	_nodes	= new TreeMap<>();
	private final boolean _isDebug;

	/** fse
	 * @param name */
	public Model(@JsonProperty(value = "_name") String name)
	{
		if (name == null || name.trim().isEmpty()) throw new NullPointerException("Model name can't be empty!");
		_name = name;
		_isDebug = name.trim().toLowerCase().equals("debug");
	}

	/** @return */
	public int addNode()
	{
		int result = _nodes.size();
		_nodes.put(result, new Node(result, _isDebug));
		return result;
	}

	/** fsefe */
	public void deleteLastNode()
	{
		_nodes.remove(_nodes.size() - 1);
	}

	/** @return sbsei */
	public int getNodesCount()
	{
		return _nodes.size();
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

	@Override
	public String toString()
	{
		StringBuffer sbuf = new StringBuffer(String.format("Model \"%s\". Node collection:\n", _name));
		if (_nodes.size() == 0) sbuf.append("<no nodes>\n");
		for (Entry<Integer, Node> node : _nodes.entrySet())
			sbuf.append(node);
		return sbuf.toString();
	}

	/** @return the _name */
	public String get_name()
	{
		return _name;
	}
}
