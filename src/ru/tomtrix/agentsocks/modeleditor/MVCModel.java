package ru.tomtrix.agentsocks.modeleditor;

import java.io.File;
import ru.tomtrix.consoleui.*;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;
import ru.tomtrix.agentsocks.infrastructure.*;
import ru.tomtrix.agentsocks.utils.JsonSerializer;

/** jfsofeos
 * @author tom-trix */
public class MVCModel implements ConsoleUIListener
{
	private static final String	ERROR_STR	= "Error occured";

	/** fnsefnieo */
	private ConsoleUI			_cuiRef;
	/** fshisfueih */
	private Model				_model;
	/** jdfaiofsoei */
	private ProcessAndAgent		_pa;

	/** fsemklfel
	 * @author tom-trix */
	private class ProcessAndAgent
	{
		public LogicProcess	process;
		public Agent		agent;

		public ProcessAndAgent(LogicProcess p, Agent a)
		{
			process = p;
			agent = a;
		}
	}

	/** fshirfiiu
	 * @param name
	 * @return */
	public String createModel(String name)
	{
		try
		{
			_model = new Model(name);
			return String.format("OK. Model \"%s\" has been created", name);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fsejofhbeui
	 * @param filename
	 * @return */
	public String loadModel(String filename)
	{
		try
		{
			_model = JsonSerializer.getMapper().readValue(new File(filename), Model.class);
			_model.loadCode();
			return String.format("OK. Model \"%s\" has been loaded", _model.get_name());
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** dfahifehui
	 * @param filename
	 * @return */
	public String saveModel(String filename)
	{
		try
		{
			JsonSerializer.getMapper().writeValue(new File(filename), _model);
			return String.format("OK. Model \"%s\" has been saved", _model.get_name());
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fhisufsei
	 * @return */
	public String showModel()
	{
		return _model.toString();
	}

	/** dfaseihfi
	 * @return */
	public String createNode()
	{
		try
		{
			return String.format("OK. Node %d has been created", _model.addNode());
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** @return fshoehfeuio */
	public String deleteLastNode()
	{
		try
		{
			_model.deleteLastNode();
			return String.format("OK. Node and all its logic processes have been removed. There are %d nodes left", _model.getNodesCount());
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fshjoieio
	 * @param name
	 * @param rank
	 * @return */
	public String createProcess(String name, String rank)
	{
		try
		{
			_model.getNodeByNumber(Integer.parseInt(rank)).get_container().addLogicProcess(name);
			return String.format("OK. Logic process \"%s\" has been created on node %d", name, rank);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** sfeohis
	 * @param name
	 * @param rank
	 * @param newname
	 * @return */
	public String renameProcess(String name, String rank, String newname)
	{
		try
		{
			_model.getNodeByNumber(Integer.parseInt(rank)).get_container().getProcessByName(name).set_name(newname);
			return String.format("OK. Logic process \"%s\" has been renamed into \"%s\"", name, newname);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** afjoeefoi
	 * @param name
	 * @param rank
	 * @param newRank
	 * @return */
	public String moveProcess(String name, String rank, String newRank)
	{
		try
		{
			Container c = _model.getNodeByNumber(Integer.parseInt(rank)).get_container();
			LogicProcess lp = c.getProcessByName(name);
			c.removeProcess(name);
			_model.getNodeByNumber(Integer.parseInt(newRank)).get_container().addLogicProcess(lp);
			return String.format("OK. Logic process \"%s\" has been moved from %d to %d", name, rank, newRank);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fsohjoef
	 * @param name
	 * @param rank
	 * @return */
	public String deleteProcess(String name, String rank)
	{
		try
		{
			_model.getNodeByNumber(Integer.parseInt(rank)).get_container().removeProcess(name);
			return String.format("OK. Logic process \"%s\" and all its agents have been removed from the node %d", name, rank);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** uafopjafo
	 * @param name
	 * @param process
	 * @param rank
	 * @return */
	public String createAgent(String name, String process, String rank)
	{
		try
		{
			_pa = new ProcessAndAgent(_model.getNodeByNumber(Integer.parseInt(rank)).get_container().getProcessByName(process), new DefaultAgent(name));
			_pa.process.addAgent(_pa.agent);
			return String.format("OK. Agent \"%s\" has been created.\nNow it is currently used", name);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** faseoifeo
	 * @param name
	 * @param process
	 * @param rank
	 * @return */
	public String useAgent(String name, String process, String rank)
	{
		try
		{
			// push greeting
			if (_pa != null && _pa.agent != null) _cuiRef.pop_greeting();
			_cuiRef.push_greeting(name);
			// create new "_pa" instance
			LogicProcess p = _model.getNodeByNumber(Integer.parseInt(rank)).get_container().getProcessByName(process);
			_pa = new ProcessAndAgent(p, p.getAgentByName(name));
			// return
			return String.format("OK. Now agent \"%s\" is currently used", name);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fsvonedpa
	 * @param newname
	 * @return */
	public String renameAgent(String newname)
	{
		try
		{
			_pa.agent.set_name(newname);
			_cuiRef.pop_greeting();
			_cuiRef.push_greeting(newname);
			return String.format("OK. Agent has been renamed into \"%s\"", newname);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fasejoijho
	 * @param newProcess
	 * @param newNode
	 * @return */
	public String moveAgent(String newProcess, String newNode)
	{
		try
		{
			_pa.process.removeAgent(_pa.agent);
			_pa.process = _model.getNodeByNumber(Integer.parseInt(newNode)).get_container().getProcessByName(newProcess);
			_pa.process.addAgent(_pa.agent);
			return String.format("OK. Agent \"%s\" has been moved to logic process \"%s\" on node %d", _pa.agent.get_name(), newProcess, newNode);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fasjoijefo
	 * @return */
	public String deleteAgent()
	{
		try
		{
			String s = String.format("OK. Agent \"%s\" has been removed from process \"%s\"", _pa.agent.get_name(), _pa.process.get_name());
			_pa.process.removeAgent(_pa.agent);
			_pa = null;
			_cuiRef.pop_greeting();
			return s;
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** fsiefi
	 * @param code
	 * @return */
	public String addVariable(String code)
	{
		try
		{
			_pa.agent.addVariable(code);
			return String.format("OK. Agent \"%s\" has got the definition changed:\n", _pa.agent.get_name(), _pa.agent);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** ohfsiohabif
	 * @param var
	 * @return */
	public String deleteVariable(String var)
	{
		try
		{
			_pa.agent.removeVariable(var);
			return String.format("OK. Agent \"%s\" has got the definition changed:\n", _pa.agent.get_name(), _pa.agent);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** joefai
	 * @param code
	 * @return */
	public String addFunction(String code)
	{
		try
		{
			_pa.agent.addFunction(code);
			return String.format("OK. Agent \"%s\" has got the definition changed:\n", _pa.agent.get_name(), _pa.agent);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** pkfoeshui
	 * @param fid
	 * @return */
	public String deleteFunction(String fid)
	{
		try
		{
			_pa.agent.removeFunction(fid);
			return String.format("OK. Agent \"%s\" has got the definition changed:\n", _pa.agent.get_name(), _pa.agent);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** @param fid
	 * @param timestamp
	 * @param params
	 * @return */
	public String addEvent(String fid, String timestamp, String params)
	{
		try
		{
			_pa.agent.addEvent(Double.parseDouble(timestamp), fid);
			return String.format("OK. Agent \"%s\" has got the definition changed:\n", _pa.agent.get_name(), _pa.agent);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** posijoefjo
	 * @param fid
	 * @param timestamp
	 * @return
	 */
	public String deleteEvent(String fid, String timestamp)
	{
		try
		{
			_pa.agent.removeEvent(Double.parseDouble(timestamp));
			return String.format("OK. Agent \"%s\" has got the definition changed:\n", _pa.agent.get_name(), _pa.agent);
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error(ERROR_STR, e);
			return e.toString();
		}
	}

	/** hcfseaio */
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
