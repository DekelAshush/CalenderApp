package calenderapp.calenderapp;

import java.time.YearMonth;

public class Logic {


    // Method to get number of days in month
    public int getNumberOfDaysInMonth(int year,int month)
    {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return daysInMonth;
    }

    public String monthName(int month){
        String monthName = "";
        switch (month){
            case 1:
                monthName="January";
                break;
            case 2:
                monthName="February";
                break;
            case 3:
                monthName="March";
                break;
            case 4:
                monthName="April";
                break;
            case 5:
                monthName="May";
                break;
            case 6:
                monthName="June";
                break;
            case 7:
                monthName="July";
                break;
            case 8:
                monthName="August";
                break;
            case 9:
                monthName="September";
                break;
            case 10:
                monthName="October";
                break;
            case 11:
                monthName="November";
                break;
            case 12:
                monthName="December";
                break;
            default:
        }
    return monthName;

    }



}
