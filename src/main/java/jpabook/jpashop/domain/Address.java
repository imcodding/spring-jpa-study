package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //내장타입
@Getter // @Setter 를 제거. 값 타입 변경 불가능하게 만든다.
public class Address {

    private String city;
    private String street;
    private String zipCode;

    protected Address() {} // jpa 제약 -> 리플랙션이나 프록시 등을 생성할 수 있도록 하기 위해서.

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
