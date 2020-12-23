package org.example.springrestapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class PulsaController {

    @Autowired
    private RestTemplate restTemplate;

    private static String urltelkomsel = "http://localhost:9090/api/pulsa/telkomsel";
    private static String urlindosat = "http://localhost:9090/api/pulsa/indosat";
    private static String urlxl = "http://localhost:9090/api/pulsa/xl";

    @GetMapping("/pulsa/telkomsel")
    public List<Object> getPulsaTelkomsel() {
        Object[] PulsaTelkomsel = restTemplate.getForObject(urltelkomsel, Object[].class);
        return Arrays.asList(PulsaTelkomsel);
    }

    @GetMapping("/pulsa/indosat")
    public List<Object> getPulsaIndosat() {
        Object[] PulsaIndosat = restTemplate.getForObject(urlindosat, Object[].class);
        return Arrays.asList(PulsaIndosat);
    }

    @GetMapping("/pulsa/xl")
    public List<Object> getPulsaXL() {
        Object[] PulsaXl = restTemplate.getForObject(urlindosat, Object[].class);
        return Arrays.asList(PulsaXl);
    }
}
