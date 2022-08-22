package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "account_session")
@AllArgsConstructor
@NoArgsConstructor
public class AccountSession implements Serializable {
	private static final long serialVersionUID = -297553281792804396L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "access_token")
	private String accessToken;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Đánh dấu có mỗi quan hệ 1-1 với role ở phía dưới
	@JoinColumn(name = "account_id") // Liên kết với nhau qua khóa ngoại
	private User user;

	// Nếu không đánh dấu @Column thì sẽ mapping tự động theo tên biến
	@Column(name = "created_time")
	private Date createdTime = new Date();
	@Column(name = "updated_time")
	private Date updatedTime = new Date();
}
