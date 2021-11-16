package uoc.ded.practica;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uoc.ded.practica.exceptions.ActivityNotFoundException;
import uoc.ded.practica.exceptions.DEDException;
import uoc.ded.practica.exceptions.NoActivitiesException;
import uoc.ded.practica.exceptions.NoRecordsException;
import uoc.ded.practica.exceptions.UserNotFoundException;
import uoc.ded.practica.exceptions.UserNotInActivityException;
import uoc.ded.practica.util.DateUtils;
import uoc.ei.tads.ExcepcioContenidorPle;
import uoc.ei.tads.ExcepcioPosicioInvalida;

public class SafetyActivities4Covid19PR1ExtendedTest {
	
	private SafetyActivities4Covid19 safetyActivities4Covid19;
	private SafetyActivities4Covid19 safetyActivities4Covid19Empty;

	// Load all the data from the factory
    @Before
    public void setUp() throws Exception {
        this.safetyActivities4Covid19 = FactorySafetyActivities4Covid19.getSafetyActivities4Covid19();
        this.safetyActivities4Covid19Empty = new SafetyActivities4Covid19Impl();
    }

    // Clear the TAD
    @After
    public void tearDown() {
        this.safetyActivities4Covid19 = null;
    }
    
    // Try to overflow the user's array
    @Test
    public void testUsersFull() {
    	for (int i = safetyActivities4Covid19.numUsers(); i < SafetyActivities4Covid19.U; i++)
    		this.safetyActivities4Covid19.addUser("idUser20" + safetyActivities4Covid19.numUsers(), "Robert", "Lopez", createLocalDate("02-01-1942"), false);
    	
    	Assert.assertEquals(SafetyActivities4Covid19.U, this.safetyActivities4Covid19.numUsers());
    	
    	try {
    		this.safetyActivities4Covid19.addUser("idUserOut", "Robert", "Lopez", createLocalDate("02-01-1942"), false);
    		fail("fail ExcepcioContenidorPle");
    	} catch(ExcepcioContenidorPle e) {}
    }
    
    // Try to overflow the organizations' array
    @Test
    public void testOrganizationsFull() {
    	// Only add organizations when the position of the array is null
    	for (int i = 0; i < SafetyActivities4Covid19.O; i++)
    		if (this.safetyActivities4Covid19.getOrganization(i) == null)
    			this.safetyActivities4Covid19.addOrganization(i, "ORG_" + (i+100), "description " + (i+100));
    	
    	Assert.assertEquals(SafetyActivities4Covid19.O, this.safetyActivities4Covid19.numOrganizations());
    	
    	try {
    		this.safetyActivities4Covid19.addOrganization(SafetyActivities4Covid19.O, "ORG_OUT", "description out");
    		fail("fail ExcepcioPosicioInvalida");
    	} catch(ExcepcioPosicioInvalida e) {}
    }
    
    // Try to overflow the activities OrderedVectorDictionary
    @Test
    public void testActivitiesFull() throws DEDException {
    	for (int i = this.safetyActivities4Covid19.numPendingRecords(); i < SafetyActivities4Covid19.A; i++)
    		this.safetyActivities4Covid19.addRecord("XXX-" + (i+100), "ACT-" + (i+2000), "description ACT-" + (i+2000) ,
                    createDate("22-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 100, 10);
    	
    	Assert.assertEquals(this.safetyActivities4Covid19.numPendingRecords(), SafetyActivities4Covid19.A);
    	
    	for (int i = this.safetyActivities4Covid19.numActivities(); i < SafetyActivities4Covid19.A; i++)
    	{
    		this.safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.ENABLED,
                    createDate("25-11-2021 23:00:00"), "OK X" + (i+1));
    	}
    	
    	Assert.assertEquals(this.safetyActivities4Covid19.numActivities(), SafetyActivities4Covid19.A);
    	
    	this.safetyActivities4Covid19.addRecord("XXX-1000", "ACT-3000", "description ACT-3000" ,
                createDate("22-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 100, 10);
    	
    	try {
    		this.safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.ENABLED,
                    createDate("25-11-2021 23:00:00"), "OK X3000");
    		fail("fail ExcepcioContenidorPle");
    	} catch (ExcepcioContenidorPle e) {}
    }
    
    // Try to updateRecords with no records
    @Test
    public void testCheckNoRecords() throws DEDException {
    	this.safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.DISABLED,
                createDate("25-11-2021 23:00:00"), "KO X3000");
    	
    	try {
    		this.safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.ENABLED,
                    createDate("25-11-2021 23:00:00"), "OK X3000");
    		fail("fail NoRecordsException");
    	} catch (NoRecordsException e) {}
    }
    
