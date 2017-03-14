package testex;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by ismailcam on 14/03/2017.
 */

@RunWith( MockitoJUnitRunner.class )
public class DateFormatterTest
{

    @Mock
    IDateFormatter IDateF;

    @Mock
    Joke joke;

    @Mock
    JokeFetcher JokeF;

    @Test
    public void testGetFormattedDateMethod() throws Exception
    {
        Date date = new Date( 669988800000L );
        String timeZone = "Europe/Copenhagen";

        DateFormatter dateFormatter = new DateFormatter();

        String formatResult = dateFormatter.getFormattedDate( timeZone, date );
        String expectedResult = "26 mar. 1991 01:00 PM";

        assertThat( expectedResult, is( formatResult ) );

    }

}