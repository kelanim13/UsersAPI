package com.tts.usersapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.usersapi.model.User;
import com.tts.usersapi.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "users", description = "Operations to view/create/update/delete users")
@RequestMapping("/v1")
public class UserControllerV1 
{
	@Autowired
	UserRepository repository; 
	
	@ApiOperation(value = "Get all users, optionally filtered by state", response=User.class,
				  responseContainer = "List")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully retrieved users")
	})
	//adding endpoint to get all users
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required = false) String state)
	{
		if(state != null)
		{
			return new ResponseEntity<List<User>>((List<User>) repository.findByState(state), HttpStatus.OK);
			/*List<User> user = repository.findByState(state); 
			return new ResponseEntity<List<User>>(user, HttpStatus.OK); */
		}
		else
		{
			return new ResponseEntity<List<User>>((List<User>) repository.findAll(), HttpStatus.OK); 
		}
	}
	
	@ApiOperation(value = "Get a single user", response=User.class)
	@ApiResponses( value = {
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 200, message = "Successfully retrieved user")
			
	})
	@GetMapping("users/{id}")
	public ResponseEntity <Optional<User>> getUserByID(@PathVariable(value="id") Long id)
	{
		Optional<User> user = repository.findById(id); 
		
		if(!user.isPresent()) 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		else
		{
		return new ResponseEntity<>(user, HttpStatus.OK); 
		}
		
	}
	
	@ApiOperation(value = "Create a user", response=Void.class)
	@ApiResponses( value = {
			@ApiResponse(code = 400, message = "Bad request formatting or user exists"),
			@ApiResponse(code = 201, message = "Successfully created user")
			
	})
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody User user, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		else
		{
		repository.save(user); 
		return new ResponseEntity<>(HttpStatus.CREATED); 
		}
	}
	
	@ApiOperation(value = "Update a user", response=Void.class)
	@ApiResponses( value = {
			@ApiResponse(code = 404, message = "User id not found"),
			@ApiResponse(code = 404, message = "Bad request formatting"),
			@ApiResponse(code = 200, message = "User updated successfully")	
	})
	@PutMapping("/users/{id}")
	public ResponseEntity<Void> createUser(@Valid @PathVariable(value="id") Long id,
						   @RequestBody User user, BindingResult bindingResult)
	{
		Optional<User> requestedUser = repository.findById(id); 
		
		if(!requestedUser.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(bindingResult.hasErrors())
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		repository.save(user); 
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	@ApiOperation(value = "Delete a user", response=Void.class)
	@ApiResponses( value = {
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 200, message = "User deleted")	
	})
	@DeleteMapping("users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value="id") Long id)
	{
		Optional<User> user = repository.findById(id); 
		
		if(!user.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK); 
	}

}
