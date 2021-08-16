package com.jeongho.jpashop.domain;

import com.jeongho.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격
    private int count;      // 주문수량

    /**
    * @desc : 생성 메소드
    * @author : 박정호
    * @date : 2021-08-16 오전 4:12
    */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    /** 
    * @desc : 취소 비즈니스 로직
    * @author : 박정호
    * @date : 2021-08-16 오전 4:03
    */
    public void cancel() {
        getItem().addStock(count);
    }

    /**
    * @desc : 총 가격
    * @author : 박정호
    * @date : 2021-08-16 오전 4:06
    */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
