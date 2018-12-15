package com.paem.config;

import org.activiti.engine.ManagementService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Customising activiti process engine, by registering event listeners
 * to manage process scope
 */

@Configuration
@EnableScheduling
public class ProcessEngineConfiguration implements ProcessEngineConfigurationConfigurer {

    @Autowired
    private ProcessStartedActivitiEventListener processStartedActivitiEventListener;

    @Autowired
    private ProcessCompletedActivitiEventListener processCompletedActivitiEventListener;

    @Autowired
    private JobStartedActivitiEventListener jobStartedActivitiEventListener;

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        Map<String, List<ActivitiEventListener>> typedListeners = new HashMap<String, List<ActivitiEventListener>>(2, 1) {
            {
                put(ActivitiEventType.PROCESS_STARTED.name(), Collections.singletonList(processStartedActivitiEventListener));
                put(ActivitiEventType.PROCESS_COMPLETED.name(), Collections.singletonList(processCompletedActivitiEventListener));
                put(ActivitiEventType.JOB_EXECUTION_SUCCESS.name(), Collections.singletonList(jobStartedActivitiEventListener));
            }
        };

        processEngineConfiguration.setTypedEventListeners(typedListeners);
        processEngineConfiguration.setFailedJobCommandFactory(JobNoRetryCmd::new);
    }

    /**
     * Configure ThreadPool for activity, decorating thread for clearing thread local
     * @return
     */
    @Primary
    @Bean
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("ActivitiThread-");
        executor.setTaskDecorator((runnable -> () -> {
            try {
                runnable.run();
            } finally {
                ProcessContext.cleanup();
            }
        }));
        return executor;
    }

    @Autowired
    @Bean
    @Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ProcessScopeHolder processScopeHolder(BeanFactory beanFactory, ManagementService managementService) {
        return new ProcessScopeHolder(((ConfigurableBeanFactory) beanFactory).getRegisteredScope("process"), managementService);
    }
}