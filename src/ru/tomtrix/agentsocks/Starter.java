package ru.tomtrix.agentsocks;

import mpi.MPI;
import java.io.*;
import ru.tomtrix.consoleui.*;
import org.apache.log4j.Logger;

import ru.tomtrix.agentsocks.messaging.LocalMail;
import ru.tomtrix.agentsocks.messaging.Mail;
import ru.tomtrix.javassistwraper.ClassStore;
import ru.tomtrix.agentsocks.modeleditor.MVCModel;
import ru.tomtrix.agentsocks.utils.JsonSerializer;
import ru.tomtrix.agentsocks.infrastructure.Model;

/** cfasfseespok
 * @author tom-trix */
public class Starter
{
	/** @param args
	 * @throws IOException */
	public static void main(String[] args) throws IOException
	{
		// let an MPI class loader know the Mail class definition
		ClassStore.getInstance().addClassPath(Mail.class);
		ClassStore.getInstance().addClassPath(LocalMail.class);
		ClassStore.getInstance().addImport(Mail.class.getPackage().getName());
		ClassStore.getInstance().addImport(LocalMail.class.getPackage().getName());

		// run an application in an EDITOR mode
		if (args != null && args.length > 0 && args[0].trim().toLowerCase().equals("-editor"))
		{
			ConsoleUIListener listener = new MVCModel();
			ConsoleUI cui = new ConsoleUI(Constants.REGEXES_FILENAME, listener);
			listener.setConsoleUI(cui);
			cui.run();
		}
		// run an application in an MPI mode
		else try
		{
			// initialize MPI
			try
			{
				MPI.Init(args);
				Logger.getLogger(Starter.class).info(String.format("Trying to load %d nodes", MPI.COMM_WORLD.Size()));
			}
			catch (Exception e)
			{
				Logger.getLogger(Starter.class).error("\nBe careful!!! This program MUST be loaded within MPI-software! e.g.\n - Linux:   $MPJ_HOME/bin/mpjrun.sh -np 2 -jar agentsocks.jar\n - Windows: %MPJ_HOME%\\bin\\mpjrun.exe -np 2 -jar agentsocks.jar\n\nP.S. You're also able to run the program in an editor mode (add \"-editor\" argument)\n", e);
			}

			// load the model
			Model _modelRef = JsonSerializer.getMapper().readValue(new File(Constants.MODEL_FILENAME), Model.class);

			// compile the agents within runtime classes
			_modelRef.loadCode();
			_modelRef.compileAgents();
			Logger.getLogger(Starter.class).info(String.format("Node %d is ready to start", MPI.COMM_WORLD.Rank()));

			// run the specific node
			_modelRef.getNodeByNumber(MPI.COMM_WORLD.Rank()).run();

			// finalize MPI
			MPI.Finalize();
		}
		catch (Exception e)
		{
			Logger.getLogger(Starter.class).error("Error in a global scope", e);
		}
	}
}
