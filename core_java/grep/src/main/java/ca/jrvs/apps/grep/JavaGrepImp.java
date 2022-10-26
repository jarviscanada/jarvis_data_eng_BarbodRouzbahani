package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.Buffer;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

	private String regex;
  	private String rootPath;
  	private String outFile;

  	public static void main(String[] args) {
    	if (args.length != 3) {
  			throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    	}

	BasicConfigurator.configure();

       	JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
       	javaGrepImp.setRootPath(args[1]);
    	javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }

    }
    
    @Override
    public void process() throws IOException {
        List<String> lines = new ArrayList<>();
      
        for (File file : listFiles(getRootPath())) {
            for (String readLine : readLines(file)) {
                if (containsPattern(readLine)) {
                    lines.add(readLine);
                }
            }
        }
        writeToFile(lines);
    }
    
    @Override
    public List<File> listFiles(String rootDir) {
        List<File> listedFiles = new ArrayList<>();
        
        File dir = new File(rootDir);
        File[] fileList = dir.listFiles();
        
        if (fileList == null) {
            return listedFiles;
        }
      
        for (File file : fileList) {
            if (file.isDirectory()) {
                listedFiles.addAll(listFiles(file.getAbsolutePath()));
            } else {
                listedFiles.add(file);
            }
        }
      
        return listedFiles;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                lines.add(currentLine);
            }
            br.close();
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return Pattern.matches(getRegex(), line);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        File file = new File(getOutFile());
        FileOutputStream out = new FileOutputStream(file, true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        try {
            for (String line:lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }


}
