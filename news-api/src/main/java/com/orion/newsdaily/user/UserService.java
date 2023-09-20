package com.orion.newsdaily.user;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${news.page.min:0}")
    public int pageMin = 0;

    @Value("${news.page.size.min:1}")
    public int pageSizeMin = 1;

    @Value("${news.page.size.max:100}")
    public int pageSizeMax = 100;


    final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDetails loadUserByUsername(String jti, String username) throws UsernameNotFoundException {
        return loadUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        Optional<User> acc = userRepo.findById(id);
        if (acc.isPresent())
        {
            return acc;
        }
        return null;
    }

    public List<User> findAllByName(int page, int size, String title) {

        if (page < pageMin) {
            page = pageMin;
        }
        if (size > pageSizeMax) {
            size = pageSizeMax;
        } else if (size < pageSizeMin) {
            size = pageSizeMin;
        }
        logger.debug("News search with page: {}, size: {}, title: {}", page, size, title.replaceAll("[\r\n]", ""));   //log forgery
        Pageable pageable = PageRequest.of(page, size);
        if (title.isEmpty() || title.isBlank()) {
            return  userRepo.findByOrderByIdDesc(pageable).getContent();
        }
        return userRepo.findByUsernameLikeOrderByIdDesc(pageable, title).getContent();
    }



    @Transactional
    public User create(User accToInsert) {
        Long accId = accToInsert.getId();
        if(accId != null && userRepo.existsById(accId)) {
            return null;
        }
        return userRepo.save(accToInsert);
    }

    @Transactional
    public User update(User updatedAccount, Long id) {

        // Check if the account item with the given id exists in the repository
        Optional<User> existingAccountOptional = userRepo.findById(id);

        logger.debug("check this.");

        if (existingAccountOptional.isEmpty()) {
            logger.trace("There is nothing to update with id = {}",id);
            return null; // Return null or throw an exception to handle the case where the account item doesn't exist
        }

        // Get the existing news item from the Optional
        User existingAccount = existingAccountOptional.get();

        // Update the existing news item with the new data from newsToUpdate
        existingAccount.setUsername(updatedAccount.getUsername());
        existingAccount.setPassword(updatedAccount.getPassword());
        existingAccount.setEmail(updatedAccount.getEmail());
        existingAccount.setRole(updatedAccount.getRole());
        existingAccount.setLoggedIn(updatedAccount.isLoggedIn());

        // Save the updated news item back to the repository
        userRepo.save(existingAccount);

        return existingAccount; // Return the updated news item
    }

    public User findByUserName(String username){
        return userRepo.findByUsername(username);
    }
    public boolean delete(Long id)
    {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent())
        {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public String getRoleByUsername(String username) {
        return userRepo.getRoleByUsername(username);
    }
}
