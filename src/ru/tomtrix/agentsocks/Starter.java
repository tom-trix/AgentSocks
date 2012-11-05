package ru.tomtrix.agentsocks;

import mpi.MPI;
import org.apache.log4j.Logger;

import ru.tomtrix.agentsocks.mathmodel.Agent;
import ru.tomtrix.agentsocks.ui.GUI;
import ru.tomtrix.agentsocks.ui.MVCmodel;
import ru.tomtrix.javassistwraper.ClassStore;

/** @author tom-trix */
public class Starter
{
	/** @param args */
	public static void main(String[] args)
	{
		try
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
		}
	}
}
