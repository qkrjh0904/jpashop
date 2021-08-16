package com.jeongho.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/*
값 타입은 변경 불가능하게 설계해야 한다.
@Setter를 제거하고, 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 만들어야한다.
그래서 기본 생성자는 protected로 설정하는것이 안전하다.
이러한 제약을 두는 이유는 JPA구현 라이브러리가 객체를 생성할 때 리플랙션 같은 기술을 사용할 수 있도록 지원해야 하기 때문이다.
 */
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
