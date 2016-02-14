package com.college.snmp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateConvertHex {

    private final static String HEX_DIGITS = "0123456789abcdef";

    public static String filterHex(String hexDate) {

        StringBuilder formatDate = new StringBuilder();
        for(int i = 0; i < hexDate.length(); i++){
            if(HEX_DIGITS.contains(String.valueOf(hexDate.charAt(i)))){
                formatDate.append(hexDate.charAt(i));
            }
        }
        return formatDate.toString();
    }

    public static String hexToAscii (String hex)
    {
        String formatHex = filterHex(hex.toLowerCase());
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < formatHex.length(); i+=2) {
            String str = formatHex.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        return String.valueOf(output);
    }

    public static String convertDecimal(String hexDate) {

        String formatDate = filterHex(hexDate.toLowerCase());
        String year = formatDate.substring(0, 4);
        String month = formatDate.substring(4, 6);
        String day = formatDate.substring(6, 8);
        String hour = formatDate.substring(8, 10);
        String minute = formatDate.substring(10, 12);
        String second = formatDate.substring(12, 14);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year, 16));
        cal.set(Calendar.MONTH, (Integer.parseInt(month, 16)-1));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day, 16));
        cal.set(Calendar.HOUR, Integer.parseInt(hour, 16));
        cal.set(Calendar.MINUTE, Integer.parseInt(minute, 16));
        cal.set(Calendar.SECOND, Integer.parseInt(second, 16));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(cal.getTime()). toString();
    }

    public static void main(String args[])throws IOException{

        DateConvertHex DateConvert= new DateConvertHex();
        String stringDate = DateConvert.convertDecimal("07:de:06:11:12:04:0c:00");
        System.out.println(stringDate);
    }
}