package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyMessageRepository extends JpaRepository<MessageModel, Long>{
    List<MessageModel> findAllBySenderId(Integer senderId);
    List<MessageModel> findAllByChatRoomId(Integer chatRoomId);
    void  deleteById (Integer id);
    void deleteAllByChatRoomId(Integer chatRoomId);

}

