package ru.tomtrix.agentsocks.test;

import org.junit.*;
import java.util.Random;
import static org.junit.Assert.*;
import ru.tomtrix.agentsocks.utils.Collections;

/** dfase
 * @author tom-trix */
public class UtilsTester
{

	/** fse
	 * @throws java.lang.Exception */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	/** ggs
	 * @throws java.lang.Exception */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	/** esfs
	 * @throws java.lang.Exception */
	@Before
	public void setUp() throws Exception
	{}

	/** dawda
	 * @throws java.lang.Exception */
	@After
	public void tearDown() throws Exception
	{}

	/** fhusi
	 * @param count */
	private void testArrays(int count)
	{
		Random r = new Random(System.currentTimeMillis());
		for (int i = 1; i <= count; i++)
		{
			byte bytes[] = new byte[r.nextInt(2000) + 2];
			for (int j = 0; j < bytes.length; j++)
				bytes[j] = (byte) (r.nextInt(55) - 1);
			int ints[] = Collections.toIntArray(bytes);
			byte nv[] = Collections.toByteArray(ints);
			assertArrayEquals(bytes, nv);
		}
	}

	/** Test method for {@link ru.tomtrix.agentsocks.utils.Collections#toIntArray(byte[])}. */
	@Test
	public final void testToIntArray()
	{
		testArrays(20000);
	}

	/** Test method for {@link ru.tomtrix.agentsocks.utils.Collections#toByteArray(int[])}. */
	@Test
	public final void testToByteArray()
	{
		testArrays(20000);
	}
}
