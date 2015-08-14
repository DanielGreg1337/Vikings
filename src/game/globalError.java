package game;

/**
 * Created by bob on 12/08/15.
 */
public class globalError {
    String message = ""; //error message to display to user.
    String technicalDetails = ""; //logged error message, advanced details for technical support or advanced users.

    public globalError(String message, String technicalDetails)
    {
        this.message = message;
        this.technicalDetails = technicalDetails;
    }
}
