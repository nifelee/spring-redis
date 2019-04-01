package com.nifelee.springredis.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class Item implements Serializable {

  private static final long serialVersionUID = -7734201237931573922L;

  private String name;

  @Builder
  public Item(String name) {
    this.name = name;
  }

}
