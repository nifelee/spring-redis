package com.nifelee.springredis.redis;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PointRedisRepositoryTest {

  @Autowired
  private PointRedisRepository pointRedisRepository;

  @Test
  public void save_And_findById() {
    //given
    String id = "nifelee";
    LocalDateTime refreshTime = LocalDateTime.now();

    Point point = Point.builder()
        .id(id)
        .amount(1000L)
        .refreshTime(refreshTime)
        .build();

    //when
    pointRedisRepository.save(point);

    //then
    Point savedPoint = pointRedisRepository.findById(id).get();
    log.debug("point : {}", savedPoint);
    Assertions.assertThat(savedPoint.getAmount()).isEqualTo(1000L);
    Assertions.assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
  }

  @Test
  public void update() {
    //given
    String id = "nifelee";
    LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
    pointRedisRepository.save(Point.builder()
        .id(id)
        .amount(1000L)
        .refreshTime(refreshTime)
        .build());

    //when
    Point savedPoint = pointRedisRepository.findById(id).get();
    savedPoint.refresh(2000L, LocalDateTime.now());
    log.debug("savedPoint : {}", savedPoint);
    pointRedisRepository.save(savedPoint);

    //then
    Point refreshPoint = pointRedisRepository.findById(id).get();
    log.debug("refreshPoint : {}", refreshPoint);
    Assertions.assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
  }

  @Test
  public void list() {
    //given
    String id = "nifelee";
    LocalDateTime refreshTime = LocalDateTime.now();

    Point point = Point.builder()
        .id(id)
        .amount(1000L)
        .refreshTime(refreshTime)
        .build();

    point.addItem("item");

    //when
    pointRedisRepository.save(point);

    //then
    Point savedPoint = pointRedisRepository.findById(id).get();
    log.debug("point : {}", savedPoint);
  }

}