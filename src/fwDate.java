import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fwDate {
    private static final Integer[] daysInMonth = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String datePattern = "(\\d+)\\-(\\d+)\\-(\\d+)";
    private int Day;
    private int Month;
    private int Year;
    public String resultDate;
    private boolean validity;
    private static final String dateMinString = "2017-01-01";
    private static final String dateMaxString = "2022-12-31";

    public fwDate(int day, int month, int year) {
        if (check()) {
            validity = true;
        } else {
            validity = false;
        }
        resultDate = dateToString();
    }

    public fwDate(String date) {
        if (parseString(date)) {
            validity = true;
        } else {
            validity = false;
        }
        resultDate = dateToString();
    }

    public fwDate getDateMax () {
        return new fwDate(dateMaxString);
    }

    public fwDate getDateMin () {
        return new fwDate(dateMinString);
    }

    public int compareTo(fwDate d) {
        // like string
        // like >
        // if == returns 0
        // if > , returns 1
        // if < returns -1

        if (Month == d.Month && Day == d.Day && Year == d.Year) {return 0;}

        if (Year > d.Year) {return 1;}

        if (Year < d.Year) {return -1;}

        if (Month > d.Month) {return 1;}

        if (Month < d.Month) {return  -1;}

        if (Day > d.Day) {return 1;}

        if (Day < d.Day) {return  -1;}

        return 0;

    }

    public boolean parseString(String date) {
        Pattern r = Pattern.compile(datePattern);
        Matcher m = r.matcher(date);
        if (m.find()) {
            Year = Integer.parseInt(m.group(1));
            Month = Integer.parseInt(m.group(2));
            Day = Integer.parseInt(m.group(3));
            if (check()) {
                return true;
            }
            else {
                return false;
            }

        } else {
            return false;
        }
    }

    private String dateToString() {
        if (!validity) {return "";}
        String result =  Integer.toString(Year) + "-";
        if (Month > 9) {
            result += Integer.toString(Month) + "-";
        }
        else {
            result +=  "0" + Integer.toString(Month) + "-";
        }
        if (Day > 9) {
            result += Integer.toString(Day) ;
        }
        else {
            result +=  "0" + Integer.toString(Day) ;
        }
        return result;
    }

    private boolean check() {
        if (Year < 2017 || Year > 2022) {
            return false;
        }
        if (Month < 1 || Month > 12) {
            return false;
        }
        if (!checkDay()) {
            return false;
        }
        return true;
    }

    private boolean checkDay() {
        if (Month != 2) {
            if (Day < 1 || Day > daysInMonth[Month - 1]) {
                return false;
            }
        } else {
            int FebDays = 0;
            if (Year % 4 == 0) {
                FebDays = 29;
            } else {
                FebDays = 28;
            }
            if (Day < 1 || Day > FebDays) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        fwDate d = new fwDate("04.14.2030");
    }
}
