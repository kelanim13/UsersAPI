package com.tts.usersapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.usersapi.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
	//method to find users with given value for the property 'state'
	public List<User> findByState(String state); 
	
	@Override
	public List<User> findAll(); 
}
