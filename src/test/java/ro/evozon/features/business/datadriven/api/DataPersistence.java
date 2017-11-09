package ro.evozon.features.business.datadriven.api;

import ro.evozon.tools.models.Domain;
import ro.evozon.tools.models.Service;
import ro.evozon.tools.models.Staff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataPersistence {
    static  class DomainDataPersistence {


        public static List<Domain> processInputFile(String inputFilePath){
            List<Domain> inputList = new ArrayList<Domain>();
            try{

                File inputF = new File(inputFilePath);
                InputStream inputFS = new FileInputStream(inputF);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
                inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return inputList;
        }
        private static Function<String, Domain> mapToItem = (String line) -> {
            String[] p = line.split(",");// a CSV has comma separated lines
            Domain item = new Domain();

            if (p[0] != null && p[0].trim().length() > 0) {
                item.setNumeDomeniu(p[0]);//<-- this is the first column in the csv file
            }
            if (p[1] != null && p[1].trim().length() > 0) {
                item.setLocatiaDomeniului(p[1]);//<-- this is the first column in the csv file
            }
            if (p[2] != null && p[2].trim().length() > 0) {
                item.setLocatieId(p[2]);//<-- this is the first column in the csv file
            }
            else
            {
                item.setLocatieId("");
            }
            //more initialization goes here
            return item;
        };

    }
    static class ServiceDataPersistence {


        public static List<Service> processServiceInputFile(String inputFilePath){
            List<Service> inputList = new ArrayList<Service>();
            try{

                File inputF = new File(inputFilePath);
                InputStream inputFS = new FileInputStream(inputF);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
                inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return inputList;
        }
        private static Function<String, Service> mapToItem = (String line) -> {
            String[] p = line.split(",");// a CSV has comma separated lines
            Service item = new Service();

            if (p[0] != null && p[0].trim().length() > 0) {
                item.setAsociatedDomain(p[0]);//<-- this is the first column in the csv file
            }
            if (p[1] != null && p[1].trim().length() > 0) {
                item.setServiceName(p[1]);//<-- this is the first column in the csv file
            }
            if (p[2] != null && p[2].trim().length() > 0) {
                item.setServiceDuration(p[2]);//<-- this is the first column in the csv file
            }
            if (p[3] != null && p[3].trim().length() > 0) {
                item.setServicePrice(p[3]);//<-- this is the first column in the csv file
            }
            if (p[4] != null && p[4].trim().length() > 0) {
                item.setPersPerService(p[4]);//<-- this is the first column in the csv file
            }
            if (p[5] != null && p[5].trim().length() > 0) {
                item.setDomainId(p[5]);//<-- this is the first column in the csv file
            }
            else {
                item.setDomainId("");
            }
            if (p[6] != null && p[6].trim().length() > 0) {
                item.setLocationId(p[6]);//<-- this is the first column in the csv file
            }
            else {
                item.setLocationId("");
            }
            //more initialization goes here
            return item;
        };

    }
    static class StaffDataPersistence {

        public static List<Staff> processInputFile(String inputFilePath) {
            List<Staff> inputList = new ArrayList<Staff>();
            try {

                File inputF = new File(inputFilePath);
                InputStream inputFS = new FileInputStream(inputF);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
                inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputList;
        }

        private static Function<String, Staff> mapToItem = (String line) -> {
            String[] p = line.split(",");// a CSV has comma separated lines
            Staff item = new Staff();
            if (p[0] != null && p[0].trim().length() > 0) {
                item.setNumeAngajat(p[0]);//<-- this is the first column in the csv file
            }
            if (p[1] != null && p[1].trim().length() > 0) {
                item.setEmailAngajat(p[1]);//<-- this is the first column in the csv file
            }
            if (p[2] != null && p[2].trim().length() > 0) {
                item.setTelefonAngajat(p[2]);//<-- this is the first column in the csv file
            }
            if (p[3] != null && p[3].trim().length() > 0) {
                item.setLuni(p[3]);//<-- this is the first column in the csv file
            }
            if (p[4] != null && p[4].trim().length() > 0) {
                item.setMarti(p[4]);//<-- this is the first column in the csv file
            }
            if (p[5] != null && p[5].trim().length() > 0) {
                item.setMiercuri(p[5]);//<-- this is the first column in the csv file
            }
            if (p[6] != null && p[6].trim().length() > 0) {
                item.setJoi(p[6]);//<-- this is the first column in the csv file
            }
            if (p[7] != null && p[7].trim().length() > 0) {
                item.setVineri(p[7]);//<-- this is the first column in the csv file
            }
            if (p[8] != null && p[8].trim().length() > 0) {
                item.setSambata(p[8]);//<-- this is the first column in the csv file
            }
            if (p[9] != null && p[9].trim().length() > 0) {
                item.setDuminica(p[9]);//<-- this is the first column in the csv file
            }
            if (p[10] != null && p[10].trim().length() > 0) {
                item.setServiciuAsignat(p[10]);//<-- this is the first column in the csv file
            }
            if (p[11] != null && p[11].trim().length() > 0) {
                item.setServiciuId(p[11]);//<-- this is the first column in the csv file
            }
            else
            {
                item.setServiciuId("");
            }

            if (p[12] != null && p[12].trim().length() > 0) {
                item.setDomeniuId(p[12]);//<-- this is the first column in the csv file
            }
            else
            {
                item.setDomeniuId("");
            }
            if (p[13] != null && p[13].trim().length() > 0) {
                item.setLocatieId(p[13]);//<-- this is the first column in the csv file
            }
            else
            {
                item.setLocatieId("");
            }
            if (p[14] != null && p[14].trim().length() > 0) {
                item.setStaffId(p[14]);//<-- this is the first column in the csv file
            }
            else
            {
                item.setStaffId("");
            }


            //more initialization goes here
            return item;
        };

    }

}
