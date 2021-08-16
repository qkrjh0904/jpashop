package com.jeongho.jpashop.service;

import com.jeongho.jpashop.Exception.NotEnoughStockException;
import com.jeongho.jpashop.domain.Address;
import com.jeongho.jpashop.domain.Member;
import com.jeongho.jpashop.domain.Order;
import com.jeongho.jpashop.domain.OrderStatus;
import com.jeongho.jpashop.domain.item.Book;
import com.jeongho.jpashop.domain.item.Item;
import com.jeongho.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("정호책", 10000, 10);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // result
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, findOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, findOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000*orderCount, findOrder.getTotalPrice(), "주문 가격은 가격*수량 이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만틈 재고가 줄어야한다.");

    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("정호책", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // result
        Order order = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, book.getStockQuantity(),"주문이 취소된 상품은 재고가 그만큼 증가해야한다.");
    }

    @Test
    public void 재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("정호책", 10000, 10);

        int orderCount = 11;

        // when
        // result
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("박정호");
        member.setAddress(new Address("광교", "에듀타운로", "123-123"));
        em.persist(member);
        return member;
    }

}