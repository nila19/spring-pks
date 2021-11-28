package com.springcloud.inventory;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.springcloud.inventory.service.Inventory2Service;
import com.springcloud.inventory.service.InventoryService;
import com.springcloud.inventory.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
  private static final String JSON = APPLICATION_JSON_VALUE;

  private final InventoryService inventoryService;
  private final RestService restService;
  private final Inventory2Service inventory2Service;

  @PostMapping(value = "/process", consumes = JSON, produces = JSON)
  public ResponseEntity<Order> process(@RequestBody Order order) {
    return ResponseEntity.ok(this.inventoryService.process(order));
  }

  @PostMapping(value = "/rest", consumes = JSON, produces = JSON)
  public ResponseEntity<Order> rest(@RequestBody OrderWithUrl input) {
    return ResponseEntity.ok(this.restService.makeRestCall(input));
  }

  @PostMapping(value = "/restFast", consumes = JSON, produces = JSON)
  public ResponseEntity<Order> restFast(@RequestBody OrderWithUrl input) {
    return ResponseEntity.ok(this.restService.makeRestCallFast(input));
  }

  @PostMapping(value = "/restSlow", consumes = JSON, produces = JSON)
  public ResponseEntity<Order> restSlow(@RequestBody OrderWithUrl input) throws Exception {
    return ResponseEntity.ok(this.inventory2Service.makeRestCallSlow(input));
  }

  @PostMapping(value = "/calc")
  public ResponseEntity<Integer> calc() {
    return ResponseEntity.ok(this.inventory2Service.calculate(5, 10));
  }
}
