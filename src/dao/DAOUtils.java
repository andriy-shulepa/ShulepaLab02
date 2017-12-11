package dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class DAOUtils {
    public static BigInteger generateID(int tableID) {
        Calendar calendar = new GregorianCalendar();

        String date = String.format("%04d%02d%02d%02d%02d%02d", calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH) + 1),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        Random random = new Random();
        int randomValue = random.nextInt(9999);
        return new BigInteger("" + tableID + date + String.format("%04d", randomValue));
    }
}
