package com.learning.shopdevjava.postgres.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "message", schema = "learning_jpa_hibernate")
@Data
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
    @SequenceGenerator(name = "message_id_seq", sequenceName = "learning_jpa_hibernate.message_id_seq", allocationSize = 1)
    private long id;
    private String text;

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}