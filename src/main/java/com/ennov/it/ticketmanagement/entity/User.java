package com.ennov.it.ticketmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "ticket_management_user")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties
public class User implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "user_id_user_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_user")
    private long idUser;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;
    @Column(name = "deleted_by")
    private String deletedBy;

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
