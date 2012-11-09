package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tom-trix
 *
 */
public class Model
{
	@SuppressWarnings("unused")
	private final String _name;
	private final Map<Integer, Node> _nodes = new TreeMap<>();
	
	public Model(@JsonProperty(value="_name") String name)
	{
		_name = name;
	}
	
	public int addNode()
	{
		int result = _nodes.size();
		_nodes.put(result, new Node(result));
		return result;
	}
	
	public void deleteLastNode()
	{
		_nodes.remove(_nodes.size()-1);
	}
	
	public Node getNodeByNumber(int num)
	{
		if (!_nodes.containsKey(num)) throw new NullPointerException("jyfjfh");
		return _nodes.get(num);
	}
}
