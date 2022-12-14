package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = -297553281792804396L;
	public final static AccountRole DEFAULT_ROLE = new AccountRole(1);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Mapping thông tin biến với tên cột trong Database
	@Column(name = "login_name", unique = true)
	private String loginName;
	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@ManyToOne(fetch = FetchType.EAGER) // Đánh dấu có mỗi quan hệ 1-1 với role ở phía dưới
	@JoinColumn(name = "role_id", columnDefinition = "int default 1") // Liên kết với nhau qua khóa ngoại
	private AccountRole role = DEFAULT_ROLE;

	// Nếu không đánh dấu @Column thì sẽ mapping tự động theo tên biến
	@Column(name = "created_time")
	private Date createdTime = new Date();
	@Column(name = "updated_time")
	private Date updatedTime = new Date();
}
