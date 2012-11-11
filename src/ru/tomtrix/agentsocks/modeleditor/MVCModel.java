/**
 * 
 */
package ru.tomtrix.agentsocks.modeleditor;

import java.io.File;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;
import ru.tomtrix.agentsocks.infrastructure.*;
import ru.tomtrix.consoleui.ConsoleUI;
import ru.tomtrix.consoleui.ConsoleUIListener;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;


/**
 * @author tom-trix
 *
 */
public class MVCModel implements ConsoleUIListener
{
	private ConsoleUI _cuiRef;
	private Model _modelRef;
	private ProcessAndAgent _pa;
	
	private class ProcessAndAgent
	{
		public LogicProcess process;
		public Agent agent;
		
		public ProcessAndAgent(LogicProcess p, Agent a)
		{
			process = p;
			agent = a;
		}
	}
	
	public String createModel(String name)
	{
		_modelRef = new Model(name);
		return "OK";
	}
	
	public String loadModel(String filename)
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper(); //TODO
			mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			_modelRef = mapper.readValue(new File(filename), Model.class);
			//общий загрузчик кода TODO
			_modelRef.loadCode();
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String saveModel(String filename)
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper(); //TODO
			mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.writeValue(new File(filename), _modelRef);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String showModel()
	{
		return _modelRef.toString(); //TODO
	}
	
	public String createNode()
	{
		try
		{
			return "OK: " + _modelRef.addNode();
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String deleteLastNode()
	{
		try
		{
			_modelRef.deleteLastNode();
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String createProcess(String name, String rank)
	{
		try
		{
			_modelRef.getNodeByNumber(Integer.parseInt(rank)).get_container().addLogicProcess(name);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String renameProcess(String name, String rank, String newName)
	{
		try
		{
			_modelRef.getNodeByNumber(Integer.parseInt(rank)).get_container().getProcessByName(name).set_name(newName);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String moveProcess(String name, String rank, String newRank)
	{
		try
		{
			Container c = _modelRef.getNodeByNumber(Integer.parseInt(rank)).get_container();
			LogicProcess lp = c.getProcessByName(name);
			c.removeProcess(name);
			_modelRef.getNodeByNumber(Integer.parseInt(newRank)).get_container().addLogicProcess(lp);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String deleteProcess(String name, String rank)
	{
		try
		{
			_modelRef.getNodeByNumber(Integer.parseInt(rank)).get_container().removeProcess(name);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String createAgent(String name, String process, String rank)
	{
		try
		{
			_pa = new ProcessAndAgent(_modelRef.getNodeByNumber(Integer.parseInt(rank)).get_container().getProcessByName(process), new DefaultAgent(name));
			_pa.process.addAgent(_pa.agent);
			//TODO push_greeting
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String useAgent(String name, String process, String rank)
	{
		try
		{
			LogicProcess p = _modelRef.getNodeByNumber(Integer.parseInt(rank)).get_container().getProcessByName(process);
			_pa = new ProcessAndAgent(p, p.getAgentByName(name));
			//TODO push_greeting
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String renameAgent(String newname)
	{
		try
		{
			_pa.agent.set_name(newname);
			//TODO pop_greeting
			//TODO push_greeting
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String moveAgent(String newProcess, String newNode)
	{
		try
		{
			_pa.process.removeAgent(_pa.agent);
			_pa.process = _modelRef.getNodeByNumber(Integer.parseInt(newNode)).get_container().getProcessByName(newProcess);
			_pa.process.addAgent(_pa.agent);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String deleteAgent()
	{
		try
		{
			_pa.process.removeAgent(_pa.agent);
			_pa = null;
			//TODO pop_greeting
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String addVariable(String code)
	{
		try
		{
			_pa.agent.addVariable(code);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String deleteVariable(String var)
	{
		try
		{
			//TODO _pa.agent.deleteVariable(var);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String addFunction(String code)
	{
		try
		{
			_pa.agent.addFunction(code);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String deleteFunction(String var)
	{
		try
		{
			//TODO _pa.agent.deleteFunction(var);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String addEvent(String fid, String timestamp, String params)
	{
		try
		{
			_pa.agent.addEvent(Double.parseDouble(timestamp), fid);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public String deleteEvent(String fid, String timestamp)
	{
		try
		{
			//TODO _pa.agent.get_eventList().deleteEvent(Double.parseDouble(timestamp), fid);
			return "OK";
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("fseedfa", e);
			return e.toString();
		}
	}
	
	public void bye()
	{
		_cuiRef.stop();
	}

	@Override
	public void setConsoleUI(ConsoleUI cui)
	{
		_cuiRef = cui;
	}
}
