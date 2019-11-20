package com.evm.filter;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
@Service
public class FilterApplicationStarter {
    @Autowired
    ResourceLoader resourceLoader;



    private static final String validVolteFile="validVotersList.txt";
    private static final String checkCastedVoterlist = "votersCandList.txt";
    private static  File validVote=null;
    private static  File castedVoterlist = null;
    private static  File resultFile = null;
    private static final String filePath = "result.csv";
    private static Set<String> set=new HashSet();
    private static Set<InvalidVoters> invalid=new HashSet();
    private static final String path = "src/main/resources";

    public void getFile(){
        File files = new File(path);
        for (File name: files.listFiles()) {
            if(name.getName().equalsIgnoreCase(validVolteFile))
                validVote=name;
            else if(name.getName().equalsIgnoreCase(checkCastedVoterlist))
                castedVoterlist=name;
else if(filePath.equalsIgnoreCase(name.getName())){
                resultFile=name;
            }
        }
    }
     public void checkVoters(){
        try {
            getFile();
            long lineCount = Files.lines(validVote.toPath()).count();
            System.out.println(" valid voter count :"+lineCount);
            BloomFilter bloomFilter = new BloomFilter((int)lineCount,6,8);
            addVotesList(bloomFilter);
            System.out.println(" add  votes valid voter finish :");
            checkCastVotes(bloomFilter);
            System.out.println(" check  valid voter finish :");
            writeDataLineByLine();
            System.out.println("process end with "+invalid.size());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public  void  addVotesList(BloomFilter bloomFilter ) throws IOException {

        try (FileReader fr = new FileReader(validVote);
             BufferedReader br = new BufferedReader(fr);) {

            String line;
            while ((line = br.readLine()) != null) {
                bloomFilter.add(line);
                set.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public  void checkCastVotes(BloomFilter bloomFilter) throws IOException {
        try(FileReader fr = new FileReader(castedVoterlist);
            BufferedReader br = new BufferedReader(fr);){

            String line;
            while ((line = br.readLine()) != null) {
                String[] arr=line.split("\\s+");
                Boolean b=  bloomFilter.contains(arr[0]);
               if(!b) {
                   InvalidVoters voters = new InvalidVoters().setCandidate_id(arr[1])
                           .setVoterId(arr[0]).setExcptedResult(set.contains(arr[0])).setResult(b);
                   invalid.add(voters);
               }
                }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public  void writeDataLineByLine()
    {
        try {


if(resultFile!=null)
            Files.deleteIfExists(resultFile.toPath());

File file=new File(path+"/"+filePath);
                    // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "voterId", "candidate_id", "excptedResult" ,"result"};
            writer.writeNext(header);
            for (InvalidVoters voters:invalid) {
                String[] data2 = {voters.getVoterId(),voters.getCandidate_id(),Boolean.toString(voters.getExcptedResult()),Boolean.toString(voters.getResult()) };
                writer.writeNext(data2);
            }
     writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
