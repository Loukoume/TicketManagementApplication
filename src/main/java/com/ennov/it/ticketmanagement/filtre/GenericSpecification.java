package com.yaayi.gschool.filtre;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author k.eklou & loukoume 10/05/2022
 * @param <T>
 */
public class  GenericSpecification<T> implements Specification<T> {

    private Class<T> t,p;
    private  Requete requete;
    private  Where where;
    private Sort sort;

    public GenericSpecification(Class<T> t, Requete requete) {
        this.t = t;
        // where = null;
        this.requete = requete;
        this.setSort();
        System.out.println("====wheres==="+requete.getWhere());
    }

    public GenericSpecification(Class<T> t, Requete requete, Where filtre) {
        this.t = t;
        this.where = filtre;
        this.requete = requete;
        this.setSort();
    }

    private Class<?> retrieveObjectClass(String searchProperty) {
        // System.out.println(t.getSimpleName()+"=========searchProperty========"+searchProperty);
        if(searchProperty.split(":").length>2){
            return retrieveObjectClassN(searchProperty);
        }
        if (searchProperty != null && searchProperty.contains(":")) {
            //System.out.println("===filtre classe=="+t);
            Field[] fields = t.getDeclaredFields();
            String propertyName = searchProperty.split(":")[0];
            for (Field f : fields) {
                if (f.getName().equals(propertyName)) {
                    return f.getType();
                }
            }
        }
        return null;
    }
    private Class<?> retrieveObjectClassN(String searchProperty) {
        String[] serch=searchProperty.split(":");
        Class c=retrieveObjectClass(serch[0]+":"+serch[1]);
        if(c!=null)t=c;
        else return t;
        // System.out.println(serch[0]+":"+serch[1]+"====debout=="+c+"======"+t);
        for(int i=1;i<serch.length-1;i++){
            //System.out.println(serch[i]+":"+serch[i+1]+"==t.getSimpleName============"+t.getSimpleName()+"===="+c.getSimpleName());
            if(c!=null) {
                t=c;
                // System.out.println(serch[i]+":"+serch[i+1]+"==t.getSimpleName============"+t.getSimpleName());
                c=retrieveObjectClass(serch[i]+":"+serch[i+1]);

            }
        }
        if(c!=null){
            //  where.setName(serch[serch.length-1]);
            t=c;
        }
        // System.out.println(c+"====clss final ob=="+c);
        return c;
    }

    public static  Class<?> retrieveObjectClass2(String searchProperty,Class t) {
        // System.out.println(t.getSimpleName()+"=========searchProperty========"+searchProperty);

        if (searchProperty != null && searchProperty.contains(":")) {
            Field[] fields = t.getDeclaredFields();
            String propertyName = searchProperty.split(":")[0];
            for (Field f : fields) {
                //  System.out.println(t.getSimpleName()+"=====f.getName()====="+f.getName());
                if (f.getName().equals(propertyName)) {
                    return f.getType();
                }
            }
        }
        return null;
    }

    private void setSort() {
       // System.out.println(requete.getSort()+"===sortr=="+requete.getSort().getAttribute());
        if (requete != null && requete.getSort() != null && requete.getSort().getOrder() != null) {
            this.sort = requete.getSort().getOrder().equals("asc")
                    ? Sort.by(requete.getSort().getAttribute()).ascending() :
                    Sort.by(requete.getSort().getAttribute()).descending();
        }
    }

    public Sort getSort() {
        return this.sort;
    }

