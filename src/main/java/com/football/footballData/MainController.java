package com.football.footballData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.football.footballData.pojo.Page;
import com.football.footballData.pojo.ResponseDTO;

@RestController
public class MainController {

	@GetMapping("/matches/drawn")
	public ResponseDTO getNumberOfDrawMatches(@RequestParam int year) {
		RestTemplate restTemplate = new RestTemplate();
		int i = 0;
		int totalPages = 0;
		int totalDrawMatches = 0;
		int totalPerGoals = 0;
		Page page = null;
		String fooResourceUrl = "";
		ConcurrentHashMap<Integer, Integer> scorelineMatches = new ConcurrentHashMap<>();
		ResponseDTO responseDTO = new ResponseDTO();

		while (true) {

			fooResourceUrl = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&page=1&team1goals="
					+ i + "&team2goals=" + i;

			page = restTemplate.getForObject(fooResourceUrl, Page.class);
			totalPages = Integer.parseInt(page.getTotal_pages());

			if (totalPages == 0)
				break;

			totalPerGoals += Integer.parseInt(page.getTotal());

			for (int j = 2; j <= totalPages; j++) {

				fooResourceUrl = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&page=" + j
						+ "&team1goals=" + i + "&team2goals=" + i;

				page = restTemplate.getForObject(fooResourceUrl, Page.class);
				totalPerGoals += Integer.parseInt(page.getTotal());
			}

			scorelineMatches.put(i, totalPerGoals);
			totalDrawMatches += totalPerGoals;
			i++;

			totalPerGoals = 0;

		}

		responseDTO.setScorelineMatches(scorelineMatches);
		responseDTO.setTotalDrawnMatches(totalDrawMatches);

		return responseDTO;
	}

	@GetMapping("/matches/drawn/thread")
	public ResponseDTO getNumberOfDrawMatchesViaThread(@RequestParam int year) throws InterruptedException {
		RestTemplate restTemplate = new RestTemplate();
		int i = 0;
		int totalPages = 0;
		int totalDrawMatches = 0;
		int totalPerGoals = 0;
		Page page = null;
		String fooResourceUrl = "";
		Map<Integer, Integer> scorelineMatches = new HashMap<>();
		ResponseDTO responseDTO = new ResponseDTO();

		while (true) {

			fooResourceUrl = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&page=1&team1goals="
					+ i + "&team2goals=" + i;

			page = restTemplate.getForObject(fooResourceUrl, Page.class);
			totalPages = Integer.parseInt(page.getTotal_pages());

			if (totalPages == 0)
				break;

			responseDTO.setTotalDrawnMatches(responseDTO.getTotalDrawnMatches() + Integer.parseInt(page.getTotal()));

			List<Thread> threadList = new ArrayList<Thread>();

			for (int j = 2; j <= totalPages; j++) {

				RunApi runnable = new RunApi();
				runnable.setResponseDTO(responseDTO);
				runnable.setI(i);
				runnable.setYear(year);
				runnable.setJ(j);
				Thread t1 = new Thread(runnable);
				t1.setName("Thread-" + j);
				threadList.add(t1);

			}

			for (Thread t : threadList) {
				t.start();
			}

			i++;
			int count = threadList.size();

			while (count >= 1) {

				List<Thread> collect = threadList.stream().filter(th -> th.isAlive()).collect(Collectors.toList());
				count = collect.size();
				System.out.println("Count ------------------- = " + count);
			}

			totalPerGoals = 0;

		}

		return responseDTO;
	}

	@GetMapping("/matches/drawn/thread/pool")
	public ResponseDTO getNumberOfDrawMatchesViaThreadPool(@RequestParam int year) throws InterruptedException {
		RestTemplate restTemplate = new RestTemplate();
		int i = 0;
		int totalPages = 0;
		int totalDrawMatches = 0;
		int totalPerGoals = 0;
		Page page = null;
		String fooResourceUrl = "";
		Map<Integer, Integer> scorelineMatches = new HashMap<>();
		ResponseDTO responseDTO = new ResponseDTO();

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

		while (true) {

			fooResourceUrl = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&page=1&team1goals="
					+ i + "&team2goals=" + i;

			page = restTemplate.getForObject(fooResourceUrl, Page.class);
			totalPages = Integer.parseInt(page.getTotal_pages());

			if (totalPages == 0)
				break;

			responseDTO.setTotalDrawnMatches(responseDTO.getTotalDrawnMatches() + Integer.parseInt(page.getTotal()));

			List<Thread> threadList = new ArrayList<Thread>();

			for (int j = 2; j <= totalPages; j++) {

				RunApi runnable = new RunApi();
				runnable.setResponseDTO(responseDTO);
				runnable.setI(i);
				runnable.setYear(year);
				runnable.setJ(j);
				executor.submit(runnable);

			}

			i++;
			int count = threadList.size();

		}

		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		
		System.out.println("Active count = "+ executor.getActiveCount());
		System.out.println("Queue size = "+ executor.getQueue().size());
		
		

		return responseDTO;
	}

}
