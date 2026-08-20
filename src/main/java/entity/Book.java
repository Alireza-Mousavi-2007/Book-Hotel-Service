package entity;

import enums.BookStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_user",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_room",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Room room;

    @Column(name = "startDate",nullable = false)
    private LocalDateTime startDate;

    @Column(name = "endDate",nullable = false)
    private LocalDateTime endDate;

    @Column(name = "status")
    private BookStatus status;

    @Column(name = "createdAt",nullable = false)
    private LocalDateTime createdAt;

    public Book() {
    }

    public Book(User user, Room room, LocalDateTime startDate, LocalDateTime endDate, BookStatus status, LocalDateTime createdAt) {
        this.user = user;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Book(Integer id, User user, Room room, LocalDateTime startDate, LocalDateTime endDate, BookStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
