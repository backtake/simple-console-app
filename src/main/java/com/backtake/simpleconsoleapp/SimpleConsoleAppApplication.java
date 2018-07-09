package com.backtake.simpleconsoleapp;

import com.backtake.simpleconsoleapp.controllers.AppController;
import com.backtake.simpleconsoleapp.user.User;
import com.backtake.simpleconsoleapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleConsoleAppApplication implements CommandLineRunner {

	@Autowired
	private AppController appController;
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SimpleConsoleAppApplication.class, args);
	}

	@Override
	public void run(String... args) {
		loadSampleDataToBase();
		appController.runApp();
	}

	private void loadSampleDataToBase() {
		User user = new User("Janusz", "1UxnoR+UrVTlH4uqtAfKSbl9wNpBsAgjHHhtkXYo2YI=", "333333333", "janusz@gmail.com");
		//user plain text password is: BUE4HaL#
		userRepository.save(user);
	}
}
