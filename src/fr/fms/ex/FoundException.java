package fr.fms.ex;

import java.util.Date;

public class FoundException {
    public static void main(String[] args) {
        Date date = null;
        Date today = new Date();
        try{
            System.out.println(date.getClass().getName());
            System.out.println(today.getClass().getName());
        } catch (NullPointerException e){
            System.out.println("Erreur: tentative d'accès à un objet null!");
            System.out.println(e.getMessage());
            System.err.println("Type d'exception: " + e.getClass().getName());
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally{
            System.out.println("L'éxécution est términée!");
        }

    }

}
