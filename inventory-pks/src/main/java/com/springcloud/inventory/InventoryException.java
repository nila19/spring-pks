package com.springcloud.inventory;

public class InventoryException extends RuntimeException {
  private static final long serialVersionUID = 3050929978823738127L;

  public InventoryException(String msg) {
    super(msg);
  }
}
