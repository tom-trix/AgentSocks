package ru.tomtrix.agentsocks.test;

import java.io.Serializable;

import ru.tomtrix.agentsocks.utils.ArrayTransformer;

public class Fray implements Serializable
{
	private static final long	serialVersionUID	= -6668670094590638487L;
	public String s;
	public double x;
	public Fray(String s, double x)
	{
		this.s = s;
		this.x = x;
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		Serializable s = new Fray("Trix", 7.55);
		byte[] b = ArrayTransformer.serialize(s);
		Fray d = (Fray) ArrayTransformer.deserialize(b);
		System.out.println(d.s + " " + d.x);
	}

}
