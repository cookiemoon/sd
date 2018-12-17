package Shared;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.sql.Date;

public class inputUtil implements Serializable {
    static final int DAY_START	= 1;
    static final int MONTH_START= 0;
    static final int MONTH_END	= 11;
    static final int DAY_END	= 31;
    static final int YEAR_START = 1900;
    static final int YEAR_END   = Year.now().getValue();
    static Scanner scan = new Scanner(System.in);
    static String[] MONTHS = {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};


    static public int StringToInt(String s, String param) throws BadInput {
        try {
            int res = Integer.parseInt(s);
            if(res < 0) {
                throw new BadInput(param, "invalid negative value");
            }
            return res;
        } catch (Exception e) {
            throw new BadInput(param, "the parameters entered were not numbers.");
        }
    }

    static public int StringToInt(String s, String param, int lower, int upper) throws BadInput {
        try {
            int res = Integer.parseInt(s);
            if(res < lower || res > upper) {
                throw new BadInput(param, param + " was not between limit of " + String.valueOf(lower) + "-"+ String.valueOf(upper));
            }
            return res;
        } catch (Exception e) {
            throw new BadInput(param, "the parameters entered were not numbers.");
        }
    }

    static public boolean notEmptyOrNull(String... strings) {
        for(String s : strings) {
            if(s==null || s.equals(""))
                return false;
        }
        return true;
    }

    static public int oldOrNew(int old_i, int new_i) {
        if(new_i <= 0)
            return old_i;
        return new_i;
    }

    static public Object nullOrNew(Object old_o, Object new_o) {
        if(new_o == null)
            return old_o;
        return new_o;
    }

    static public String oldOrNew(String old_s, String new_s) {
        if(new_s == null || new_s.equals("") || new_s.equals(old_s)) {
            return old_s;
        } else {
            return new_s;
        }
    }

    static public Calendar toCalendar(String date, String param) throws BadInput {
        try {
            String[] parts = date.split("/");
            Calendar c = new GregorianCalendar();
            int day = Integer.parseInt(parts[0]);
            if(day < DAY_START || day > DAY_END)
                throw new BadInput(param, "invalid day in date.");
            int month = Integer.parseInt(parts[1])-1;
            if(month < MONTH_START || month > MONTH_END)
                throw new BadInput(param, "invalid month in date.");
            int year = Integer.parseInt(parts[2]);
            if(year < YEAR_START || year > YEAR_END)
                throw new BadInput(param, "invalid year in date.");
            c.set(year,month,day);
            return c;
        } catch (NumberFormatException e) {
            throw new BadInput(param, "the parameters entered were not numbers.");
        } catch (IndexOutOfBoundsException e) {
            throw new BadInput(param, "not enough numbers entered (did you properly separate then with '/'?).");
        }
    }

    static public Calendar toCalendar(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }

    static public Date toDate(Calendar cal) {
        Date date = new Date(cal.getTimeInMillis());
        return date;
    }

    static public String oldStrOrNew(String msg, String old) {
        System.out.print(msg);
        
        String str;
        str = scan.nextLine().trim();
        if(str.isEmpty())
            return old;

        return str;
    }
    
    /**
     * @param msg - Message to print before asking for input
     * @return String read from keyboard
     * @author Victor Carvalho
     */
    public static String promptStr(String msg) {
        System.out.print(msg);
        
        String str;
        do {
            str = scan.nextLine().trim();
        } while (str.isEmpty());

        return str;
    }

    /**
     * @param msg - Print the message and prompts with ">>" in a newline
     * @return Int read from keyboard
     * @author Victor Carvalho
     */
    public static int promptInt(String msg) {
        System.out.print(msg);
        
        int n = -1;
        do {
            n = scan.nextInt();
        } while(n == -1);

        return n;
    }
    
    /**
     * Prompts for an int using [lower, upper] including
     * @param upper upper bound check
     * @param lower lower bound check
     */
    public static int promptIntBound(int upper, int lower, String msg, boolean nullable) {
        int val;
        do {
            val = promptInt(msg);
            if(nullable && val == 0)
                break;
        } while (val > upper || val < lower);
        return val;
    }

    /**
     * 
     * @param msg
     * @param options
     * @return
     */
    public static int promptIntOrStr(String msg, String[] options) {
        int val = -1;
        String month;
        do {
            month = promptStr(msg);
            if (month.matches("[0-9]+")) {
                val = Integer.parseInt(month);
            } else {
                for (int i = 0; i < MONTHS.length; i++) {
                    if (MONTHS[i].equals(month.toLowerCase())) {
                        val = i;
                        break;
                    }
                }

            }
        } while (val < 0 || val > options.length);
        return val;
    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
            e.getStackTrace();
        }
    }

    /**
     * 
     * @param str - The string we want to separate it's items by
     * @param token - the token we will use to separate each element
     * @return
     */
    public static List<String> separateBy(String str, String token) {
        List<String> items = Arrays.asList(str.split(token));
        
        items = items.stream().map(String::trim).collect(Collectors.toList());

        return items;
    }

    public static Calendar promptDate(String msg, boolean nullable) {
        System.out.println(msg);
        int year = promptIntBound(YEAR_END, YEAR_START, "Year: ", nullable);
        int month, day;
        if(year == 0) {
            return null;
        } else {
            month = promptIntOrStr("Month: ", MONTHS);
            day = promptIntBound(DAY_END, DAY_START, "Day: ", false);
        }

        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        
        return c;
    }

    public static boolean promptYesNo(String msg) {
        String answer;
        do {
            answer = inputUtil.promptStr(msg).toLowerCase();
        } while (!answer.equals("y") && !answer.equals("n"));

        if(answer.equals("y"))
            return true;
        else
            return false;
    }

    public static String hashedPass(String passwd) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(
        passwd.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
        String hex = Integer.toHexString(0xff & encodedhash[i]);
        
        if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
    
        return hexString.toString();
    }

    public static String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (date != null)
            return sdf.format(date.getTime());
        else
            return "Not specified";
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date.getTime());
    }

    public static List<String> promptListStr() {
        List<String> terms = new ArrayList<>();
        while(true) {
            String term = inputUtil.promptStr("Term (\"/end\" to stop): ");
            if (term.toLowerCase().equals("/end"))
                break;
            terms.add(term);
        }
        return terms;
    }
}
