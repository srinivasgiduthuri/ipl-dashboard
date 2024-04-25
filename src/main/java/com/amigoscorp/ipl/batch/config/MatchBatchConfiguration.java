package com.amigoscorp.ipl.batch.config;

import com.amigoscorp.ipl.batch.MatchInputItemProcessor;
import com.amigoscorp.ipl.batch.listener.JobCompletionNotificationListener;
import com.amigoscorp.ipl.data.MatchInput;
import com.amigoscorp.ipl.model.Match;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
public class MatchBatchConfiguration {

  private final EntityManagerFactory entityManagerFactory;

  @Autowired
  public MatchBatchConfiguration(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Bean
  public JpaTransactionManager transactionManager(DataSource dataSource) {
    final JpaTransactionManager tm = new JpaTransactionManager();
    tm.setDataSource(dataSource);
    return tm;
  }

  @Bean
  public FlatFileItemReader<MatchInput> reader() {
    return new FlatFileItemReaderBuilder<MatchInput>().name("matchInputItemReader")
        .resource(new ClassPathResource("match-data.csv")).delimited()
        .names("id", "city", "date", "playerOfMatch", "venue", "neutralVenue", "team1", "team2",
            "tossWinner", "tossDecision", "winner", "result", "resultMargin", "eliminator",
            "method", "umpire1", "umpire2").targetType(MatchInput.class).build();
  }

  @Bean
  public MatchInputItemProcessor processor() {
    return new MatchInputItemProcessor();
  }

  @Bean
  public JpaItemWriter<Match> writer(DataSource dataSource) {
    return new JpaItemWriterBuilder<Match>().entityManagerFactory(entityManagerFactory).build();
  }

  @Bean
  public Job importMatchJob(JobRepository jobRepository, Step step1,
      JobCompletionNotificationListener listener) {
    return new JobBuilder("importMatchJob", jobRepository).listener(listener).start(step1).build();
  }

  @Bean
  public Step step1(JobRepository jobRepository, JpaTransactionManager transactionManager,
      FlatFileItemReader<MatchInput> reader, MatchInputItemProcessor processor,
      JpaItemWriter<Match> writer) {
    return new StepBuilder("step1", jobRepository).<MatchInput, Match>chunk(10, transactionManager)
        .reader(reader).processor(processor).writer(writer).build();
  }
}
