package ru.tomtrix.agentsocks;

import java.io.File;
import java.io.IOException;
import mpi.MPI;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import ru.tomtrix.consoleui.*;
import ru.tomtrix.javassistwraper.ClassStore;
import ru.tomtrix.agentsocks.infrastructure.Model;
import ru.tomtrix.agentsocks.message.Mail;
import ru.tomtrix.agentsocks.modeleditor.MVCModel;

/** @author tom-trix */
public class Starter
{
	/** @param args 
	 * @throws IOException */
	public static void main(String[] args) throws IOException
	{
		if (args != null && args.length > 0 && args[0].trim().toLowerCase().equals("-editor"))
		{
			ConsoleUIListener listener = new MVCModel();
			ConsoleUI cui = new ConsoleUI("/data/Buffer/console.txt", listener);
			listener.setConsoleUI(cui);
			cui.run();
		}
		else try
		{
			MPI.Init(args);
			//
			ClassStore.getInstance().addClassPath(Mail.class);
			ClassStore.getInstance().addImport("ru.tomtrix.agentsocks.message");
			//
			ObjectMapper mapper = new ObjectMapper(); //TODO
			mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Model _modelRef = mapper.readValue(new File("/home/tom-trix/workspace/AgentSocks/lights.txt"), Model.class);
			_modelRef.loadCode();
			Logger.getLogger(Starter.class).info(String.format("Node #%d loaded (total = %d)", MPI.COMM_WORLD.Rank(), MPI.COMM_WORLD.Size()));
			Thread.sleep(1500);
			_modelRef.getNodeByNumber(MPI.COMM_WORLD.Rank()).run();
			MPI.Finalize();
		}
		catch (Exception e)
		{
			Logger.getLogger(Starter.class).error("fedohawo", e);
		}
	}
}
