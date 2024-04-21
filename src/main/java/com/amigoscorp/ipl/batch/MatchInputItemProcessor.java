package com.amigoscorp.ipl.batch;

import com.amigoscorp.ipl.data.MatchInput;
import com.amigoscorp.ipl.model.Match;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class MatchInputItemProcessor implements ItemProcessor<MatchInput, Match> {

  @Override
  public Match process(final MatchInput matchInput) {
    String firstInningsTeam;
    String secondInningsTeam;
    if ("bat".equals(matchInput.getTossDecision())) {
      firstInningsTeam = matchInput.getTossWinner();
      secondInningsTeam =
          matchInput.getTossWinner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
              : matchInput.getTeam1();
    } else {
      firstInningsTeam =
          matchInput.getTossWinner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
              : matchInput.getTeam1();
      secondInningsTeam = matchInput.getTossWinner();
    }
    return Match.builder().id(Long.parseLong(matchInput.getId())).city(matchInput.getCity())
        .date(LocalDate.parse(matchInput.getDate())).playerOfMatch(matchInput.getPlayerOfMatch())
        .venue(matchInput.getVenue()).team1(firstInningsTeam).team2(secondInningsTeam)
        .tossDecision(matchInput.getTossDecision()).winner(matchInput.getWinner())
        .result(matchInput.getResult()).resultMargin(matchInput.getResultMargin())
        .umpire1(matchInput.getUmpire1()).umpire2(matchInput.getUmpire2()).build();
  }

}