package com.icic.test.app.controller;


import com.icic.test.app.exception.ResourceNotFoundException;
import com.icic.test.app.model.User;
import com.icic.test.app.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.GetLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.GetLogEventsResponse;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		String originalStr = "Hello";
		String reversedStr = "";
		System.out.println("Original string: " + originalStr);

		for (int i = originalStr.length()-1; i >=0; i--) {
			reversedStr =reversedStr + originalStr.charAt(i)  ;
		}

		System.out.println("Reversed string: "+ reversedStr);
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(
			@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	@GetMapping("/user")
	public User createUser() {
		CloudWatchLogsClient logsClient = CloudWatchLogsClient.builder()
				.region(Region.US_EAST_1) // Replace with your desired region
				.build();

		// Define the log group and log stream names
		String logGroupName = "/aws/eks/sky-dev-cluster/contra-reader";
		String logStreamName = "containerd://aa664d08257a8d2697f85d207b0c0b0e35136bc71bcac9b5c3cc29e00dd117d9";


// Create a request to retrieve log events
		GetLogEventsRequest request = GetLogEventsRequest.builder()
				.logGroupName(logGroupName)
				.logStreamName(logStreamName)
				.build();

		// Retrieve the log events
		GetLogEventsResponse response = logsClient.getLogEvents(request);

		// Iterate over the log events and print the messages
		response.events().forEach(event -> System.out.println(event.message()));

		return null;
	}

	//TODO sky-dev-cluster,

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(
			@PathVariable(value = "id") Long userId,
			@Valid @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
		        .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
		
		user.setEmailId(userDetails.getEmailId());
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(
			@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
		        .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
