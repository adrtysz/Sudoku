import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class })
public class RunTest
{
	public static void main(final String[] args)
	{
		// Run all JUnit tests programmatically
		final Result result = JUnitCore.runClasses( BoardTest.class );
		for ( final Failure failure : result.getFailures() )
		{
			System.out.println( failure.toString() );
		}
	}

}
