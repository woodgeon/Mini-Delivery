package me.woodgeon.minidelivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import me.woodgeon.minidelivery.domain.Order;
import me.woodgeon.minidelivery.dto.order.AddOrderRequest;
import me.woodgeon.minidelivery.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<Order> addOrder(@RequestBody AddOrderRequest request) throws JsonProcessingException {
        Order savedOrder = orderService.save(request);
        orderService.sendOrderToMqtt(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedOrder);
    }
    @GetMapping("/api/history")
    // 주문내역을 조회한 User가 주문한 내용만 리턴하도록 만들기
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

}
