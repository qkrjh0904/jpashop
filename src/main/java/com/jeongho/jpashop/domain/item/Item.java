package com.jeongho.jpashop.domain.item;

import com.jeongho.jpashop.controller.NotEnoughStockException;
import com.jeongho.jpashop.domain.Category;
import com.jeongho.jpashop.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
    
    /**
    * @desc : 재고 추가 비즈니스로직
    * @author : 박정호
    * @date : 2021-08-16 오전 12:12
    */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
    * @desc : 재고 감소 로직
    * @author : 박정호
    * @date : 2021-08-16 오전 12:13
    */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }
}
