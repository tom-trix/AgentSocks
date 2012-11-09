package ru.tomtrix.agentsocks;

import java.io.IOException;
import ru.tomtrix.consoleui.*;
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
		/*else try
		{
			MPI.Init(args);
			ClassStore.getInstance().addClassPath(Agent.class);
			GUI frame = new GUI(new MVCmodel(MPI.COMM_WORLD.Rank()));
			frame.setTitle("Modelling system (process" + MPI.COMM_WORLD.Rank() + ")");
			Logger.getLogger(Starter.class).info(String.format("Process #%d started (total = %d)", MPI.COMM_WORLD.Rank(), MPI.COMM_WORLD.Size()));
			MPI.Finalize();
		}
		catch (Exception e)
		{
			GUI frame = new GUI(new MVCmodel(0));
			frame.setTitle("Modelling system");
		}*/
	}
}
