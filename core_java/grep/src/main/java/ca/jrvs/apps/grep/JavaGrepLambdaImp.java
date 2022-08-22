package ca.jrvs.apps.practice;

import ca.jrvs.apps.grep.JavaGrepImp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepLambdaImp extends JavaGrepImp {
    public static void main(String[] args) {
        
        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }
        
        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try{
            javaGrepLambdaImp.process();
        } catch (Exception ex){
            javaGrepLambdaImp.logger.error("Error: Unable to process", ex);
        }
    }
        

        @Override
        public List<String> readLines (File inputFile) {
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                return br.lines().collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
        

        @Override
        public List<File> listFiles(String rootDir) {
            File dir = new File(rootDir);
            File[] filesInDir = dir.listFiles();
        
            if (filesInDir == null) {
                return new ArrayList<>();
            }
        
            List<File> recListedFiles = Arrays.stream(filesInDir).filter(File::isDirectory)
                .map(file -> listFiles(file.getAbsolutePath()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        
            return Stream.concat(Arrays.stream(filesInDir).filter(file -> !file.isDirectory()),
                    recListedFiles.stream())
                .collect(Collectors.toList());
        }
        
}

