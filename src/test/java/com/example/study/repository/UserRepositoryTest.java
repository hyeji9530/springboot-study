package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String account = "Test03";
        String password = "Test03";
        UserStatus status = UserStatus.REGISTERED;
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";


        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);

        User u = User.builder()
                .account(account)
                .password(password)
                .status(status)
                .email(email)
                .build();

        User newUser = userRepository.save(user);

        Assertions.assertNotNull(newUser);
       /* // String dql = insert into user (%s, %s, %d) value (account, email, age);
        User user = new User();
        user.setAccount("TestUser03");
        user.setEmail("TestUser03@gmail.com");
        user.setPhoneNumber("010-3333-3333");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser3");

        User newUser = userRepository.save(user);
        System.out.println("newUser : "+newUser);*/
    }

    @Test
    @Transactional
    public void read(){

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");


        if(user != null) {
            user.getOrderGroupList().stream().forEach(orderGroup -> {

                System.out.println("----------------------????????????---------------------");
                System.out.println("????????? : " + orderGroup.getRevName());
                System.out.println("????????? : " + orderGroup.getRevAddress());
                System.out.println("????????? : " + orderGroup.getTotalPrice());
                System.out.println("????????? : " + orderGroup.getTotalQuantity());

                System.out.println("----------------------????????????---------------------");

                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("???????????? ?????? : "+orderDetail.getItem().getPartner().getName());
                    System.out.println("???????????? ???????????? : "+orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("?????? ?????? : "+orderDetail.getItem().getName());
                    System.out.println("???????????? ?????? : "+orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("????????? ?????? : "+orderDetail.getStatus());
                    System.out.println("?????????????????? : "+orderDetail.getArrivalDate());
                });

            });
        }
        Assertions.assertNotNull(user);

        /*// select * from user where id = ?
        Optional<User> user = userRepository.findByAccount("TestUser03");

        user.ifPresent(selectUser ->{

            selectUser.getOrderDetailList().stream().forEach(detail ->{
                Item item = detail.getItem();
                System.out.println(item);

            });

        });*/
    }

    @Test
    @Transactional
    public void update(){

        // update user set account=%?
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser ->{
           selectUser.setAccount("PPPP");
           selectUser.setUpdatedAt(LocalDateTime.now());
           selectUser.setUpdatedBy("update method()");

           userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(3L);

        Assertions.assertTrue(user.isPresent());    // false

        user.ifPresent(selectUser->{
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(3L);

        Assertions.assertFalse(deleteUser.isPresent()); // false
    }
}
