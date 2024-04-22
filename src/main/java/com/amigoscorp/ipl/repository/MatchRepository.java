package com.amigoscorp.ipl.repository;

import com.amigoscorp.ipl.model.Match;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

  List<Match> findByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

  @Query("SELECT m FROM Match m WHERE (m.team1 = :teamName or m.team2 = :teamName) AND m.date BETWEEN :startDate AND :endDate ORDER BY m.date DESC")
  List<Match> getMatchesByTeamNameBetweenDates(@Param("teamName") String teamName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  default List<Match> findLatestMatchesByTeam(String teamName, int count) {
    return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, Pageable.ofSize(count));
  }
}
