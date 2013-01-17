package ru.tomtrix.agentsocks;

import mpi.MPI;
import java.io.*;

import ru.tomtrix.consoleui.*;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.messaging.*;
import ru.tomtrix.agentsocks.modeleditor.*;
import ru.tomtrix.javassistwraper.ClassStore;
import ru.tomtrix.agentsocks.utils.XMLSerializer;
import ru.tomtrix.agentsocks.infrastructure.Model;

/** cfasfseespok
 * @author tom-trix */
public class Starter
{
	/** @param args gr
	 * @throws IOException */
	public static void main(String[] args) throws IOException
	{
		// let an MPI class loader know the Mail class definition
		ClassStore.getInstance().addClassPath(Mail.class);
		ClassStore.getInstance().addClassPath(LocalMail.class);
		ClassStore.getInstance().addImport(Mail.class.getPackage().getName());
		ClassStore.getInstance().addImport(LocalMail.class.getPackage().getName());

		if (args != null && args.length > 0)
            switch (args[0].trim().toLowerCase())
            {
                case "-console":
                    ConsoleUIListener listener = new MVCModel();
                    ConsoleUI cui = new ConsoleUI(Constants.REGEXES_FILENAME, listener);
                    listener.setConsoleUI(cui);
                    cui.run();
                    break;
                case "-gui":
                    MVCModel model = new MVCModel();
                    GraphicUI gui = new GraphicUI(model);
                    gui.run();
                    break;
                default: start(args);
            }
		else start(args);
	}

    public static void start(String[] args)
    {
        try
        {
            // initialize MPI
            try
            {
                MPI.Init(args);
                Logger.getLogger(Starter.class).info(String.format("Trying to load %d nodes", MPI.COMM_WORLD.Size()));
            }
            catch (Exception e)
            {
                Logger.getLogger(Starter.class).error("\nBe careful!!! This program MUST be loaded within MPI-software! e.g.\n - Linux:   $MPJ_HOME/bin/mpjrun.sh -np 2 -jar agentsocks.jar\n - Windows: %MPJ_HOME%\\bin\\mpjrun.exe -np 2 -jar agentsocks.jar\n\nP.S. You're also able to run the program in an editor mode (add \"-console\" or \"-gui\" argument)\n", e);
            }

            // load the model
            Model _modelRef = new XMLSerializer<Model>().deserializeFromFile(Constants.MODEL_FILENAME);

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
