package io.samjingwen.example;

import io.samjingwen.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoApplication {

  public static void main(String[] args) {
    log.info("" + HttpStatus.BAD_REQUEST);
    log.info("" + HttpStatus.UNAUTHORIZED);
    log.info("" + HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
