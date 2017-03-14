package testex.jokefetching;

import testex.JokeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ismailcam on 14/03/2017.
 */
public class FetcherFactory implements IFetcherFactory
{

    private final List< String > allTypes = Arrays.asList( "EduJoke", "ChuckNorris", "Moma", "Tambal" );

    @Override
    public List< String > getAvailableTypes()
    {
        return allTypes;
    }

    @Override
    public List< IJokeFetcher > getJokeFetchers(String jokesToFetch)
    {
        List< IJokeFetcher > fetchers = new ArrayList< IJokeFetcher >();
        String[] tokens = jokesToFetch.split( "," );
        Class c;
        IJokeFetcher fetch;
        try
        {
            for( String token : tokens )
            {
                if( !allTypes.contains( token ) )
                {
                    throw new JokeException( "Inputs (jokesToFetch) contain types not recognized" );
                }
                else
                {
                    fetchers.add( ( IJokeFetcher ) Class.forName( "testex.jokefetching." + token ).newInstance() );
                }
            }
        }
        catch( Exception e )
        {
            System.out.println( e );
        }
        return fetchers;
    }
}

