package com.yaayi.gschool.utils;


import com.yaayi.gschool.gschools.entity.Eleve;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class S {
    public static int randum(int inf, int sup) {
        int i = inf + (int) (Math.random() * (sup - inf + 1));
        return i;
    }
    public static int randum(int inf, int sup, List<Integer> priveDe) {
        int i = inf + (int) (Math.random() * (sup - inf + 1));
        while (priveDe.contains(i)){
            i = inf + (int) (Math.random() * (sup - inf + 1));
        }
        return i;
    }
    public static String filemane(String ph) {
        if (ph != null && ph.contains("/")) {
            return ph.substring(ph.lastIndexOf("/") + 1).replace("+", " ").replace("%", " ");
        }
        return ph;
    }

    public static String filemane2(String ph) {
        String ns = filemane(ph);

        return ns.contains(".") ? ns.substring(0, ns.lastIndexOf(".")) : ns;
    }
    public static String toUperCaseFirstChar(String s) {
        while (s.length() > 0 && s.charAt(0) == ' ')
            s = s.substring(1);
        if (s.length() == 0)
            return s;
        String sa = s.toLowerCase();
        sa = (sa.charAt(0) + "").toUpperCase() + sa.substring(1);
        return sa;
    }

    public static String nom() {
        String v[] = { "a", "e", "i", "o", "u", "ou" }, k = "bcdfghjklmnprstvwyz", n = "", p = "";
        int x = randum(2, 5);
        for (int i = 0; i < x; i++) {
            n = n + k.charAt(randum(0, k.length() - 1)) + v[randum(0, v.length - 1)];
        }
        x = randum(2, 5);
        for (int i = 0; i < x; i++) {
            p = p + k.charAt(randum(0, k.length() - 1)) + v[randum(0, v.length - 1)];
        }
        return n.toUpperCase() + " " + toUperCaseFirstChar(p);
    }

    public static String nomtel() {
        String v[] = { "91", "90", "92", "93", "70", "99", "98", "97", "79" }, k = "0123456789", n = "";
        int x = randum(0, v.length - 1);
        n = v[x];
        for (int i = 0; i < 6; i++) {
            n = n + k.charAt(randum(0, k.length() - 1));
        }
        return n;
    }

  
    public static Timestamp timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    public static String today_hms() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String dat = simpleDateFormat.format(new Date());
        return dat;
    }
    public static String todayhms() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dat = simpleDateFormat.format(new Date());
        return dat;
    }

    public final static Date assembleDate(int years, int months, int days) throws ParseException {
        LocalDate date3 = LocalDate.of(years, months, days);
        Date date = Date.from(date3.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //Affiche.info("Date urgentoooo "+ new Date(date.getTime()));
        return new Date(date.getTime());
    }
    public final static int getPartOfDate(Date date,char charcacter){

        int leRetour = -1;   Calendar calDate = Calendar.getInstance();
        if(date != null )
        {

            calDate.setTime(date);
            switch (charcacter){
                case 'd':
                    leRetour = calDate.get(Calendar.DAY_OF_MONTH);
                    break;
                case 'm':
                    leRetour = calDate.get(Calendar.MONTH)+1;
                    break;
                case 'y':
                    leRetour = calDate.get(Calendar.YEAR);
                    break;
                case 'h':
                    leRetour = calDate.get(Calendar.HOUR_OF_DAY);
                    break;
                case 'c':
                    leRetour = calDate.get(Calendar.MINUTE);
                    break;
                case 's':
                    leRetour = calDate.get(Calendar.SECOND);
                    break;
                case 'l':
                    leRetour = calDate.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
        }
        return leRetour;
    }
    public final static Date addToDate(Date depart, int ans, int mois, int jours, int heures, int minutes , int secondes ) throws ParseException {

        Date dateObtenu  = null, dp = assembleDate(getPartOfDate(depart, 'y'), getPartOfDate(depart, 'm'), getPartOfDate(depart, 'd'));//new Date(depart.getTime());
        if (dp != null){
            LocalDateTime ll =   new java.sql.Timestamp(depart.getTime()).toLocalDateTime();//((Date)depart).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            ll = (ans >= 0)? ll.plusYears(ans) : ll.minusYears(Math.abs(ans));
            ll = (mois >= 0)? ll.plusMonths(mois) : ll.minusMonths(Math.abs(mois));
            ll = (jours >= 0)? ll.plusDays(jours) : ll.minusDays(Math.abs(jours) );
            ll = (heures >= 0)? ll.plusHours(heures) : ll.minusHours(Math.abs(heures));
            ll = (minutes >= 0)? ll.plusMinutes(minutes) : ll.minusMinutes(Math.abs(minutes));
            ll = (secondes >= 0)? ll.plusSeconds(secondes) : ll.minusSeconds(Math.abs(secondes));
            dateObtenu = Date.from(ll.atZone(ZoneId.systemDefault()).toInstant());
        }
        //Affiche.info(" info "+dateObtenu);
        return dateObtenu;
    }

    public static Eleve eleve() throws ParseException {
        Eleve eleve=new Eleve();
        String[] np=nom().split(" ");
        eleve.setNom(np[0]);
        eleve.setPrenom(np[1]);
       eleve.setSex(S.randum(0,1)==1?"M":"F");
        eleve.setNueroMatricule(S.randum(100000,999999)+"");
        eleve.setNationalite("Togolaise");
        eleve.setAdresse(nomtel());
        eleve.setDateNaissance((Timestamp) addToDate(new Date(),-randum(11,20),0,0,0,0,0));
        return eleve;
    }

    private static String dateToString(Date addToDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String dat = simpleDateFormat.format(addToDate);
        return dat;
    }
    public static boolean isInt(String chaine) {
        boolean valeur = false;
        if( !chaine.equals("")){
            valeur = true;
            char[] tab = chaine.toCharArray();
            for (char caract : tab) {
                if (!Character.isDigit(caract) && valeur) {
                    valeur = false;
                }
            }
        }
        return valeur;
    }
    public static String arrondi(Double xx){
        String x=xx==null?"0":xx+"";
        if(x==null)return "0";
        if (isInt(x.replace(".", ""))) {
            if (x.contains(".")) {
                int i = x.indexOf(".");
                boolean oui = x.endsWith("%");
                x = x.replace("%", "");
                String av = x.substring(0, i), ap = x.substring(i + 1);
                if (ap.length() == 1) {
                    return av + "." + ap + "0" + (oui ? "%" : "");
                }
                if (x.length() > i + 3) {
                    try {
                        int e = Integer.parseInt(String.valueOf(x.charAt(i + 3)));
                        if (e < 5) {
                            x = x.substring(0, i + 3);
                        } else {
                            double vd = Double.parseDouble(x.substring(0, i + 3)) + 0.01;
                            return String.valueOf(vd);
                        }

                        if (oui) x = x + "%";
                    } catch (Exception e) {

                    }
                }
            }
            return x;
        } else {
            return x;
        }
    }

    public static String end(String s){
        while (s.endsWith(" "))s=s.substring(0,s.length()-1);
        return s;
    }

    public static String start(String s){
        while (s.startsWith(" "))s=s.substring(1);
        return s;
    }
}
