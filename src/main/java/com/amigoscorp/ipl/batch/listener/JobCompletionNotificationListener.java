package com.amigoscorp.ipl.batch.listener;

import com.amigoscorp.ipl.model.Team;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private final EntityManager entityManager;

  @Autowired
  public JobCompletionNotificationListener(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");
      Map<String, Team> teamData = new HashMap<>();
      entityManager.createQuery("select m.team1, count(m.id) from Match m group by m.team1",
              Object[].class).getResultList().stream().map(
              objects -> Team.builder().teamName(String.valueOf(objects[0]))
                  .totalMatches(Long.valueOf(String.valueOf(objects[1]))).build())
          .forEach(team -> teamData.put(team.getTeamName(), team));
      entityManager.createQuery("SELECT m.team2, count(m.id) from Match m group by m.team2",
          Object[].class).getResultList().forEach(objects -> {
        String teamName = String.valueOf(objects[0]);
        Long totalMatches = Long.valueOf(String.valueOf(objects[1]));
        Team currentTeam = teamData.get(teamName);
        currentTeam.setTotalMatches(currentTeam.getTotalMatches() + totalMatches);
      });
      entityManager.createQuery("select m.winner, count(*) from Match m group by m.winner",
          Object[].class).getResultList().forEach(objects -> {
        String teamName = String.valueOf(objects[0]);
        Long totalWins = Long.valueOf(String.valueOf(objects[1]));
        Team currentTeam = teamData.get(teamName);
        if (currentTeam != null) {
          currentTeam.setTotalWins(totalWins);
        }
      });
      teamData.values().forEach(entityManager::persist);
    }
  }
}
