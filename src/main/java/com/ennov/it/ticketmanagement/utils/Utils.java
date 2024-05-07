package com.ennov.it.ticketmanagement.utils;



import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Utils {


    public static List<List<String>> integrationFichier(String repertoireFichier) throws Exception {
        File f = new File("C:\\Users\\SNPT\\Downloads\\LISTE DES ELEVES 2022-2023(1).xls");
        if (f.exists() == false) {
//            throw new Exception("Le fichier n'a pas été retrouvé sur le serveur. "+repertoireFichier);
        }
        List<List<String>> fileObjectList = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(f.getAbsolutePath()));
        String ligne;
        while ((ligne = in.readLine()) != null) {
            String[] tabSpliter = ligne.trim().split(";");

            fileObjectList.add(Arrays.asList(tabSpliter));
        }
        return fileObjectList;
    }

    public static boolean isInt(String chaine) {
        if (chaine.equals("")) {
            return false;
        }

        for (char caract : chaine.toCharArray()) {
            if (!Character.isDigit(caract)) {
                return false;
            }
        }

        return true;
    }
    public static String arondi(double xd) {
        String x = String.valueOf(xd);
        System.out.println("=====arondi======"+x);
        int decimalIndex = x.indexOf(".");
        if (decimalIndex != -1) {
            boolean hasPercentage = x.endsWith("%");
            x = x.replace("%", "");
            if (x.contains(".")) {
                // Création d'un BigDecimal avec une valeur initiale
                BigDecimal nombreDecimal = new BigDecimal(x);

                // Arrondi à 2 chiffres après la virgule avec RoundingMode.HALF_UP
                BigDecimal resultatArrondi = nombreDecimal.setScale(2, RoundingMode.HALF_UP);
                return resultatArrondi.toEngineeringString();

            }
            if (hasPercentage) {
                x += "%";
            }
        }
        return x;
    }
    public String arrondi(String x){
        if(x==null)return "0";
        if (isInt(x.replace(".", ""))) {
            x=x.replace(" ","");
            if (x.contains(".")) {
                // Création d'un BigDecimal avec une valeur initiale
                BigDecimal nombreDecimal = new BigDecimal(x);

                // Arrondi à 2 chiffres après la virgule avec RoundingMode.HALF_UP
                BigDecimal resultatArrondi = nombreDecimal.setScale(2, RoundingMode.HALF_UP);
                return resultatArrondi.toEngineeringString();

            }
            return x;
        } else {
            return x;
        }
    }


    public static double arondi_d(double xd){
        String x= String.valueOf(xd);

        if(x.contains(".")&&isInt(x.replace(".",""))){
            int i=x.indexOf(".");
            boolean oui=x.endsWith("%");
            x=x.replace("%","");
            String av=x.substring(0,i),ap=x.substring(i+1);
            if(ap.length()==1){
                return Double.parseDouble(av+"."+ap+"0");
            }
            if(x.length()>i+3){
                int e=Integer.parseInt(String.valueOf(x.charAt(i+3)));
                if (e<5){
                    x=x.substring(0,i+3);
                    return Double.parseDouble(x);
                }else {
                    double vd= Double.parseDouble(x.substring(0,i+3))+0.01;
                    return vd;
                }

            }
        }
        return xd;
    }


    public static String trime(String s){
      return s.replace(".","").replace(" ","_");
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
    public static String today(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String dd=simpleDateFormat.format(new Date());
        return dd.replace(" ","T")+"Z";
    }
    public static String toStrin(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String dd=simpleDateFormat.format(date);
        return dd.replace(" ","T");
    }
    public static String formater(String date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String dd=simpleDateFormat.format(toDate(date));
        return dd.replace(" ","T");
    }

    public static Date toDate(String date){
        if(date==null||date.equals(""))date="31/12/1900";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/YYYY");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String twoWeek() throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date=new Date();
        date=addToDate(date,0,0,-11,0,0,0);
        String dd=simpleDateFormat.format(date);
        return dd.replace(" ","T")+"Z";
    }

    public static String twoMinutek() throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date=new Date();
        date=addToDate(date,0,0,0,0,-15,0);
        String dd=simpleDateFormat.format(date);
        return dd.replace(" ","T")+"Z";
    }
    public static String typeFile(String fileName) {
        if(fileName.contains(".")){
            String type=fileName.substring(fileName.lastIndexOf(".")+1);
            return type;
        }
        return "";
    }

    public static String fileName(String fileName) {
        String fnam=fileName.substring(fileName.lastIndexOf("/")+1).replace(typeFile(fileName),"");
        return fnam;
    }
}
