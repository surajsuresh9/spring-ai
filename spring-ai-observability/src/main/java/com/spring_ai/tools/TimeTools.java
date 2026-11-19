package com.spring_ai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class TimeTools {

    private static final Logger LOG = LoggerFactory.getLogger(TimeTools.class);

    @Tool(name = "getCurrentLocalTime", description = "Get the current time in the user's time zone")
    public String getCurrentLocalTime() {
        LOG.info("Returning the current time in user's timezone");
        return LocalTime.now().toString();
    }


    @Tool(name = "getCurrentTime", description = "Get the current time in the specified time zone")
    public String getCurrentTime(@ToolParam(description = "Value representing the time zone") String timezone) {
        LOG.info("Returning the current time in the timezone {}", timezone);
        return LocalTime.now(ZoneId.of(timezone)).toString();
    }
}
