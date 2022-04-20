package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter // 웬만해서는 열어두지 않는 것이 좋음.
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id") // 테이블은 타입이 없기 때문에 '객체명_id' 로 이름 설정
    private Long id; // 객체명이 있기 때문에 id 로만 이름을 설정해도 됨.

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //연관관계 주인이 아니다. Order 테이블에 있는 member 필드에 의해 매핑되었다는 의미.
    private List<Order> orders = new ArrayList<>();
}
