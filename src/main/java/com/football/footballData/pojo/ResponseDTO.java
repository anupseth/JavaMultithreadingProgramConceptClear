package com.football.footballData.pojo;

import java.util.concurrent.ConcurrentHashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDTO {
	
	ConcurrentHashMap<Integer,Integer> scorelineMatches = new ConcurrentHashMap<>();
	
    volatile int totalDrawnMatches;
    
    public synchronized void setTotalDraws(int total) {
    	this.totalDrawnMatches = total;
    }

}
