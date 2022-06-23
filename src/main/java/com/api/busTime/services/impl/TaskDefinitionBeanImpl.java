package com.api.busTime.services.impl;

import com.api.busTime.dtos.EmailTaskDefinition;
import org.springframework.stereotype.Service;

@Service
public class TaskDefinitionBeanImpl implements Runnable {

    private EmailTaskDefinition taskDefinition;

    @Override
    public void run() {
 
    }

    public EmailTaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(EmailTaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