    public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Predicate filtrer(Root<T> root, CriteriaBuilder builder) {
        where.setValue(where.getValue().replaceAll("  ", " "));
        String format = getDateFormat(where.getValue());
        System.out.println(format+"==format=="+where.getName()+"======field======"+where.getValue()+"===="+where.getAction());
        try {
            if (format != null||where.getName().contains("date")) {
                return searchDate(root, builder, format);
            } else if (where.getAction().equalsIgnoreCase(">=")) {
                return builder.greaterThanOrEqualTo(
                        root.get(where.getName()), where.getValue());
            } else if (where.getAction().equals("<=")) {
                return builder.lessThanOrEqualTo(
                        root.get(where.getName()), where.getValue());
            } else if (where.getAction().equals("=")) {
                return builder.equal(root.get(where.getName()), where.getValue());
            } else if (where.getAction().contains("LIKE")) {
                return this.filterByLikeAction(root, builder);
            }
            else if (where.getAction().equals("notnull")) {
                return this.filterByLikeAction(root, builder);
            }
            else if (where.getAction().equals("null")) {
                return this.filterByLikeAction(root, builder);
            }  else if (where.getAction().equals("true")||where.getAction().equals("false")) {

                return this.filterByLikeAction(root, builder);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Predicate searchObject(Root<T> root, CriteriaBuilder builder, Field field) {
        String propriete = where.getObjectPropriete();
        if (where.getAction().equals("=") && propriete.equals(field.getName())) {
            return builder.equal((root.get(where.getObjectObj()).get(propriete)),  where.getValue());
        }
        if (propriete != null && propriete.equals(field.getName())) {
            return builder.like(builder.upper(root.get(where.getObjectObj()).get(propriete)), "%" + where.getValue().toUpperCase() + "%");
        }
        return null;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> queryss, CriteriaBuilder builder) {
        if (where != null) {
            Class obj = retrieveObjectClass(where.getName());
            if (obj == null) {
                return this.filtrer(root, builder);
            } else {
                final List<Predicate> predicates = new ArrayList<>();
                String string[]=where.getName().split(":");
                Join<T,T> join=null;
                for(int i=0;i< string.length-1;i++){
                    join =join==null? root.join(string[i],JoinType.LEFT):join.join(string[i],JoinType.LEFT);
                }
                // System.out.println("====kokok=="+where);
                switch (where.getAction()) {
                    case "LIKEDEBUT":
                        predicates.add(builder.like(builder.upper(join.get(string[string.length-1])), where.getValue().toUpperCase()));
                        break;
                    case "true":
                        predicates.add(builder.isTrue(join.get(string[string.length-1])));
                        break;
                    case "false":
                        predicates.add(builder.isFalse(join.get(string[string.length-1])));
                        break;
                    case "notnull":
                        predicates.add(builder.isNotNull(join.get(string[string.length-1])));
                        break;
                    case "null":
                        predicates.add(builder.isNull(join.get(string[string.length-1])));
                        break;
                    case "LIKEFIN":
                        predicates.add(builder.like(builder.upper(join.get(string[string.length-1])), "%"+String.valueOf(where.getValue().toUpperCase())));
                        break;
                    case "LIKEORDER":
                        predicates.add(builder.like(builder.upper(join.get(string[string.length-1])),likeOrder(String.valueOf(where.getValue().toUpperCase()))));
                        break;
                    case "=":
                        System.out.println(where.getValue()+"===getName======="+where.getName()+"=====//-"+where.getAction()+"####"+where.getAction().length()+"####");
                        predicates.add(builder.equal(join.get(string[string.length-1]),where.getValue()));
                        break;
                    case "IN":
                        List<String> values = Arrays.asList(where.getValue().split(","));
                        Expression<Integer> expression = join.get(string[string.length - 1]).as(Integer.class); // Assurez-vous que le type est Integer
                        List<Integer> intValues =where.getValue().isEmpty()?new ArrayList<>(): values.stream().map(e -> Integer.parseInt(e.trim())).collect(Collectors.toList());
                        return expression.in(intValues);

                    default:
                        predicates.add(builder.like(builder.upper(join.get(string[string.length-1])), "%"+String.valueOf(where.getValue().toUpperCase())+"%"));
                        break;
                }

                System.out.println(where.getValue()+"===getNaBuildeme======="+where.getName()+"=====//-"+where.getAction()+"####"+builder.and(predicates.toArray(new Predicate[predicates.size()]))+"####");

                // predicates.add(builder.equal(originRoot.get("numeroEntreprise"), "777E-E1"));
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                //System.out.println("===predicates======="+predicates);

               /* Field[] flds = obj.getDeclaredFields();
                for (Field f : flds) {
                   try {
                       Predicate p= searchObject(root, builder, f);
                       if(p!=null) {return p;}
                   }catch (Exception e){
                     e.printStackTrace();
                   }
                }*/



            }
        }
        return null;
    }
    public Specification<T> and(Specification<T> other) {
        return Specification.super.and(other);
    }
    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }
    private Predicate searchDate(Root<T> root, CriteriaBuilder builder, String format) {
        try {
            Date dateFormater = new SimpleDateFormat(format).parse(where.getValue());
            Expression<Date> dateExpression = root.get(where.getName()).as(Date.class);
            System.out.println(where.getAction()+"$========dateExpression========"+where.getValue());
            if (where.getAction().equalsIgnoreCase("=")) {
                return builder.between(dateExpression, dateFormater, getDateFin(dateFormater));
            }
            if (where.getAction().equalsIgnoreCase("<=")) {
                return builder.lessThanOrEqualTo(dateExpression, dateFormater);
            }
            if (where.getAction().equalsIgnoreCase(">=")) {
                return builder.greaterThanOrEqualTo(dateExpression, dateFormater);
            }
            if (where.getAction().equalsIgnoreCase("notnull")) {
                return builder.isNotNull(dateExpression);
            }
            if (where.getAction().equalsIgnoreCase("null")) {
                return builder.isNull(dateExpression);
            }
        } catch (ParseException ex) {
            Logger.getLogger(GenericSpecification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Predicate filterByLikeAction(Root<T> root, CriteriaBuilder builder) {
        // System.out.println(where.getAction()+"===class string==="+(String.class == root.get(where.getName()).getJavaType()));
        if (String.class == root.get(where.getName()).getJavaType()) {
            String valueToUpper = where.getValue().toUpperCase();
            Expression<String> expression = builder.upper(root.get(where.getName()));
            System.out.println(where.getAction()+"===idtrueidi==="+expression);
            switch (where.getAction()) {
                case "LIKEDEBUT":
                    return builder.like(expression, valueToUpper + "%");
                case "true":
                    Expression<Boolean>   expressions = builder.isTrue(root.get(where.getName()));
                    System.out.println(where.getAction()+"===ididi==="+where.getName());
                    return builder.isTrue(expressions);
                case "false":
                    expressions = builder.isFalse(root.get(where.getName()));
                    return builder.isFalse(expressions);
                case "notnull":
                    return builder.isNotNull(expression);
                case "null":
                    return builder.isNull(expression);
                case "LIKEFIN":
                    return builder.like(expression, "%" + valueToUpper);
                case "LIKEORDER":
                    return builder.like(expression, likeOrder(valueToUpper));
                case "IN":
                    List<String> values = Arrays.asList(valueToUpper); // Convertir le tableau en liste
                    return expression.in(values);
                default:
                    return builder.like(expression, "%" + valueToUpper + "%");
            }
        } else {
            Expression<String> expression = builder.upper(root.get(where.getName()));
            String valueToUpper = where.getValue().toUpperCase();
            switch (where.getAction()) {
                case "true":
                    Expression<Boolean>   expressions = builder.isTrue(root.get(where.getName()));
                    System.out.println(where.getAction()+"===ididi==="+where.getName());
                    return builder.isTrue(expressions);
                case "false":
                    Expression<Boolean>   expressios = builder.isFalse(root.get(where.getName()));
                    //  expressions = builder.isFalse(root.get(where.getName()));
                    return builder.isFalse(expressios);
                case "notnull":
                    return builder.isNotNull(expression);
                case "null":
                    return builder.isNull(expression);
                case "IN":
                    List<String> values = Arrays.asList(valueToUpper); // Convertir le tableau en liste
                    return expression.in(values);
                default:
                    return builder.equal(root.get(where.getName()), where.getValue());
            }

        }
    }

    private Specification<T> joinSpecs(Specification<T> specification, Requete requete, Where where) {
        Specification<T> spec = new GenericSpecification(t, requete, where);
        switch (where.getAction()){
            case "LIKEONEWORD":
                specification = specification.or(spec);
                break;
            case "LIKEALLWORD":
            case "=": default:
                specification = specification.and(spec);
                break;

        }
        return specification;
    }

    public Specification<T> specify() {

        if (requete != null && requete.getWhere() != null && !requete.getWhere().isEmpty()) {
            Specification<T> specification;
            Where wher = requete.getWhere().get(0);
            //System.out.println(requete.getWhere().size()+"=====wher======="+wher.toString());
            if (FiltreType.LIKEALLWORD.toString().equals(wher.getAction()) || FiltreType.LIKEONEWORD.toString().equals(wher.getAction())||requete.getWhere().size()>1) {
                List<Where> wheres = this.dislockIntoWhere(wher.getValue(), wher.getName(),wher.getAction());
                specification = new GenericSpecification(t, requete, wheres.get(0));
                for (int j = 1; j < wheres.size(); j++) {
                    Where w = wheres.get(j);
                    specification = this.joinSpecs(specification, requete, w);
                }
            } else {
                specification = new GenericSpecification(t, requete, requete.getWhere().get(0));
            }

            for (int i = 1; i < requete.getWhere().size(); i++) {
                Where cond = requete.getWhere().get(i);
                if (FiltreType.LIKEALLWORD.toString().equals(wher.getAction()) || FiltreType.LIKEONEWORD.toString().equals(wher.getAction())) {
                    List<Where> wheres = this.dislockIntoWhere(cond.getValue(), cond.getName(),cond.getAction());
                    for (Where w : wheres) {
                        specification = this.joinSpecs(specification, requete, w);
                    }
                } else {
                    Specification<T> spec = new GenericSpecification(t, requete, cond);
                    specification = specification.and(spec);
                }
            }
            return specification;
        }
        return null;
    }

    private List<Where> dislockIntoWhere(String x, String name,String action) {
        x = x.replace("  ", " ");
        String tab[] = x.split(" ");
        List<Where> retour = new ArrayList<>();
        for (String s : tab) {
            retour.add(new Where(name, s, action));
        }
        return retour;
    }

    public PageRequest getPageRequest() {
        PageRequest pr = PageRequest.of(requete.getPagination().getCurrentPage() - 1, requete.getPagination().getNbreAfficher());
        if (this.getSort() != null) {
            return pr.withSort(this.getSort());
        }
        return pr;
    }

    private String getDateFormat(String prms) {
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        if (prms.length() <= 10) {
            if (prms.contains("-")) {
                String sp[]=prms.split("-");
                if(sp.length==3&&sp[0].length()==4&&sp[1].length()==2&&sp[2].length()==2){
                    format="yyyy-MM-dd";
                }else return null;
            } else if (prms.contains("/")) {
                format = "yyyy/MM/dd";
            }
        } else {
            if (prms.contains("/")) {
                format = "yyyy/MM/dd HH:mm:ss.SSS";
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.parse(prms);
        } catch (Exception ex) {
            // ex.printStackTrace();
            return null;
        }
        return format;
    }

    private static String likeOrder(String keyWords) {
        keyWords = keyWords.replace("  ", " ");
        String t[] = keyWords.split(" "), retour = "";
        for (String s : t) {
            retour = retour + "%" + s;
        }
        if (!retour.equals("")) {
            retour = retour + "%";
        }
        return retour;
    }

    private static Date getDateFin(Date dateReference) {
        long h23h59Min59S59T_ToMillisec = 23 * 60 * 60 * 1000L + 59 * 60 * 1000L + 59 * 1000L + 59;
        return new Date(dateReference.getTime() + h23h59Min59S59T_ToMillisec);
    }
}
