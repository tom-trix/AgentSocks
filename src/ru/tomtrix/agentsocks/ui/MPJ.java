/**
 * 
 */
package ru.tomtrix.agentsocks.ui;

import org.apache.log4j.Logger;

import mpi.MPI;

/**
 * @author tom-trix
 *
 */
public class MPJ
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MPI.Init(args);
		GUI frame = new GUI();
		frame.setTitle("Modelling system");
		Logger.getLogger(MPJ.class).info(String.format("Process #%d started (total = %d)", MPI.COMM_WORLD.Rank(), MPI.COMM_WORLD.Size()));
		MPI.Finalize();
	}

}