    // Check getAllActivitiesByUser when the iterator has elements and whether no
    @Test
    public void testGetAllActivitiesByUser() throws DEDException {
    	Assert.assertEquals(this.safetyActivities4Covid19.getActivitiesByUser("idUser1").hiHaSeguent(), true);
    	Assert.assertEquals(this.safetyActivities4Covid19.getActivitiesByUser("idUser2").hiHaSeguent(), true);
    	Assert.assertEquals(this.safetyActivities4Covid19.getActivitiesByUser("idUser3").hiHaSeguent(), true);
    	Assert.assertEquals(this.safetyActivities4Covid19.getActivitiesByUser("idUser4").hiHaSeguent(), true);
    	Assert.assertEquals(this.safetyActivities4Covid19.getActivitiesByUser("idUser5").hiHaSeguent(), true);
    	Assert.assertEquals(this.safetyActivities4Covid19.getActivitiesByUser("idUser6").hiHaSeguent(), true);
    	
    	try {
    		this.safetyActivities4Covid19.getActivitiesByUser("idUser7");
    		this.safetyActivities4Covid19.getActivitiesByUser("idUser8");
    		this.safetyActivities4Covid19.getActivitiesByUser("idUser9");
    		fail("fail NoActivitiesException");
    	} catch (NoActivitiesException e) {}
    }
    
    // Illegal addRating according to activityId and userId
    @Test
    public void testAddRatingIllegal() throws DEDException {
    	try {
    		this.safetyActivities4Covid19.addRating("ACT-5102",
                    SafetyActivities4Covid19.Rating.FOUR, "Good!!!", "idUser4");
    		fail("fail ActivityNotFoundException");
    	} catch (ActivityNotFoundException e) {}
    	
    	try {
    		this.safetyActivities4Covid19.addRating("ACT-1102",
                    SafetyActivities4Covid19.Rating.FOUR, "Good!!!", "idUser40");
    		fail("fail UserNotFoundException");
    	} catch (UserNotFoundException e) {}
    	
    	try {
    		this.safetyActivities4Covid19.addRating("ACT-1102",
                    SafetyActivities4Covid19.Rating.FOUR, "Good!!!", "idUser9");
    		fail("fail UserNotInActivityException");
    	} catch (UserNotInActivityException e) {}
    	
    	try {
    		this.safetyActivities4Covid19.getRatings("ACT-5102");
    		fail("fail ActivityNotFoundException");
    	} catch (ActivityNotFoundException e) {}
    }
    
    // Check if the activity and user exist when try to create a ticket
    @Test
    public void testCreateTicketAndAssignNoUserNoActivity() throws DEDException {
    	try {
    		this.safetyActivities4Covid19.createTicket("idUser40", "ACT-1102");
    		fail("fail UserNotFoundException");
    	} catch (UserNotFoundException e) {}
    	
    	try {
    		this.safetyActivities4Covid19.createTicket("idUser4", "ACT-5102");
    		fail("fail ActivityNotFoundException");
    	} catch (ActivityNotFoundException e) {}
    }
    
    // Check if the activity exists when try to assign a seat
    @Test
    public void testAssignSeatNoActivity() throws DEDException {
    	try {
    		this.safetyActivities4Covid19.assignSeat("ACT-5102");
    		fail("fail ActivityNotFoundException");
    	} catch (ActivityNotFoundException e) {}
    }
    
    // No best activity because there aren't any one
    @Test
    public void testBestActivityEmpty() {
    	try {
    		this.safetyActivities4Covid19Empty.bestActivity();
    		fail("fail ActivityNotFoundException");
    	} catch (ActivityNotFoundException e) {}
    }
    
    // Check the most active user and check it when there
    // aren't any user or there aren't any user in activity too
    @Test
    public void testMostActiveUser() throws UserNotFoundException {
    	Assert.assertEquals(this.safetyActivities4Covid19.mostActiveUser(), this.safetyActivities4Covid19.getUser("idUser1"));
    	
    	try {
    		this.safetyActivities4Covid19Empty.mostActiveUser();
    	} catch (UserNotFoundException e) {}
    }
    
    private Date createDate(String date) {
        return DateUtils.createDate(date);
    }

    private LocalDate createLocalDate(String date) {
        return DateUtils.createLocalDate(date);
    }
	
}
