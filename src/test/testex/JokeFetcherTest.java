package testex;

import org.junit.Before;
import org.mockito.Mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import testex.jokefetching.*;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;


/**
 * Created by ismailcam on 14/03/2017.
 */
@RunWith( MockitoJUnitRunner.class )
public class JokeFetcherTest
{

    @Mock
    IDateFormatter DateMock;

    @Mock
    Joke theNewJoke;

    @Mock
    JokeFetcher JokeF;
    private JokeFetcher jokeFetcher;
    @Mock
    IDateFormatter ifMock;
    @Mock
    IFetcherFactory factory;
    @Mock
    Moma moma;
    @Mock
    ChuckNorris chuck;
    @Mock
    EduJoke edu;
    @Mock
    Tambal tambal;

    @Before
    public void setup()
    {
        List< IJokeFetcher > fetchers = Arrays.asList( edu, chuck, moma, tambal );
        when( factory.getJokeFetchers( "EduJoke,ChuckNorris,Moma,Tambal" ) ).thenReturn( fetchers );
        List< String > types = Arrays.asList( "EduJoke", "ChuckNorris", "Moma", "Tambal" );
        when( factory.getAvailableTypes() ).thenReturn( types );
        jokeFetcher = new JokeFetcher( ifMock, factory );
    }


    @Test
    public void testGetAvailableTypes() throws Exception
    {
        List< String > availableTypes = JokeF.getAvailableTypes();
        assertThat( availableTypes, hasItems() );
        assertThat( availableTypes, hasItems( "eduprog", "chucknorris", "moma", "tambal" ) );
    }

    @Test
    public void testCheckIfValidToken() throws Exception
    {
        // the reason we get to use isStringValid() in this class
        // is because vi have the test class in the same package as src
        // and its a public modifier method
        boolean valid = JokeF.isStringValid( "eduprog,chucknorris,moma,tambal" );

        boolean invalid = JokeF.isStringValid( "hej,med,dig,invalid" );

        assertThat( valid, equalTo( true ) );
        assertThat( invalid, equalTo( false ) );


    }


    @Test
    public void testGetJokes() throws Exception
    {
        given( DateMock.getFormattedDate( eq( "Europe/Copenhagen" ),
                                          anyObject() ) ).willReturn( "17 feb. 2017 10:56 AM" );

        Jokes jokes = JokeF.getJokes( "EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen" );

        assertThat( jokes.getJokes().size(), greaterThan( 3 ) );
        assertThat( jokes.getTimeZoneString(), containsString( "17 feb. 2017 10:56 AM" ) );

        verify( DateMock, times( 1 ) ).getFormattedDate( anyObject(), anyObject() );


        for( Joke j : jokes.getJokes() )
        {
            assertThat( j, equalTo( theNewJoke ) );
        }

        verify( edu, times( 1 ) ).getJoke();
        verify( chuck, times( 1 ) ).getJoke();
        verify( moma, times( 1 ) ).getJoke();
        verify( tambal, times( 1 ) ).getJoke();
    }


}