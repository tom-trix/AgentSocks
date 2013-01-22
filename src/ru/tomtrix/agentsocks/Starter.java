package ru.tomtrix.agentsocks;

import mpi.MPI;
import java.io.*;
import java.util.Arrays;
import ru.tomtrix.consoleui.*;
import org.apache.log4j.Logger;
import ru.tomtrix.productions.*;
import ru.tomtrix.agentsocks.messaging.*;
import ru.tomtrix.agentsocks.modeleditor.*;
import ru.tomtrix.javassistwraper.ClassStore;
import ru.tomtrix.productions.core.ConsoleCore;
import static ru.tomtrix.productions.Operation.*;
import ru.tomtrix.agentsocks.utils.XMLSerializer;
import static ru.tomtrix.productions.Inequality.*;
import ru.tomtrix.agentsocks.infrastructure.Model;
import ru.tomtrix.agentsocks.mathmodel.ProductionAgent;

/** cfasfseespok
 * @author tom-trix */
public class Starter
{
	/** @param args gr
	 * @throws IOException */
	public static void main(String[] args) throws Exception
	{
		// let an MPI class loader know the class definitions
		ClassStore.getInstance().addClassPath(Mail.class);
        ClassStore.getInstance().addClassPath(RuleSet.class);
        ClassStore.getInstance().addClassPath(Variable.class);
        ClassStore.getInstance().addClassPath(LocalMail.class);
        ClassStore.getInstance().addClassPath(ConsoleCore.class);
        ClassStore.getInstance().addClassPath(AgentProductionCore.class);
        ClassStore.getInstance().addImport(Mail.class.getPackage().getName());
        ClassStore.getInstance().addImport(RuleSet.class.getPackage().getName());
        ClassStore.getInstance().addImport(Variable.class.getPackage().getName());
        ClassStore.getInstance().addImport(LocalMail.class.getPackage().getName());
        ClassStore.getInstance().addImport(ConsoleCore.class.getPackage().getName());
        ClassStore.getInstance().addImport(AgentProductionCore.class.getPackage().getName());

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
                case "-test":
                    ProductionAgent agent = new ProductionAgent("Trix", "Trix");
                    agent.addVariable("money", VariableType.INFERRIBLE);
                    agent.addVariable("goal", VariableType.INFERRIBLE);
                    agent.addVariable("colour", VariableType.ASKABLE);
                    agent.addVariable("public String colour = \"blue\";");
                    agent.addRule("R1", Arrays.asList("money", "colour"), Arrays.asList(EQUALS, EQUALS), Arrays.asList("1", "blue"), Arrays.asList(OR), "goal", "Ура!!!");
                    agent.initializeVariable("money", "2");
                    agent.addTestConsulting("goal");
                    agent.addEvent(1, "testConsulting");
                    Model m = new Model("Debug");
                    m.addNode();
                    m.getNodeByNumber(0).get_container().addLogicProcess("Hed");
                    m.getNodeByNumber(0).get_container().getProcessByName("Hed").addAgent(agent);
                    new XMLSerializer<Model>().serializeToFile(m, "model.xml");
                    /*Model m = new XMLSerializer<Model>().deserializeFromFile("model.xml");
                    m.loadCode();*/
                    m.compileAgents();
                    m.getNodeByNumber(0).run();
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
            catch (Exception e) { Logger.getLogger(Starter.class).error("\nBe careful!!! This program MUST be loaded within MPI-software! e.g.\n - Linux:   $MPJ_HOME/bin/mpjrun.sh -np 2 -jar agentsocks.jar\n - Windows: %MPJ_HOME%\\bin\\mpjrun.exe -np 2 -jar agentsocks.jar\n\nP.S. You're also able to run the program in an editor mode (add \"-console\" or \"-gui\" argument)\n", e); }

            // load the model
            Model model = new XMLSerializer<Model>().deserializeFromFile(Constants.MODEL_FILENAME);

            // compile the agents within runtime classes
            model.loadCode();
            model.compileAgents();
            Logger.getLogger(Starter.class).info(String.format("Node %d is ready to start", MPI.COMM_WORLD.Rank()));

            // run the specific node
            model.getNodeByNumber(MPI.COMM_WORLD.Rank()).run();

            // finalize MPI
            MPI.Finalize();
        }
        catch (Exception e)
        {
            Logger.getLogger(Starter.class).error("Error in a global scope", e);
        }
    }
}
