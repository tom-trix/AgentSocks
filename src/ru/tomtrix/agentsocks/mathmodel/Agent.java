package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.message.*;
import com.fasterxml.jackson.annotation.*;
import ru.tomtrix.javassistwraper.ClassStore;
import javax.activation.UnsupportedDataTypeException;
import ru.tomtrix.agentsocks.infrastructure.IAgentProcessible;

/** fseefsfo
 * @author tom-trix */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Agent implements IAgentProcessible
{
	/** agent's name */
	private String				_name;
	/** fse */
	private final List<String>	_state				= new ArrayList<>();
	/** gsrrg */
	private final List<String>	_transformFunctions	= new ArrayList<>();
	/** list of events */
	private final EventList		_eventList			= new EventList();
	/** name of runtime class that corresponds to an agent */
	private final String		_RAClassname;
	/** runtime assistant */
	private Object				_runtimeAssistant;

	/** creates an agent with a specific name; it also builds runtime class that will comrise fields and methods of the agent
	 * @param name - agent's name (and corresponding runtime class's name)
	 * @throws Exception */
	Agent(String name) throws Exception
	{
		if (name == null || name.isEmpty()) throw new NullPointerException("Agent must have a name");
		_name = name;
		_RAClassname = name;
		ClassStore.getInstance().addClass(name, null, null);
		Logger.getLogger(getClass()).info(String.format("Agent \"%s\" created", name));
	}

	/** adds a variable to the agent by adding a new field to the corresponding runtime class
	 * @param code - Java code (e.g. "int x = 5;")
	 * @throws Exception */
	public void addVariable(String code) throws Exception
	{
		if (code == null || code.isEmpty()) throw new NullPointerException("Empty code");
		ClassStore.getInstance().addField(_RAClassname, code);
		_state.add(code);						// д.б. после обращения к ClassStore
	}

	/** adds a function to the agent by adding a new method to the corresponding runtime class
	 * @param code - Java code (e.g. "public String sayHello() {return \"Hello\"};")
	 * @throws Exception */
	public void addFunction(String code) throws Exception
	{
		if (code == null || code.isEmpty()) throw new NullPointerException("Empty code");
		ClassStore.getInstance().addMethod(_RAClassname, code);
		_transformFunctions.add(code);			// д.б. после обращения к ClassStore
	}

	/** fseef
	 * @param data
	 * @throws Exception */
	public void notifyAgent(IMessage data) throws Exception
	{
		if (data instanceof StateChanged)
		{
			StateChanged msg = (StateChanged) data;
			Logger.getLogger(getClass()).info(String.format("---> Message received: %s = %s", msg.get_variable(), msg.get_value()));
			_runtimeAssistant.getClass().getDeclaredField(msg.get_variable()).set(_runtimeAssistant, msg.get_value());
		}
		else throw new UnsupportedDataTypeException("Message type is unknown");
	}

	/** @return fsaef */
	public Collection<String> getState()
	{
		return new LinkedList<>(_state);
	}

	/** @return fnasiojo */
	public Collection<String> getTransformFunctions()
	{
		return new LinkedList<>(_transformFunctions);
	}

	@Override
	public void loadCode() throws Exception
	{
		for (String s : _state)
			ClassStore.getInstance().addField(_RAClassname, s);
		for (String s : _transformFunctions)
			ClassStore.getInstance().addMethod(_RAClassname, s);
	}

	@Override
	public void compileAgents() throws Exception
	{
		_runtimeAssistant = ClassStore.getInstance().compile(_RAClassname);
		_eventList.setRuntimeAssistant(_runtimeAssistant);
		Logger.getLogger(getClass()).info(String.format("Agent \"%s\" compiled", _name));
	}

	/** @return the _name */
	public String get_name()
	{
		return _name;
	}

	/** @param _name the _name to set */
	public void set_name(String name)
	{
		if (name == null || name.isEmpty()) throw new NullPointerException("Agent must have a name");
		_name = name;
	}

	/** @throws Exception
	 * @see ru.tomtrix.agentsocks.mathmodel.EventList#executeNextEvent() */
	public void executeNextEvent() throws Exception
	{
		_eventList.executeNextEvent();
	}

	/** @return
	 * @see ru.tomtrix.agentsocks.mathmodel.EventList#getNextEventTime() */
	public Double getNextEventTime()
	{
		return _eventList.getNextEventTime();
	}

	/** @param timestamp
	 * @param fid
	 * @param pars
	 * @throws Exception
	 * @see ru.tomtrix.agentsocks.mathmodel.EventList#addEvent(java.lang.Double, java.lang.String, java.lang.Object[]) */
	public void addEvent(Double timestamp, String fid, Object... pars) throws Exception
	{
		_eventList.addEvent(timestamp, fid, pars);
	}
}
