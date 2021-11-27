import main.java.DataModels.Reservation;
import main.java.Managers.ReservationManager;
import main.java.Utilities.InputValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class ReservationManagerTests {

    @Test
    public void verifyCreateReservationCodeReturns() {
        String reservationCode = ReservationManager.createReservationCode();
        Assertions.assertNotEquals("", reservationCode);
    }

    @Test
    public void verifyMakeReservationFails(){
        LocalDate fromDate = LocalDate.of(2021, 10, 13);
        LocalDate toDate = LocalDate.of(2021, 10, 13);
        //Assertions.assertEquals(ReservationManager.makeReservation(toDate, fromDate), "");
    }

    @Test
    public void verifyMakeReservationSucceeds(){
        LocalDate fromDate = LocalDate.of(2021, 10, 12);
        LocalDate toDate = LocalDate.of(2021, 10, 13);
        //Assertions.assertNotEquals(ReservationManager.makeReservation(fromDate, toDate), "");
    }
}
