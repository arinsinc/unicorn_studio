package com.unicorn.studio.entity;



import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
public class Notification {
    @Id
    @GenericGenerator(name="notification_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "notification_seq")
    @Column(name="id")
    private long id;

    @Column(name="note")
    private String note;

    @Column(name="note_type")
    private String noteType;

    @Column(name="is_read")
    private Boolean isRead = false;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Notification() {}

    public Notification(String note, String noteType, Boolean isRead) {
        this.note = note;
        this.noteType = noteType;
        this.isRead = isRead;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
