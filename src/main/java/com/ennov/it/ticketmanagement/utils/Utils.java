package com.yaayi.gschool.utils;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.yaayi.gschool.filtre.RetourData;
import com.yaayi.gschool.gschools.entity.Bulletin;
import com.yaayi.gschool.gschools.entity.EleveEvaluation;
import com.yaayi.gschool.gschools.entity.InfoBulletin;
import com.yaayi.gschool.gschools.entity.NoteBulletin;
import com.yaayi.gschool.gschools.model.emp.EmploiDuTemp;
import com.yaayi.gschool.gschools.model.entity.Param;
import com.yaayi.gschool.gschools.pojo.Bullo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Utils {

  public static EleveEvaluation sur20(EleveEvaluation e){
      double sur=e.getIdNote().getTotal();
      if(sur==20)return e;
      double r=20/sur;
      e.getIdNote().setTotal(20);
      e.getIdNote().setNote(e.getIdNote().getNote()*r);
      return e;
  }
    public static String apreciation(double note){
        if(note>0){
            double d=note/20.0;
            if(d>=0.9)return "Excellent";
            if(d>=0.8)return "Très bien";
            if(d>=0.7)return "Bien";
            if(d>=0.6)return "Assez-bien";
            if(d>=0.5)return "Passable";
            if(d>=0.4)return "Insuffisant";
            if(d>=0.3)return "Très Insuffisant";
            if(d>=0.2)return "Faible";
            return "Très Faible";
        }
        return "";
    }
    public static String apreciationBulletin(double note){
        if(note>0){
            double d=note/20.0;
            if(d>=0.9)return "Excellent";
            if(d>=0.8)return "Très bien";
            if(d>=0.7)return "Bien";
            if(d>=0.6)return "Assez-bien";
            if(d>=0.5)return "Passable";
            if(d>0.35)return "Insuffisant";
            if(d<=0.35)return "Très Insuffisant";
            return "Très Faible";
        }
        return "";
    }
    public static void triBulleNoteBulletin(List<NoteBulletin> l) {
        if (l != null) {
            int longueur = l.size();
            boolean inversion;

            do {
                inversion = false;

                for (int i = 0; i < longueur - 1; i++) {
                    if (l.get(i).getMoyenne()<(l.get(i + 1).getMoyenne())) {
                        echanger(l, i, i + 1);
                        inversion = true;
                    }else {
                       l.get(i).setRang(i+1);
                        l.get(i+1).setRang(i+2);
                    }
                }
                longueur--;
            }
            while (inversion);
        }

    }

    private static void echanger(List<NoteBulletin> l, int i, int i1) {
        NoteBulletin aux = l.get(i);
        l.set(i, l.get(i1));
        aux.setRang(i1);
        l.set(i1, aux);
    }

    public static void triBulleInfoBulletin(List<InfoBulletin> l) {
        if (l != null) {
            int longueur = l.size();
            boolean inversion;

            do {
                inversion = false;

                for (int i = 0; i < longueur - 1; i++) {
                    if (l.get(i).getMoyenne()<(l.get(i + 1).getMoyenne())) {
                        echangers(l, i, i + 1);
                        inversion = true;
                    }else {
                       // l.get(i).setRang(i+1);
                        //l.get(i+1).setRang(i+2);
                    }
                }
                longueur--;
            }
            while (inversion);
            for(int i=0;i<l.size(); i++){
                l.get(i).setRang(i+1);
            }
        }

    }

    private static void echangers(List<InfoBulletin> l, int i, int i1) {
        InfoBulletin aux = l.get(i);
        l.set(i, l.get(i1));
        aux.setRang(i1);
        l.set(i1, aux);
    }
    public static String creerDossiers(String paquage, String name) {
        if(paquage.endsWith(name))return paquage;
        String fileNme = paquage + "\\" + name;
        fileNme=fileNme.replace(" ","_");
        Path path = Paths.get(fileNme);
        if (!Files.exists(path)) {
            if(fileNme.contains("\\")){
                fileNme=fileNme.replace("\\","#");
                String[] sp=fileNme.split("#");
                String ph=sp[0]+"\\"+sp[1];
                System.out.println(paquage+"===x==x==="+ph.equals(paquage)+"==="+name);
                path = Paths.get(ph);
                if (!Files.exists(path)) {
                    try {
                        Files.createDirectory(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                for(int i=2;i<sp.length;i++){
                    ph=ph+"\\"+sp[i];
                    path = Paths.get(ph);
                    if (!Files.exists(path)) {
                        try {
                            Files.createDirectory(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {

        }
        return fileNme.replace("#","\\");
    }


    public static String creerDossier(String paquage, String name) throws IOException {
        if(paquage.endsWith(name))return paquage;
        String fileNme = paquage + "\\" + name;
        fileNme=fileNme.replace(" ","_");
        Path path = Paths.get(fileNme);
        if (!Files.exists(path)) {
            if(fileNme.contains("\\")){
                fileNme=fileNme.replace("\\","#");
                String[] sp=fileNme.split("#");
                String ph=sp[0]+"\\"+sp[1];
                System.out.println(paquage+"===x==x==="+ph.equals(paquage)+"==="+name);
                    path = Paths.get(ph);
                    if (!Files.exists(path)) Files.createDirectory(path);
                    for(int i=2;i<sp.length;i++){
                        ph=ph+"\\"+sp[i];
                        path = Paths.get(ph);
                        if (!Files.exists(path)) Files.createDirectory(path);
                    }
            }
            if (!Files.exists(path))   Files.createDirectory(path);
        } else {

        }
        return fileNme.replace("#","\\");
    }

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

    public static List<List<String>> xls(String path) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));


        // Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();
        List<List<String>> fileObjectList = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // Get iterator to all cells of current row
            Iterator<Cell> cellIterator = row.cellIterator();
            List<String> r=new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                // Change to getCellType() if using POI 4.x
                CellType cellType = cell.getCellTypeEnum();

                switch (cellType) {
                    case _NONE:
                        r.add("");
                         break;
                    case BOOLEAN:
                        r.add(cell.getBooleanCellValue()+"");
                       // System.out.print(cell.getBooleanCellValue());
                       // System.out.print("\t");
                        break;
                    case BLANK:
                       r.add("");
                        break;
                    case FORMULA:
                        // Formula
                        r.add(cell.getCellFormula());
                        //System.out.print(cell.getCellFormula());
                       // System.out.print("\t");

                        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                        // Print out value evaluated by formula
                        r.add(evaluator.evaluate(cell).getNumberValue()+"");
                        //System.out.print(evaluator.evaluate(cell).getNumberValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            r.add(dateFormat.format(date));
                        } else {
                            r.add(cell.getNumericCellValue() + "");
                        }
                        break;
                    case STRING:
                        r.add(cell.getStringCellValue()+"");
                      //  System.out.print(cell.getStringCellValue());
                      //  System.out.print("\t");
                        break;
                    case ERROR:
                        r.add("");
                        //System.out.print("!");
                       // System.out.print("\t");
                        break;
                }

            }
            fileObjectList.add(r);
         }
            return fileObjectList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public static List<Bulletin> triBulleBulletin(List<Bulletin> l) {
        if (l != null) {
            int longueur = l.size();
            boolean inversion;

            do {
                inversion = false;

                for (int i = 0; i < longueur - 1; i++) {
                    if (l.get(i).getMoyenne()<(l.get(i + 1).getMoyenne())) {
                        echangerBule(l, i, i + 1);
                        inversion = true;
                    }else {
                        // l.get(i).setRang(i+1);
                        //l.get(i+1).setRang(i+2);
                    }
                }
                longueur--;
            }
            while (inversion);
            for(int i=0;i<l.size(); i++){
                l.get(i).setRang(i+1);
            }
        }
        return exceco(l);
    }

    private static void echangerBule(List<Bulletin> l, int i, int i1) {
        Bulletin aux = l.get(i);
        l.set(i, l.get(i1));
        l.set(i1, aux);
    }

    private static void echangerBulo(List<Bullo> l, int i, int i1) {
        Bullo aux = l.get(i);
        l.set(i, l.get(i1));
        l.set(i1, aux);
    }

    public static List<Bulletin> exceco(List<Bulletin> bulletins){
      List<Bulletin> aux,retour=new ArrayList<>();
      while (!bulletins.isEmpty()){
          aux=new ArrayList<>();
          Bulletin b=bulletins.get(0);
          aux.add(b);
          int rg=b.getRang();
          for (int i=1;i<bulletins.size();i++){
              if(arondi_d(b.getMoyenne())==arondi_d(bulletins.get(i).getMoyenne())){
                  aux.add(bulletins.get(i));
                  if(rg>bulletins.get(i).getRang()){
                      rg=bulletins.get(i).getRang();
                  }
              }
          }
          boolean ex=aux.size()>1;
          for(Bulletin bulletin:aux){
             bulletin.setRang(rg);
             bulletin.setEx(ex);
             retour.add(bulletin);
          }
          bulletins.removeAll(aux);
      }
      return retour;
    }

    public static List<Bullo> triBulleBulletinBulloEx(List<Bullo> bullos) {
        List<Bullo> aux,retour=new ArrayList<>();
        while (!bullos.isEmpty()){
            aux=new ArrayList<>();
            Bullo b=bullos.get(0);
            aux.add(b);
            int rg=b.getRang();
            for (int i=1;i<bullos.size();i++){
                if(arondi_d(b.getMoyene())==arondi_d(bullos.get(i).getMoyene())){
                    aux.add(bullos.get(i));
                    if(rg>bullos.get(i).getRang()){
                        rg=bullos.get(i).getRang();
                    }
                }
            }
            for(Bullo bulletin:aux){
                bulletin.setRang(rg);
                retour.add(bulletin);
            }
            bullos.removeAll(aux);
        }
        return retour;
    }


    public static List<Bullo> triBulleBulletinBullo(List<Bullo> l) {
        if (l != null) {
            int longueur = l.size();
            boolean inversion;

            do {
                inversion = false;

                for (int i = 0; i < longueur - 1; i++) {
                    if (l.get(i).getMoyene()<(l.get(i + 1).getMoyene())) {
                        echangerBulo(l, i, i + 1);
                        inversion = true;
                    }else {
                        // l.get(i).setRang(i+1);
                        //l.get(i+1).setRang(i+2);
                    }
                }
                longueur--;
            }
            while (inversion);
            for(int i=0;i<l.size(); i++){
                l.get(i).setRang(i+1);
            }
        }
        return triBulleBulletinBulloEx(l);
    }

    public static RetourData<Boolean> writeObjet(Param equipement, String name){
        List<String> tx = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        RetourData<Boolean> retour;
        try {
            //creerDossier("json");
            String jsonString = mapper.writeValueAsString(equipement);
            Path fichier = Paths.get( name);
            tx.add(jsonString);
            Files.write(fichier, tx, Charset.forName("UTF-8"));
            retour=new RetourData<>("200","true");
            retour.setDescription("");
        } catch (IOException e) {
            e.printStackTrace();
            retour=new RetourData<>("0","true");
            retour.setDescription(e.getMessage());
        }
        return retour;
    }
    public static RetourData<Boolean> writeEmploi(EmploiDuTemp equipement, String name){
        List<String> tx = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        RetourData<Boolean> retour;
        try {
            //creerDossier("json");
            String jsonString = mapper.writeValueAsString(equipement);

          /*URI uri = ClassLoader.getSystemResource("com/stackoverflow/json").toURI();
            String mainPath = Paths.get(uri).toString();
            Path path = Paths.get(mainPath ,name+".json");*/

            Path fichier = Paths.get( name);
            tx.add(jsonString);
            Files.write(fichier, tx, Charset.forName("UTF-8"));
            retour=new RetourData<>("200","true");
            retour.setDescription("");
        } catch (IOException e) {
            e.printStackTrace();
            retour=new RetourData<>("0","true");
            retour.setDescription(e.getMessage());
        }
        return retour;
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
