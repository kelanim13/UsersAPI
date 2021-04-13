package com.tts.usersapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User 
{
	//user properties
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The user id")
	Long id; 
	
	@Length(max = 20)
	@ApiModelProperty(notes = "The first name of the user")
	String firstName; 
	
	@Length(min = 2)
	@ApiModelProperty(notes = "The last name of the user")
	String lastName; 
	
	@Length(max = 20, min = 4)
	@ApiModelProperty(notes = "The state of residence of the user")
	String state; //of residence
	

}
