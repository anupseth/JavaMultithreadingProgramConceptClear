package com.football.footballData.pojo;

import java.io.Serializable;
import java.util.List;

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
public class Page { /*
					 * implements Serializable{
					 * 
					 * private static final long serialVersionUID = 5678L;
					 */
    String page;
    String per_page;
    String total;
    String total_pages;
    List<Match> data;
    
}