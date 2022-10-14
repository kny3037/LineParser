package com.line;

import com.line.domain.Hospital;
import com.line.parser.HospitalParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FileController<Hospital> hospitalFileController = new FileController<>(new HospitalParser());
        //new HospitalParser()는 이름없는 객체 이거를 LineReader의 매개변수로 넘긴다.
        //new HospitalParser()의 type는 LineReader<Hospital>이다.
        //LineReader<Hospital> 여기서의 Hospital는 LineReader의  public class LineReader<T> 여기서의 T이다.
        //객체가 생성, 선언되었으니 LineReader<Hospital>의 생성자가 선언이 되는데, 그 생성자는
        //public LineReader(Parser<T> parser) { //이 parser<T>객체
        //        //LineReader에 매개변수로 Parser<T>가 왔다.
        //        this.parser = parser;
        //    }
        //
        String filename = "C:\\Users\\psi50\\Desktop\\seoul_hospital_infos.csv";
        List<Hospital> hospitals =  hospitalFileController.readLines(filename);

        System.out.println(hospitals.size());
        List<String> lines = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            lines.add(hospital.getSqlInsertQuery());
        }
        String sqlFilename = "hospital_insert.sql";
        hospitalFileController.createANewFile(sqlFilename);
        hospitalFileController.writeLines(lines, sqlFilename);


    }
}
