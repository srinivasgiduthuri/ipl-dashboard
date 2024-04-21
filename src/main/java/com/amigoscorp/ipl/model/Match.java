package com.amigoscorp.ipl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Match {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String city;
  private LocalDate date;
  private String playerOfMatch;
  private String venue;
  private String team1;
  private String team2;
  private String tossWinner;
  private String tossDecision;
  private String winner;
  private String result;
  private String resultMargin;
  private String umpire1;
  private String umpire2;
}
