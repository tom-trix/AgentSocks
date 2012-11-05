package ru.tomtrix.agentsocks.ui;

import java.io.IOException;
import org.apache.log4j.Logger;
import ru.tomtrix.consoleui.ConsoleUI;

/**
 * @author tom-trix
 *
 */
public class ConsoleInterface
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			//TODO относительный адрес
			ConsoleUI cui = new ConsoleUI("/home/tom-trix/1.txt", new MVCmodel(0));
			cui.run();
		}
		catch (IOException e)
		{
			Logger.getLogger(ConsoleInterface.class).error("Error during reading the file", e);
		}
	}
}
