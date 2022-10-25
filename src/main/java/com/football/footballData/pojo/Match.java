package com.football.footballData.pojo;

import java.io.Serializable;

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
public class Match { /*implements Serializable{
    
    private static final long serialVersionUID = 1234L;*/
    
    String competition;
    String year;
    String round;
    String team1;
    String team2;
    String team1goals;
    String team2goals;
    
}
