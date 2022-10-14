package com.line.parser;

public interface Parser<T> {
    T parse(String str);
}
//T가 어떤 형으로 받아질지를 모름
//환자형일수도 병원형일 수도 있고,
//hospitalparser를 병원형으로 받기 위해,
//다른데는 별도 일수도 있으니. T로 선언해줌.
//파일 읽을 때
//이 Parser를 통해서 바꿔줘야만 Hospital 형태로 받을 수 있는 것.
