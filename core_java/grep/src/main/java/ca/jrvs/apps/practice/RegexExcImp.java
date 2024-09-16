package ca.jrvs.apps.practice;
import java.util.regex.*;

public class RegexExcImp implements RegexExc {

    @Override
    public boolean matchJpeg(String filename) {
        String regex = "([^\\s]+(\\.(?i)(jpe?g))$)";
        Pattern p = Pattern.compile(regex);

        if (filename == null) {
            return false;
        }

        Matcher m = p.matcher(filename);
        return m.matches();
    }

    @Override
    public boolean mathcIP(String ip) {
        String IPv4 =
            "^([0-9]?[0-9]?[0-9]?)\\." +
            "([0-9]?[0-9]?[0-9]?)\\." +
            "([0-9]?[0-9]?[0-9]?)\\." +
            "([0-9]?[0-9]?[0-9]?)$";

        Pattern p = Pattern.compile(IPv4);

        if (ip == null) {
            return false;
        }

        Matcher m = p.matcher(ip);
        return m.matches();
    }

    @Override
    public boolean isEmptyLine(String line) {
        if (line.replaceAll("\\s", "").isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }
}
