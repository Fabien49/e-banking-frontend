/*package com.fabienIT.escaladefriendsp6ocr.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="dates")
public class Dates {


    @Id
    @Column(name = "commentaire_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private static void getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
    }


}*/
