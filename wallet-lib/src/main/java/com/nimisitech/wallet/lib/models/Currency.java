package com.nimisitech.wallet.lib.models;

import com.nimisitech.wallet.lib.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "currencies")
@Getter
@Setter
public class Currency extends BaseEntity {
    @Column(length = 50)
    private String name;
    @Column(length = 5,unique = true)
    private String code;
}
