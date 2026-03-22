package com.studying.utilitybillsbot;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  private final UsersRepository usersRepository;

  @Autowired
  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public User findById(int id) {
    return usersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  public User findByChatId(long chatId) {
    return usersRepository.findByChatId(chatId).orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  public void save(User user) {
    usersRepository.save(user);
  }

  public void deleteById(int id) {
    usersRepository.deleteById(id);
  }

}
