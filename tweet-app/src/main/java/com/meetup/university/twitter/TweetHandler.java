package com.meetup.university.twitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Component
@Log
@RequiredArgsConstructor
@ExternalTaskSubscription("tweet")
public class TweetHandler implements ExternalTaskHandler {

    private final TwitterService twitterService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            log.info(externalTask.getVariable("feedback").toString());
            twitterService.tweet(externalTask.getVariable("feedback"));
            externalTaskService.complete(externalTask);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
