package jp.co.alpha;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="hero")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hero {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable=false)
	private String name;
	
	@Column
	private String name_kana;
	
	@Column
	private String mail_address;
	
	@Column
	private Date date_of_birth;
	
	@Column
	private String place_of_birth;
	
	@Column
	private String blood_type;
	
	@Column(nullable=false)
	private String account_image;
}
