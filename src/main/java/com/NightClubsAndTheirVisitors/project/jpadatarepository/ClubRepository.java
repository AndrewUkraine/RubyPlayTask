package com.NightClubsAndTheirVisitors.project.jpadatarepository;


import com.NightClubsAndTheirVisitors.project.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ClubRepository extends JpaRepository<Club, Integer> {

    Club save (Club club);

    @Modifying
    @Query(value = "INSERT INTO club_user (USER_ID, CLUB_ID) VALUES (?, ?)", nativeQuery = true)
    int saveIdUserAndIdClub(Integer userId, Integer clubId);

    @Query(value = "SELECT * FROM club_user WHERE user_id =? AND club_id=?", nativeQuery = true)
    List<Integer> getClubAndUser (Integer userId, Integer clubId);


    //get all names one club -> all users
    @Query(value = "SELECT user_name FROM club_user FULL JOIN user u on FULL.user_id = u.id JOIN club ON FULL.club_id = club.id WHERE club_id=?", nativeQuery = true)
    List<String> getAllUsersNameOneClub (Integer clubId);

    //all name of club
    List<String> getClubByNightClubName (String name);


    @Modifying
    @Query(value = "DELETE FROM club_user WHERE club_id=?", nativeQuery = true)
    void delete (Integer id);

}




