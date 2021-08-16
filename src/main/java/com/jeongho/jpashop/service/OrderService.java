package com.jeongho.jpashop.service;

import com.jeongho.jpashop.domain.Delivery;
import com.jeongho.jpashop.domain.Member;
import com.jeongho.jpashop.domain.Order;
import com.jeongho.jpashop.domain.OrderItem;
import com.jeongho.jpashop.domain.item.Item;
import com.jeongho.jpashop.repository.ItemRepository;
import com.jeongho.jpashop.repository.MemberRepository;
import com.jeongho.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**
    * @desc : 주문
    * @author : 박정호
    * @date : 2021-08-16 오후 3:52
    */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // Entity 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
    * @desc : 주문 쥐소
    * @author : 박정호
    * @date : 2021-08-16 오후 4:29
    */
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /**
    * @desc : 검색
    * @author : 박정호
    * @date : 2021-08-16 오후 4:33
    */
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
