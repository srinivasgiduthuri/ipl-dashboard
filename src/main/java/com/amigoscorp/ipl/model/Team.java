package com.amigoscorp.ipl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String teamName;
  private Long totalMatches;
  private Long totalWins;
  @Transient
  private List<Match> matches;
}
