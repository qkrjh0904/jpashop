# Spring Boot + JPA 실습

---
## Settings
##### 1. Spring Boot + JPA + H2
##### 2. Junit5

---
## Entity Class 개발
##### 1. 실무에서는 가급적 Getter는 열어두고, Setter는 꼭 필요한 경우에만 사용할것!
> Getter는 열어두면 편하다.  
> Setter를 열어두면 Entity의 변경을 추적하기 힘들다.  
> 따라서 변경지점이 명확하게 비지니스 메소드를 별도로 제공해야한다.  

##### 2. Entity의 식별자는 `클래스명_id`로 사용하면 식별하기 좋다.
##### 3. 실무에서는 `@ManyToMany`를 절대 사용하면 안된다.
> 중간 테이블에 다른 데이터를 넣을 수 없다.  
> 운영하기도 힘들다.  

##### 4. 값 타입은 변경 불가능하게 설계해야 한다. 
> `@Setter`를 제거하고, 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 만들어야한다.  
> 그래서 기본 생성자는 `protected`로 설정하는것이 안전하다.  
> 이러한 제약을 두는 이유는 JPA구현 라이브러리가 객체를 생성할 때 `리플랙션` 같은 기술을 사용할 수 있도록 지원해야 하기 때문이다.  

##### 5. 모든 연관관계는 지연로딩으로 설정!
> `즉시로딩(EAGER)`은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다.  
> 특히 JPQL을 실행할 때 N+1 문제가 자주발생한다.  
> 실무에서는 모든 연관관계를 `지연로딩('LAZY')`으로 설정한다.    
> 연관된 Entity를 함께 DB에서 조회해야한다면, fetch join 또는 Entity 그래프 기능을 사용한다.  
> @XToOne관계는 기본이 `EAGER`이므로 지연로딩으로 바꿔줘야한다.  

##### 6. 테이블 맵핑전략
> `SpringPhysicalNamingStrategy`로 기본셋팅이 되어있다.  
> 이걸 수정하면 전사 표준으로 설정할 수 있다.

##### 7. Cascade
> cascade 설정을 해주면 한번에 persist 된다.  
> 예를들어 OrderItems를 cascade 설정을 해두면 Order를 persist할 때 OrderItems까지 persist된다.  



