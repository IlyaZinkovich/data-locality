package io.github.ilyazinkovich.reliable.communication.remote;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  GsonHttpMessageConverter gsonHttpMessageConverter() {
    return new GsonHttpMessageConverter(new Gson());
  }
}
