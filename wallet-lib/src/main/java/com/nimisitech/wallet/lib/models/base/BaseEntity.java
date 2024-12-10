package com.nimisitech.wallet.lib.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimisitech.wallet.lib.converters.DateTimeConverter;
import com.nimisitech.wallet.lib.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @JsonIgnore
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime createdOn;

    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime lastModifiedOn;

    @PrePersist
    public void prePersist(){
        this.createdOn = DateTimeUtils.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.lastModifiedOn = DateTimeUtils.now();
    }
}
