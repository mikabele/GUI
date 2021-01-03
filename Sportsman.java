import java.io.*;
import java.util.*;

public class Sportsman implements Serializable {
    // class release version:
    private static final long serialVersionUID = 1L;
    // areas with prompts:
    String personsFullName;
    private int sectionID;
    private String exerciseType;
    String coachFullName;
    String dateTime;
    private float duration;
    private float dollarPrice;
    public static final String P_personFullName = "Person full name";
    public static final String P_sectionID = "Section ID";
    public static final String P_exerciseType = "Exercise type";
    public static final String P_coachFullName = "Coach full name";
    public static final String P_dateTime = "Date&Time";
    public static final String P_duration = "Duration";
    public static final String P_price = "Dollar price";
    // validation methods:

    private static Date curDate = new Date();

    //private static GregorianCalendar curCalendar = new GregorianCalendar();
    static Boolean validDate(long dateMS) {
        return dateMS > 0 && dateMS < curDate.getTime();
    }

    static Boolean validDuration(float dur) {
        return dur > 0;
    }

    static Boolean validPrice(float price) {
        return price > 0;
    }

    public static Boolean nextRead(Scanner fin, PrintStream out) {
        return nextRead(P_personFullName, fin, out);
    }

    static Boolean nextRead(final String prompt, Scanner fin, PrintStream out) {
        out.print(prompt);
        out.print(": ");
        return fin.hasNextLine();
    }

    public static final String nameDel = ",";

    public static Sportsman read(String[] args, PrintStream out)
            throws IOException, CustomException {
        Sportsman Sportsman = new Sportsman();

        Sportsman.personsFullName = args[0];

        Sportsman.sectionID = Integer.parseInt(args[1]);

        Sportsman.exerciseType = args[2];

        Sportsman.coachFullName = args[3];

        String str = args[4];

        long ms = Date.parse(str);
        if (!validDate(ms))
            throw new CustomException("Invalid format of date");
        Sportsman.dateTime = str;

        float tmp = Float.parseFloat(args[5]);

        if (!validDuration(tmp))
            throw new CustomException("Invalid duration (must be positive number");
        Sportsman.duration = tmp;

        tmp = Float.parseFloat(args[6]);

        if (!validPrice(tmp))
            throw new CustomException("Invalid dollar price (must be positive number");
        Sportsman.dollarPrice = tmp;
        return Sportsman;

    }

    public Sportsman() {
    }

    public static final String areaDel = "\n";

    public String toString() {
        return //new String (
                personsFullName + areaDel +
                        sectionID + areaDel +
                        exerciseType + areaDel +
                        coachFullName + areaDel +
                        dateTime + areaDel +
                        duration + areaDel +
                        dollarPrice
                //)
                ;
    }
}
