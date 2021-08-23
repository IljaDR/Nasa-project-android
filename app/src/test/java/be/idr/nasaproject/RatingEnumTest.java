package be.idr.nasaproject;

import org.junit.Test;

import static org.junit.Assert.*;
import be.idr.nasaproject.DB.Converters;
import be.idr.nasaproject.DB.EarthData;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RatingEnumTest {
    @Test
    public void convertToEnum(){
        assertEquals(EarthData.Rating.LIKE, Converters.stringToEnum("LIKE"));
        assertEquals(EarthData.Rating.NEUTRAL, Converters.stringToEnum("NEUTRAL"));
        assertEquals(EarthData.Rating.DISLIKE, Converters.stringToEnum("DISLIKE"));
    }

    @Test
    public void convertFromEnum(){
        assertEquals("LIKE", Converters.fromEnum(EarthData.Rating.LIKE));
        assertEquals("NEUTRAL", Converters.fromEnum(EarthData.Rating.NEUTRAL));
        assertEquals("DISLIKE", Converters.fromEnum(EarthData.Rating.DISLIKE));
    }
}