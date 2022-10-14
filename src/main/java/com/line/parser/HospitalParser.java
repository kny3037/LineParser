package com.line.parser;

import com.line.domain.Hospital;

public class HospitalParser implements Parser<Hospital>{
    //HospitalParser는 Parser의 Hospital이다.

    private String getSubivsion(String name){
        String[] subdivisions = {"소아과", "피부과", "성형외과", "정형외과", "산부인과", "관절", "안과", "가정의학과", "비뇨기과", "치과", "내과", "외과"};

        for(String subdvision : subdivisions){
            if (name.contains(subdvision)){
                return subdvision;
            }
        }
        return "";
    }

  /*  private String replaceAllQout(String str){
        return str.replaceAll("\"","");
    }*/
    @Override
    public Hospital parse(String str) {
        str = str.replaceAll("\"", "");
        String[] splitted = str.split(",");

        String name = splitted[10];
        String subdivision = getSubivsion(name);


        return new Hospital(splitted[0],splitted[1], splitted[2],
                Integer.parseInt(splitted[6]), name,subdivision);
    }
}
