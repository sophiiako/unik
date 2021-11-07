import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date {
    private static final Integer[] daysInMonth = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static String datePattern = "(\\d+)\\.(\\d+)\\.(\\d+)";
    private int Day;
    private int Month;
    private int Year;
    public String resultDate;
    private boolean validity;

    public Date(int day, int month, int year) {
        if (check()) {
            validity = true;
        } else {
            validity = false;
        }
        resultDate = dateToString();
    }

    public Date(String date) {
        if (parseString(date)) {
            validity = true;
        } else {
            validity = false;
        }
        System.out.println(Day);
        resultDate = dateToString();
    }

    public boolean parseString(String date) {
        Pattern r = Pattern.compile(datePattern);
        Matcher m = r.matcher(date);
        if (m.find()) {
            Day = Integer.parseInt(m.group(1));
            Month = Integer.parseInt(m.group(2));
            Year = Integer.parseInt(m.group(3));
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
        String result = "";
        if (Day > 9) {
            result += Integer.toString(Day) + ".";
        }
        else {
            result +=  "0" + Integer.toString(Day) + ".";
        }
        if (Month > 9) {
            result += Integer.toString(Month) + ".";
        }
        else {
            result +=  "0" + Integer.toString(Month) + ".";
        }
        result += Integer.toString(Year);
        return result;
    }

    private boolean check() {
        if (Year < 2017 || Year > 2021) {
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
       Date d = new Date("04.14.2030");
    }
}
