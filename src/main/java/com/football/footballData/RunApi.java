package com.football.footballData;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.client.RestTemplate;

import com.football.footballData.pojo.Page;
import com.football.footballData.pojo.ResponseDTO;

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
public class RunApi implements Runnable {
	
	public int i = 0;
	public int year;
	public int j;
	Page page = null;
	String fooResourceUrl = "";
	ResponseDTO responseDTO = null;

	@Override
	public void run() {
		
		Thread currentThread = Thread.currentThread();
		System.out.println("Started executing = "+currentThread.getName());
		
		RestTemplate restTemplate = new RestTemplate();
		
		fooResourceUrl = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&page=" + j
				+ "&team1goals=" + i + "&team2goals=" + i;

		page = restTemplate.getForObject(fooResourceUrl, Page.class);
		int parseInt = Integer.parseInt(page.getTotal());
		
		//responseDTO.setTotalDrawnMatches(responseDTO.getTotalDrawnMatches()+parseInt);
		responseDTO.setTotalDraws(responseDTO.getTotalDrawnMatches()+parseInt);
		
//		ConcurrentHashMap<Integer,Integer> scorelineMatches = responseDTO.getScorelineMatches();
//		
//		if(scorelineMatches.contains(i)) {
//			int total = scorelineMatches.get(i);
//			scorelineMatches.put(i, total+parseInt);
//		}else {
//			scorelineMatches.put(i, parseInt);
//		}
//		
//		responseDTO.setScorelineMatches(scorelineMatches);
//		
//		if(i==3)
//			System.out.println(scorelineMatches);
		
		
		System.out.println("Finished executing = "+currentThread.getName());

	}

}
