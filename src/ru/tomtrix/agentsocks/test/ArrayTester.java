package ru.tomtrix.agentsocks.test;

import java.util.Random;

import ru.tomtrix.agentsocks.utils.ArrayTransformer;

public class ArrayTester
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		Random r = new Random(System.currentTimeMillis());
		for (int i=1; i<=50000; i++)
		{
			byte bytes[] = new byte[r.nextInt(2000) + 2];
			for (int j=0; j< bytes.length; j++)
				bytes[j] = (byte) (r.nextInt(55)-1);
			int ints[] = ArrayTransformer.toIntArray(bytes);
			byte nv[] = ArrayTransformer.toByteArray(ints);
			for (int j=0; j<Math.max(bytes.length, nv.length); j++)
				if (bytes[j] != nv[j]) throw new Exception();
			System.out.println("Test successful: " + i);
		}
	}

}
