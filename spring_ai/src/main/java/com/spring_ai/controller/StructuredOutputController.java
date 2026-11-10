package com.spring_ai.controller;

import com.spring_ai.model.CityInfo;
import com.spring_ai.model.CityListResponse;
import com.spring_ai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {
    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

    @GetMapping("/structure")
    public ResponseEntity<CountryCities> countryAndCityList(@RequestParam("message") String message) {
        CountryCities countryCities = chatClient.prompt().user(message).call().entity(CountryCities.class);
        return new ResponseEntity<>(countryCities, HttpStatus.OK);
    }

    @GetMapping("/structure/list")
    public ResponseEntity<List<String>> cityList(@RequestParam("message") String message) {
        List<String> cities = chatClient.prompt().user(message).call().entity(new ListOutputConverter());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }


    @GetMapping("/structure/map")
    public ResponseEntity<Map<String, Object>> cityMap(@RequestParam("message") String message) {
        Map<String, Object> cities = chatClient.prompt().user(message).call().entity(new MapOutputConverter());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/structure/bean")
    public ResponseEntity<CityListResponse> cityBean(@RequestParam("message") String message) {
        CityListResponse cityListResponse = chatClient.prompt().user(message).call().entity(CityListResponse.class);
        return ResponseEntity.ok(cityListResponse);
    }

    @GetMapping("/structure/bean-list")
    public ResponseEntity<List<CityInfo>> cityBeanList(@RequestParam("message") String message) {
        List<CityInfo> cityInfos = chatClient.prompt().user(message).call().entity(new ParameterizedTypeReference<List<CityInfo>>() {
        });
        return ResponseEntity.ok(cityInfos);
    }
}
