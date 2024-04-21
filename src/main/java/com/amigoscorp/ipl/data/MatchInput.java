package com.amigoscorp.ipl.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchInput {

  private String id;
  private String city;
  private String date;
  private String playerOfMatch;
  private String venue;
  private String neutralVenue;
  private String team1;
  private String team2;
  private String tossWinner;
  private String tossDecision;
  private String winner;
  private String result;
  private String resultMargin;
  private String eliminator;
  private String method;
  private String umpire1;
  private String umpire2;
}
