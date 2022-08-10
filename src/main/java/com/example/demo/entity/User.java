package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userspring")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapping thông tin biến với tên cột trong Database
    @Column(name = "hp")
    private int hp = 25;
    @Column(name = "stamina")
    private int stamina = 25;

    
	// Nếu không đánh dấu @Column thì sẽ mapping tự động theo tên biến
    private int atk = 100;
    private int def = 100;
    private int agi = 100;    
}
