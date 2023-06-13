package br.com.otto.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_user")
@EqualsAndHashCode(of = "id")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private Integer age;
	
	@Column(unique = true)
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<Product> products;
	
	// Um usuario pode ter varios produtos
	// varios produtos esta relacionado com um usuario
	
}
