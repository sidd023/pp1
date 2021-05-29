package com.PP1_BackEnd.Springboot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PP1_BackEnd.Springboot.model.Profile;

public interface ProfileRepo extends JpaRepository < Profile, Long > {
	
	@Query(value = "SELECT * FROM Profile", nativeQuery = true)
	List<Profile> getAllProfiles();
	
	@Query(value = "SELECT * FROM Profile WHERE  username = :username", nativeQuery = true)
	List <Profile> getByUsername(@Param("username") String username);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Profile "
			+ "SET category= :category , date_of_graduation= :dateOfgraduation,"
			+ "degree_type= :degreeType ,pincode= :locationPincode ,summary= :summary ,university= :university "
			+ "WHERE username= :username ", nativeQuery = true)
	void updateProfile(@Param("summary") String summary, @Param("university")String university, @Param("degreeType")String degree_type, 
			@Param("dateOfgraduation")String date_of_graduation, @Param("locationPincode")String locationPincode, 
			@Param("category")String category, @Param("username")String username);
	
	

}
