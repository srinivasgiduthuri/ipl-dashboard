package com.amigoscorp.ipl.repository;

import com.amigoscorp.ipl.model.Match;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

  List<Match> findByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

  default List<Match> findLatestMatchesByTeam(String teamName, int count) {
    return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, Pageable.ofSize(count));
  }
}
