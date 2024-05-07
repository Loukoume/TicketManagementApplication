package com.ennov.it.ticketmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "ticket_management_ticket")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "titre",   "id_user"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties
public class Ticket implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_ticket")
    @SequenceGenerator(name = "seq_ticket", sequenceName = "ticket_id_ticket_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ticket")
    private long idTicket;
    @Column(name = "titre")
    private String titre;
    @Column(name = "description")
    private String description;
    @Column(name = "statut")
    private String statut;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;
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
    public Ticket clone() {
        try {
            return (Ticket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
