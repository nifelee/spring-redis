package com.nifelee.springredis.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@RedisHash("point")
public class Point implements Serializable {

  private static final long serialVersionUID = -1534631977809042385L;

  @Id
  private String id;
  private Long amount;
  private LocalDateTime refreshTime;

  private List<Item> items = new ArrayList<>();

  @Builder
  public Point(String id, Long amount, LocalDateTime refreshTime) {
    this.id = id;
    this.amount = amount;
    this.refreshTime = refreshTime;
  }

  public void refresh(long amount, LocalDateTime refreshTime){
    // 저장된 데이터보다 최신 데이터일 경우
    if(refreshTime.isAfter(this.refreshTime)){
      this.amount = amount;
      this.refreshTime = refreshTime;
    }
  }

  public void addItem(String name) {
    this.items.add(Item.builder().name(name).build());
  }

}
