package com.line;

import com.line.parser.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileController<T> {
    Parser<T> parser;
    boolean isRemoveColumnName = true;

    public FileController(Parser<T> parser) { //이 parser<T>객체
        //FileController에 매개변수로 Parser<T>가 왔다.
        this.parser = parser;
    }

    public FileController(Parser<T> parser, boolean isRemoveColumnName)
    {
        this.parser = parser;
        this.isRemoveColumnName = isRemoveColumnName;
    }

    List<T> readLines(String filename) throws IOException {
        List<T> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String str;

        //첫번째 라인이 각 Column에 대한 설명이면 빼고 나머지를 읽기 위하여
        // csv 파일의 header 제거하기
        if(isRemoveColumnName){
            br.readLine();
        }
        while ((str = br.readLine()) != null){
            System.out.println(str);
            result.add(parser.parse(str));
        }
        return result;
    }

    public void createANewFile(String filename) throws IOException{
        File file = new File(filename);
        file.createNewFile();
        System.out.println("Have a file generated? : " + file.exists());
    }

    public void writeLines(List<String> lines, String filename){
        File file = new File(filename);

        try{
            BufferedWriter writer
                    = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            for (String str : lines){
                writer.write(str);
            }
            writer.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        System.out.println("success");
    }

}
