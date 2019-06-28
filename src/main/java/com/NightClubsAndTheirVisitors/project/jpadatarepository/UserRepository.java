package com.NightClubsAndTheirVisitors.project.jpadatarepository;


import com.NightClubsAndTheirVisitors.project.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User save (User user);

    User findByUsername (String name);

    @Query(value = "SELECT user_name from user", nativeQuery = true)
    List<String> getAllUserId ();

    //all night clubs which user with username=${username}  has visited already
    @Query(value = "SELECT night_club_name FROM club_user FULL JOIN user u on FULL.user_id = u.id JOIN club ON FULL.club_id = club.id WHERE user_name=?", nativeQuery = true)
    List<String> clubsUserVisited (String userName);

    //all night clubs which user HASN'T visited yet
    @Query(value = "SELECT night_club_name from club", nativeQuery = true)
    List<String> clubsUserVisitedNotYet ();

    @Modifying
    @Query(value = "SELECT user_id FROM club_user FULL JOIN user u on FULL.user_id = u.id JOIN club ON FULL.club_id = club.id WHERE club_id=?", nativeQuery = true)
    List<User> getAllUserWhereClubId (Integer clubId);

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> listUsers ();

}
